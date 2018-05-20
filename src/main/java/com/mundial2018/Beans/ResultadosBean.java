/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Beans;

import com.mundial2018.Controller.ResultadoJpaController;
import com.mundial2018.Database.Entities.Resultado;
import com.mundial2018.Database.Persistance.EntityManagerFactoria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ariel
 */
@ManagedBean
@SessionScoped
public class ResultadosBean implements Serializable {

    private List<Resultado> listResultados;
    private ResultadoJpaController rjc;

    public ResultadosBean() {
        listResultados = new ArrayList<>();
        EntityManagerFactoria aux = new EntityManagerFactoria();

        EntityManagerFactory emf = aux.getEMF();

        rjc = new ResultadoJpaController(emf);
    }

    @PostConstruct
    public void init() {
        listResultados = rjc.getEntityManager().createNamedQuery("Resultado.findAllByPuntosSort").getResultList();
        
        
    }

    public List<Resultado> getListResultados() {
        return listResultados;
    }

    public void setListResultados(List<Resultado> listResultados) {
        this.listResultados = listResultados;
    }

}
