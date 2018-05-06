/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Database.Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author WVQ
 */
@Embeddable
public class LoginPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Empleado_id")
    private int empleadoid;

    public LoginPK() {
    }

    public LoginPK(int id, int empleadoid) {
        this.id = id;
        this.empleadoid = empleadoid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpleadoid() {
        return empleadoid;
    }

    public void setEmpleadoid(int empleadoid) {
        this.empleadoid = empleadoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) empleadoid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoginPK)) {
            return false;
        }
        LoginPK other = (LoginPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.empleadoid != other.empleadoid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mundial2018.Database.Entities.LoginPK[ id=" + id + ", empleadoid=" + empleadoid + " ]";
    }
    
}
