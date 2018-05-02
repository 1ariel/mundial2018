/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Controller;

import com.mundial2018.Database.Entities.Empleado;
import com.mundial2018.Database.Persistance.EmpleadoDBA;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ariel
 */
@ManagedBean
@SessionScoped
public class empleadosController implements Serializable {

    /**
     * Creates a new instance of empleados
     */
    
    public empleadosController() {}
     List<Empleado> emp = new ArrayList<Empleado>();
    
     @PostConstruct
    public void init() { 
       EmpleadoDBA empDB = new EmpleadoDBA();
       emp= empDB.getAllEmpleados(); 
    }
    
      public void lolol() {
         Empleado emp2 = new Empleado();
    emp2.setNombre("test");
    emp.add(emp2);
    }
   
    public List<Empleado> getAllEmpleados(){
   

    
    return emp;
    
    }
    
    public List<String> getAllEmpleados2(){
    List<String> aux = new ArrayList<String>();
    aux.add("test");
    
    return aux;
    
    }
    
}
