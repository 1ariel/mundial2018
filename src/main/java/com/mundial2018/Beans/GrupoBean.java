/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Beans;

import com.mundial2018.Controller.EquipoHistJpaController;
import com.mundial2018.Controller.EquipoJpaController;
import com.mundial2018.Controller.GrupoJpaController;
import com.mundial2018.Controller.PartidoJpaController;
import com.mundial2018.Controller.exceptions.NonexistentEntityException;
import com.mundial2018.Database.Entities.Equipo;
import com.mundial2018.Database.Entities.EquipoHist;
import com.mundial2018.Database.Entities.Grupo;
import com.mundial2018.Database.Entities.Login;
import com.mundial2018.Database.Entities.Partido;
import com.mundial2018.Database.Persistance.EntityManagerFactoria;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WVQ
 */

@ManagedBean
@ViewScoped
public class GrupoBean {
    private Login login;
    private List<Grupo> listaGrupos;
    private final GrupoJpaController grupojc;
    private final EquipoJpaController equipojc;
    private final EquipoHistJpaController equipoHistjc;
    private final PartidoJpaController partidojc;

    public GrupoBean() {
        EntityManagerFactoria aux = new EntityManagerFactoria();
        EntityManagerFactory emf = aux.getEMF();
        grupojc = new GrupoJpaController(emf);
        equipojc = new EquipoJpaController(emf);
        equipoHistjc = new EquipoHistJpaController(emf);
        partidojc = new PartidoJpaController(emf);
    }
    
    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        login = (Login) ec.getSessionMap().get("login");
        listaGrupos = ordenarEquiposPorPosicion(); 
    }
    
    public List<Grupo> ordenarEquiposPorPosicion() {
        List<Grupo> lista = grupojc.findGrupoEntities();
        
        try {
            for(Grupo grupo: lista) {
                List<Equipo> listaEquipos = equipojc.findEquiposOrdenados(grupo.getId());
                grupo.setEquipoList(listaEquipos);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public void recalcularPuntos(Partido partido) {
        String rol = login.getRol();
        List<Equipo> equipos = equipojc.findEquipoEntities();
        
        if (rol.equals("admin") || rol.equals("superuser")) {
            for(Equipo equipo : equipos) {
                try {
                    EquipoHist equipoHist = equipoHistjc.findEquipoHist(partido.getFecha(), equipo.getId());
                    // Reasignar valores anteriores a Equipo
                    equipo.setJugados(equipoHist.getJugados());
                    equipo.setGanados(equipoHist.getGanados());
                    equipo.setPerdidos(equipoHist.getPerdidos());
                    equipo.setGolesFavor(equipoHist.getGolesFavor());
                    equipo.setGolesContra(equipoHist.getGolesContra());
                    equipo.setPuntos(equipoHist.getPuntos());
                    
                    equipojc.edit(equipo);
                } catch (Exception ex) {
                    Logger.getLogger(GrupoBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void calcularPuntos(List<Partido> partidos) {
        String rol = login.getRol();
        
        if (rol.equals("admin") || rol.equals("superuser")) {
            for(Partido partido : partidos) {
                // Calcular sólo si los partidos pertenecen a la fase de grupos
                if(partido.getRondaId().getId() < 16) {
                    int golesEquipo1 = partido.getGolesEquipo1();
                    int golesEquipo2 = partido.getGolesEquipo2();
                    Equipo equipo1 = equipojc.findEquipo(partido.getEquipo1());
                    Equipo equipo2 = equipojc.findEquipo(partido.getEquipo2());

                    // Partidos jugados
                    equipo1.setJugados(+1);
                    equipo2.setJugados(+1);
                    // Goles a favor
                    equipo1.setGolesFavor(+golesEquipo1);
                    equipo2.setGolesFavor(+golesEquipo2);
                    // Goles en contra
                    equipo1.setGolesContra(+golesEquipo2);
                    equipo2.setGolesContra(+golesEquipo1);
                    // Equipo 1 gana
                    if(golesEquipo1 > golesEquipo2) {
                        equipo1.setGanados(+1);
                        equipo2.setPerdidos(+1);
                        equipo1.setPuntos(+3);
                    // Equipo 2 gana
                    } else if(golesEquipo1 < golesEquipo2) {
                        equipo1.setPerdidos(+1);
                        equipo2.setGanados(+1);
                        equipo2.setPuntos(+3);
                    // Empate
                    } else {
                        equipo1.setEmpatados(+1);
                        equipo2.setEmpatados(+1);
                        equipo1.setPuntos(+1);
                        equipo2.setPuntos(+1);
                    }

                    try {
                        equipojc.edit(equipo1);
                        equipojc.edit(equipo2);
                    } catch (Exception ex) {
                        Logger.getLogger(GrupoBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    public void crearFaseDeEliminatorias(Date fechaPartido) {
        String rol = login.getRol();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy", new Locale("es", "ES"));
        formato.setTimeZone(TimeZone.getTimeZone("UTC"));
        String fecha = formato.format(fechaPartido);
        
        if (Objects.nonNull(fecha) && (rol.equals("admin") || rol.equals("superuser"))) {
            List<Partido> partidos = null;
            
            if(fecha.equals("28-06-2018")) {
                partidos = crearOctavosDeFinal();
            } else if (fecha.equals("03-07-2018")) {
                partidos = crearCuartosDeFinal();
            } else if (fecha.equals("07-07-2018")) {
                partidos = crearSemifinales();
            } else if (fecha.equals("11-07-2018")) {
                partidos = crearFinal();
            }
            
            // Guardar partidos
            for(Partido partido : partidos) {
                try {
                    partidojc.edit(partido);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(GrupoBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(GrupoBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public List<Partido> crearOctavosDeFinal () {
        List<String> octavosRondaIds = Arrays.asList("16", "17", "18", "19");
        List<Partido> octavos = partidojc.findPartidoByRondaId(octavosRondaIds);
        
        // 1C - 2D      30 Junio
        octavos.get(0).setEquipo1(listaGrupos.get(2).getEquipoList().get(0).getId());
        octavos.get(0).setEquipo2(listaGrupos.get(3).getEquipoList().get(1).getId());
        // 1A - 2B      30 Junio
        octavos.get(1).setEquipo1(listaGrupos.get(0).getEquipoList().get(0).getId());
        octavos.get(1).setEquipo2(listaGrupos.get(1).getEquipoList().get(1).getId());
        // 1B - 2A      01 Julio
        octavos.get(2).setEquipo1(listaGrupos.get(1).getEquipoList().get(0).getId());
        octavos.get(2).setEquipo2(listaGrupos.get(0).getEquipoList().get(1).getId());
        // 1D - 2C      01 Julio
        octavos.get(3).setEquipo1(listaGrupos.get(3).getEquipoList().get(0).getId());
        octavos.get(3).setEquipo2(listaGrupos.get(2).getEquipoList().get(1).getId());
        // 1E - 2F      02 Julio
        octavos.get(4).setEquipo1(listaGrupos.get(4).getEquipoList().get(0).getId());
        octavos.get(4).setEquipo2(listaGrupos.get(5).getEquipoList().get(1).getId());
        // 1G - 2H      02 Julio
        octavos.get(5).setEquipo1(listaGrupos.get(6).getEquipoList().get(0).getId());
        octavos.get(5).setEquipo2(listaGrupos.get(7).getEquipoList().get(1).getId());
        // 1F - 2E      03 Julio
        octavos.get(6).setEquipo1(listaGrupos.get(5).getEquipoList().get(0).getId());
        octavos.get(6).setEquipo2(listaGrupos.get(4).getEquipoList().get(1).getId());
        // 1H - 2G      03 Julio
        octavos.get(7).setEquipo1(listaGrupos.get(7).getEquipoList().get(0).getId());
        octavos.get(7).setEquipo2(listaGrupos.get(6).getEquipoList().get(1).getId());

        return octavos;
    }
    
    public List<Partido> crearCuartosDeFinal () {
        List<String> octavosRondaIds = Arrays.asList("16", "17", "18", "19");
        List<Partido> octavos = partidojc.findPartidoByRondaId(octavosRondaIds);
        List<String> cuartosRondaIds = Arrays.asList("20", "21");
        List<Partido> cuartos = partidojc.findPartidoByRondaId(cuartosRondaIds);
        
        // W49 (1A - 2B) - W50 (1C - 2D)    06 Julio
        cuartos.get(0).setEquipo1(encontrarGanador(octavos.get(1)));
        cuartos.get(0).setEquipo2(encontrarGanador(octavos.get(0)));
        // W53 (1E - 2F) - W54 (1G - 2H)    06 Julio
        cuartos.get(1).setEquipo1(encontrarGanador(octavos.get(4)));
        cuartos.get(1).setEquipo2(encontrarGanador(octavos.get(5)));
        // W55 (1F - 2E) - W56 (1H - 2G)    07 Julio
        cuartos.get(2).setEquipo1(encontrarGanador(octavos.get(6)));
        cuartos.get(2).setEquipo2(encontrarGanador(octavos.get(7)));
        // W51 (1B - 2A) - W52 (1D - 2C)    07 Julio
        cuartos.get(3).setEquipo1(encontrarGanador(octavos.get(2)));
        cuartos.get(3).setEquipo2(encontrarGanador(octavos.get(3)));
        
        return cuartos;
    }
    
    public List<Partido> crearSemifinales () {
        List<String> cuartosRondaIds = Arrays.asList("20", "21");
        List<Partido> cuartos = partidojc.findPartidoByRondaId(cuartosRondaIds);
        List<String> semifinalesRondaIds = Arrays.asList("22", "23");
        List<Partido> semifinales = partidojc.findPartidoByRondaId(semifinalesRondaIds);
        
        // W57 (W49 - W50) - W58 (W53 - W54)    10 Julio
        semifinales.get(0).setEquipo1(encontrarGanador(cuartos.get(0)));
        semifinales.get(0).setEquipo2(encontrarGanador(cuartos.get(1)));
        // W59 (W51 - W52) - W60 (W55 - W56)    11 Julio
        semifinales.get(1).setEquipo1(encontrarGanador(cuartos.get(3)));
        semifinales.get(1).setEquipo2(encontrarGanador(cuartos.get(2)));
        
        return semifinales;
    }
    
    public List<Partido> crearFinal() {
        List<String> semifinalesRondaIds = Arrays.asList("22", "23");
        List<Partido> semifinales = partidojc.findPartidoByRondaId(semifinalesRondaIds);
        List<String> finalRondaIds = Arrays.asList("24", "25");
        List<Partido> finales = partidojc.findPartidoByRondaId(finalRondaIds);
        
        // L61 (W57 - W58) - L62 (W59 - W60)    14 Julio
        finales.get(0).setEquipo1(encontrarGanador(semifinales.get(0)));
        finales.get(0).setEquipo2(encontrarGanador(semifinales.get(1)));
        // W61 (W57 - W58) - W62 (W59 - 60)   15 Julio
        finales.get(1).setEquipo1(encontrarPerdedor(semifinales.get(0)));
        finales.get(1).setEquipo2(encontrarPerdedor(semifinales.get(1)));
        
        return finales;
    }
    
    public Integer encontrarGanador(Partido partido) {
        Integer ganador = null;
        
        // Equipo 1 gana
        if(partido.getGolesEquipo1() > partido.getGolesEquipo2()) {
            ganador = partido.getEquipo1();
        // Equipo 2 gana
        } else if (partido.getGolesEquipo1() < partido.getGolesEquipo2()) {
            ganador = partido.getEquipo2();
        // Empate
        } else {
            // Equipo 1 gana por penales
            if(partido.getPenalesEquipo1() > partido.getPenalesEquipo2()) {
                ganador = partido.getEquipo1();
            // Equipo 2 gana por penales
            } else {
                ganador = partido.getEquipo2();
            }
        }
        
        return ganador;
    }
    
    public Integer encontrarPerdedor(Partido partido) {
        Integer perdedor = null;
        
        // Equipo 2 pierde
        if(partido.getGolesEquipo1() > partido.getGolesEquipo2()) {
            perdedor = partido.getEquipo2();
        // Equipo 1 pierde
        } else if (partido.getGolesEquipo1() < partido.getGolesEquipo2()) {
            perdedor = partido.getEquipo1();
        // Empate
        } else {
            // Equipo 2 pierde por penales
            if(partido.getPenalesEquipo1() > partido.getPenalesEquipo2()) {
                perdedor = partido.getEquipo2();
            // Equipo 1 pierde por penales
            } else {
                perdedor = partido.getEquipo1();
            }
        }
        
        return perdedor;
    }
    
    /**
     * @return the listaGrupos
     */
    public List<Grupo> getListaGrupos() {
        return listaGrupos;
    }

    /**
     * @param listaGrupos the listaGrupos to set
     */
    public void setListaGrupos(List<Grupo> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }
}
