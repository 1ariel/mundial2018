/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Database.Persistance;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
/**
 *
 * @author ariel
 */
public class LoginDBAccess extends EntityManagerFactoria{
    
    public boolean checkLogin(String username, String Password){
       EntityManager em =  getEMF().createEntityManager();      
       
       /*
       Object x = em.find(LoginEntity.class, 1);
       */
       
       List x = em.createNamedQuery("User.CheckLogin")
               .setParameter("password",Password)
               .setParameter("user", username).getResultList();
       
       
        if (Objects.nonNull(x) && !x.isEmpty()) {
            return true;
        }
        
        
        
       return false;
    }
    
    
}
