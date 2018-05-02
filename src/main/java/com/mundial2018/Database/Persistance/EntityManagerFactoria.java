/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Database.Persistance;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ariel
 */
public class EntityManagerFactoria {
private EntityManagerFactory emf;    

    public EntityManagerFactory getEMF (){
    if (emf == null){
        emf = Persistence.createEntityManagerFactory("com.mundial2018_Mundial2018_war_1.0-SNAPSHOTPU");
    }
    return emf;
}
}
