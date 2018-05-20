/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Beans;

import com.mundial2018.Controller.EquipoJpaController;
import com.mundial2018.Controller.GrupoJpaController;
import com.mundial2018.Database.Entities.Equipo;
import com.mundial2018.Database.Entities.Grupo;
import com.mundial2018.Database.Entities.Login;
import com.mundial2018.Database.Entities.Partido;
import java.text.SimpleDateFormat;
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

    public GrupoBean() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mundial2018_Mundial2018_war_1.0-SNAPSHOTPU");
        gjc = new GrupoJpaController(emf);
        ejc = new EquipoJpaController(emf);
    }
    
    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        login = (Login) ec.getSessionMap().get("login");
        listaGrupos = gjc.findGrupoEntities();
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
    
    public void crearOctavosDeFinal () {
        
    }
    
    public void crearCuartosDeFinal () {
    
    }
    
    public void crearSemifinales () {
    
    }
    
    public void crearFinal() {
    
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
