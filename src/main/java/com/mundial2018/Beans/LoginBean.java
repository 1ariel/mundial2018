/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Beans;

import com.mundial2018.Database.Persistance.LoginDBAccess;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ariel
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;
    private String role;

    public LoginBean() {
    }
    
    public String logearse(){
        
       LoginDBAccess loginDB =new LoginDBAccess();
       
       if(loginDB.checkLogin(username,password))       { 
       
           return "Secure/Homepage.xhtml?faces-redirect=true";
            
       }
       else
           return "Login.xhtml";
    }
    
    
    
    
    
    
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
    
    
}
