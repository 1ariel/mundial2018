/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Database.Entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author WVQ
 */
@Entity
@Table(name = "login")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Login.findAll", query = "SELECT l FROM Login l"),
    @NamedQuery(name = "Login.findById", query = "SELECT l FROM Login l WHERE l.loginPK.id = :id"),
    @NamedQuery(name = "Login.findByUser", query = "SELECT l FROM Login l WHERE l.username = :username"),
    @NamedQuery(name = "Login.findByPassword", query = "SELECT l FROM Login l WHERE l.password = :password"),
    @NamedQuery(name = "Login.CheckLogin", query = "SELECT l FROM Login l WHERE l.password = :password AND l.username = :username"),
    @NamedQuery(name = "Login.findByRol", query = "SELECT l FROM Login l WHERE l.rol = :rol"),
    @NamedQuery(name = "Login.findByEmpleadoid", query = "SELECT l FROM Login l WHERE l.loginPK.empleadoid = :empleadoid")})
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LoginPK loginPK;
    @Size(max = 45)
    @Column(name = "username")
    private String username;
    @Size(max = 45)
    @Column(name = "password")
    private String password;
    @Size(max = 45)
    @Column(name = "rol")
    private String rol;
    @JoinColumn(name = "Empleado_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empleado empleado;

    public Login() {}

    public Login(LoginPK loginPK) {
        this.loginPK = loginPK;
    }

    public Login(int id, int empleadoid) {
        this.loginPK = new LoginPK(id, empleadoid);
    }

    public LoginPK getLoginPK() {
        return loginPK;
    }

    public void setLoginPK(LoginPK loginPK) {
        this.loginPK = loginPK;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginPK != null ? loginPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Login)) {
            return false;
        }
        Login other = (Login) object;
        if ((this.loginPK == null && other.loginPK != null) || (this.loginPK != null && !this.loginPK.equals(other.loginPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mundial2018.Database.Entities.Login[ loginPK=" + loginPK + " ]";
    }
    
}
