/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Beans;

import com.mundial2018.Controller.RondaJpaController;
import com.mundial2018.Database.Entities.Apuesta;
import com.mundial2018.Database.Entities.Ronda;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author WVQ
 */
@ManagedBean
@ViewScoped
public class ApuestaBean {
    private Ronda ronda;
    private Apuesta apuesta;
    private List<Ronda> listaRondas;
    private final RondaJpaController rjc;
    
    public ApuestaBean() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mundial2018_Mundial2018_war_1.0-SNAPSHOTPU");
        rjc = new RondaJpaController(emf);
    }
    
    public String formatearFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("EEEE d 'de' MMMM", new Locale("es", "ES"));
        
        return formato.format(fecha);
    }
    
    public void agregarApuesta(int rondaId) {
        
    }

    @PostConstruct
    public void init() {
        listaRondas = rjc.findRondaEntities();
    }
    
    /**
     * @return the ronda
     */
    public Ronda getRonda() {
        return ronda;
    }

    /**
     * @param ronda the ronda to set
     */
    public void setRonda(Ronda ronda) {
        this.ronda = ronda;
    }

    /**
     * @return the apuesta
     */
    public Apuesta getApuesta() {
        return apuesta;
    }

    /**
     * @param apuesta the apuesta to set
     */
    public void setApuesta(Apuesta apuesta) {
        this.apuesta = apuesta;
    }

    /**
     * @return the listaRondas
     */
    public List getListaRondas() {
        return rjc.findRondaEntities();
    }

    /**
     * @param listaRondas the listaRondas to set
     */
    public void setListaRondas(List listaRondas) {
        this.listaRondas = listaRondas;
    }
}