/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Beans;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

/**
 *
 * @author ariel
 */
@ManagedBean
@RequestScoped
public class ErrorMessages  implements Serializable  {
    private String ErrorLogin;

    public ErrorMessages() {
    ErrorLogin="";
    }

    public String getErrorLogin() {
        return ErrorLogin;
    }

    public void setErrorLogin(String ErrorLogin) {
        this.ErrorLogin = ErrorLogin;
    }
    
    
    
        public void filterCheck(ComponentSystemEvent event) {
        try {

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            String message = (String) ec.getSessionMap().get("message");

            if (message != null) {

                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                ec.getSessionMap().remove("message");

            }

        } catch (Exception e) {
            String ee = e.getMessage();
        }

    }
    
    
}
