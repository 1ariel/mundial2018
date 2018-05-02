/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Database.Persistance;

import com.mundial2018.Database.Entities.Empleado;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ariel
 */
public class EmpleadoDBA extends EntityManagerFactoria{
    
    public List<Empleado> getAllEmpleados(){
        List<Empleado> listEmp = new ArrayList();
        
      listEmp = getEMF().createEntityManager().createNamedQuery("Empleado.findAll").getResultList();
      
             
        return listEmp;
    }
    
    
}
