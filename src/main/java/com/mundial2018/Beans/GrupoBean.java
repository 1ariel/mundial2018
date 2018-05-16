/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Beans;

import com.mundial2018.Controller.GrupoJpaController;
import com.mundial2018.Database.Entities.Grupo;
import java.util.List;
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
public class GrupoBean {
    private List<Grupo> listaGrupos;
    private final GrupoJpaController gjc;

    public GrupoBean() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mundial2018_Mundial2018_war_1.0-SNAPSHOTPU");
        gjc = new GrupoJpaController(emf);
    }
    
    @PostConstruct
    public void init() {
        listaGrupos = gjc.findGrupoEntities();
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
