/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Beans;

import com.mundial2018.Controller.ResultadoJpaController;
import com.mundial2018.Database.Entities.Login;
import com.mundial2018.Database.Entities.Resultado;
import com.mundial2018.Database.Persistance.EntityManagerFactoria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ariel
 */
@ManagedBean
@RequestScoped
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

    public Resultado getResultForUser(){
 
        try{
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        Login login = (Login) ec.getSessionMap().get("login");
        
        Resultado resu = new Resultado();
       
        
        }
        catch(Exception e){
        return null;
        }
        
    
    return null;
    }
    
    public List<Resultado> getListResultados() {
        return listResultados;
    }

    public void setListResultados(List<Resultado> listResultados) {
        this.listResultados = listResultados;
    }

}
