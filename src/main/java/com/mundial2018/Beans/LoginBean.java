/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Beans;

import com.mundial2018.Controller.LoginJpaController;
import com.mundial2018.Database.Entities.Login;
import com.mundial2018.Database.Persistance.EntityManagerFactoria;
import com.mundial2018.Database.Persistance.LoginDBAccess;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ariel
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private String secondPassword;
    private String role;
    private Login login;
    private final LoginJpaController ljc;

    public LoginBean() {
        login = new Login();
        EntityManagerFactoria aux = new EntityManagerFactoria();

        EntityManagerFactory emf = aux.getEMF();
        ljc = new LoginJpaController(emf);

        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
        } catch (Exception e) {
        }

    }

    public String loguearse() {
        LoginDBAccess loginDB = new LoginDBAccess();

        if (loginDB.checkLogin(username, password)) {
            return "Secure/Homepage.xhtml?faces-redirect=true";
        } else {
            return "Login.xhtml";
        }
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

    public String iniciarSesion() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        Login nuevo = ljc.checkLogin(login);

        if (Objects.nonNull(nuevo.getLoginPK())) {
            login = nuevo;

            if (Objects.nonNull(login.getEmpleado())) {
                ec.getFlash().setKeepMessages(true);
                fc.addMessage("msg", new FacesMessage("Bienvenido " + login.getEmpleado().getNombre()));
                ec.getSessionMap().put("login", login);

            } else {
                fc.addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario debe estar asociado a un Empleado", null));
            }

            return "Secure/Homepage.xhtml?faces-redirect=true";
        } else {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario y/o contraseña incorrectos", login.getUsername()));

            return "Login.xhtml";
        }
    }

    public String cerrarSesion() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        fc.addMessage(null, new FacesMessage("Sesión finalizada", login.getUsername()));
        ec.getFlash().setKeepMessages(true);
        ec.invalidateSession();
        login = new Login();

        return "/Login.xhtml?faces-redirect=true";
    }

    public void resetearPassword() {

        //Comprobar que la password que ingreso es la misma
        if (!this.getPassword().matches(this.getSecondPassword())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden", null));
            return;
        }

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        Login loginAux = (Login) ec.getSessionMap().get("login");

        loginAux = ljc.findLogin(loginAux.getLoginPK());

        loginAux.setPassword(password);

        try {
            ljc.edit(loginAux);
             fc.addMessage(null, new FacesMessage("Contraseña actualizada exitosamente"));

        } catch (Exception ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        loginAux = null;

    }

    public boolean isThisAdmin() {
        return this.login.getRol().equalsIgnoreCase("admin");
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

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }

    /**
     * @return the login
     */
    public Login getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(Login login) {
        this.login = login;
    }
}
