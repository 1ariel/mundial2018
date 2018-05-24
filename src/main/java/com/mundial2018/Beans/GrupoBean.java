/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Beans;

import com.mundial2018.Controller.EquipoJpaController;
import com.mundial2018.Controller.GrupoJpaController;
import com.mundial2018.Controller.PartidoJpaController;
import com.mundial2018.Database.Entities.Equipo;
import com.mundial2018.Database.Entities.Grupo;
import com.mundial2018.Database.Entities.Login;
import com.mundial2018.Database.Entities.Partido;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.persistence.Persistence;

/**
 *
 * @author WVQ
 */

@ManagedBean
@ViewScoped
public class GrupoBean {
    private Login login;
    private List<Grupo> listaGrupos;
    private final GrupoJpaController gjc;
    private final EquipoJpaController ejc;
    private final PartidoJpaController pjc;

    public GrupoBean() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mundial2018_Mundial2018_war_1.0-SNAPSHOTPU");
        gjc = new GrupoJpaController(emf);
        ejc = new EquipoJpaController(emf);
        pjc = new PartidoJpaController(emf);
    }
    
    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        login = (Login) ec.getSessionMap().get("login");
        listaGrupos = ordenarEquiposPorPosicion(); 
    }
    
    public List<Grupo> ordenarEquiposPorPosicion() {
        List<Grupo> lista = gjc.findGrupoEntities();
        
        try {
            for(Grupo grupo: lista) {
                List<Equipo> listaEquipos = ejc.findEquiposOrdenados(grupo.getId());
                grupo.setEquipoList(listaEquipos);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public void calcularPuntos(Partido partido) {
        String rol = login.getRol();
        
        if (rol.equals("admin") || rol.equals("superuser")) {
            int golesEquipo1 = partido.getGolesEquipo1();
            int golesEquipo2 = partido.getGolesEquipo2();
            Equipo equipo1 = ejc.findEquipo(partido.getEquipo1());
            Equipo equipo2 = ejc.findEquipo(partido.getEquipo2());

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
                ejc.edit(equipo1);
                ejc.edit(equipo2);
            } catch (Exception ex) {
                Logger.getLogger(GrupoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void crearFaseDeEliminatorias(Date fechaPartido) {
        String rol = login.getRol();
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy", new Locale("es", "ES"));
        formato.setTimeZone(TimeZone.getTimeZone("UTC"));
        String fecha = formato.format(fechaPartido);
        
        if (Objects.nonNull(fecha) && (rol.equals("admin") || rol.equals("superuser"))) {
            if(fecha.equals("28-06-2018")) {
                crearOctavosDeFinal();
            } else if (fecha.equals("03-07-2018")) {
                crearCuartosDeFinal();
            } else if (fecha.equals("07-07-2018")) {
                crearSemifinales();
            } else if (fecha.equals("11-07-2018")) {
                crearFinal();
            }
        }
    }
    // 0A 1B 2C 3D 4E 5F 6G 7H
    public void crearOctavosDeFinal () {
        List<String> listaRondaIds = Arrays.asList("16", "17", "18", "19");
        List<Partido> partidos = pjc.findPartidoByRondaId(listaRondaIds);
        
        Integer prueba = listaGrupos.get(0).getEquipoList().get(0).getId();
        // 1C - 2D      30 Junio
        partidos.get(0).setEquipo1(prueba);
        partidos.get(0).setEquipo2(listaGrupos.get(0).getEquipoList().get(0).getId());
        // 1A - 2B      30 Junio
        partidos.get(1).setEquipo1(listaGrupos.get(0).getEquipoList().get(0).getId());
        partidos.get(1).setEquipo2(listaGrupos.get(0).getEquipoList().get(0).getId());
        // 1B - 2A      01 Julio
        partidos.get(2).setEquipo1(listaGrupos.get(0).getEquipoList().get(0).getId());
        partidos.get(2).setEquipo2(listaGrupos.get(0).getEquipoList().get(0).getId());
        // 1D - 2C      01 Julio
        partidos.get(3).setEquipo1(listaGrupos.get(0).getEquipoList().get(0).getId());
        partidos.get(3).setEquipo2(listaGrupos.get(0).getEquipoList().get(0).getId());
        // 1E - 2F      02 Julio
        partidos.get(4).setEquipo1(listaGrupos.get(0).getEquipoList().get(0).getId());
        partidos.get(4).setEquipo2(listaGrupos.get(0).getEquipoList().get(0).getId());
        // 1G - 2H      02 Julio
        partidos.get(5).setEquipo1(listaGrupos.get(0).getEquipoList().get(0).getId());
        partidos.get(5).setEquipo2(listaGrupos.get(0).getEquipoList().get(0).getId());
        // 1F - 2E      03 Julio
        partidos.get(6).setEquipo1(listaGrupos.get(0).getEquipoList().get(0).getId());
        partidos.get(6).setEquipo2(listaGrupos.get(0).getEquipoList().get(0).getId());
        // 1H - 2G      03 Julio
        partidos.get(7).setEquipo1(listaGrupos.get(0).getEquipoList().get(0).getId());
        partidos.get(7).setEquipo2(listaGrupos.get(0).getEquipoList().get(0).getId());
    }
    
    public void crearCuartosDeFinal () {
        // W49 - W50    06 Julio
        
        // W53 - W54    06 Julio
        
        // W55 - W56    07 Julio
        
        // W51 - W52    07 Julio
    }
    
    public void crearSemifinales () {
        // W57 - W58    10 Julio
        
        // W59 - W60    11 Julio
    }
    
    public void crearFinal() {
        // L61 - L62    14 Julio
        
        // W61 - W62    15 Julio
        
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
