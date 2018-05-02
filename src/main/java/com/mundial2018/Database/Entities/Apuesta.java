/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Database.Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ariel
 */
@Entity
@Table(name = "Apuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apuesta.findAll", query = "SELECT a FROM Apuesta a")
    , @NamedQuery(name = "Apuesta.findById", query = "SELECT a FROM Apuesta a WHERE a.id = :id")
    , @NamedQuery(name = "Apuesta.findByEquipo1", query = "SELECT a FROM Apuesta a WHERE a.equipo1 = :equipo1")
    , @NamedQuery(name = "Apuesta.findByGolesEquipo1", query = "SELECT a FROM Apuesta a WHERE a.golesEquipo1 = :golesEquipo1")
    , @NamedQuery(name = "Apuesta.findByEquipo2", query = "SELECT a FROM Apuesta a WHERE a.equipo2 = :equipo2")
    , @NamedQuery(name = "Apuesta.findByGolesEquipo2", query = "SELECT a FROM Apuesta a WHERE a.golesEquipo2 = :golesEquipo2")})
public class Apuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "equipo1")
    private String equipo1;
    @Column(name = "golesEquipo1")
    private Integer golesEquipo1;
    @Size(max = 45)
    @Column(name = "equipo2")
    private String equipo2;
    @Column(name = "golesEquipo2")
    private Integer golesEquipo2;
    @JoinColumn(name = "Empleado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado empleadoid;
    @JoinColumn(name = "Partido_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Partido partidoid;

    public Apuesta() {
    }

    public Apuesta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(String equipo1) {
        this.equipo1 = equipo1;
    }

    public Integer getGolesEquipo1() {
        return golesEquipo1;
    }

    public void setGolesEquipo1(Integer golesEquipo1) {
        this.golesEquipo1 = golesEquipo1;
    }

    public String getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(String equipo2) {
        this.equipo2 = equipo2;
    }

    public Integer getGolesEquipo2() {
        return golesEquipo2;
    }

    public void setGolesEquipo2(Integer golesEquipo2) {
        this.golesEquipo2 = golesEquipo2;
    }

    public Empleado getEmpleadoid() {
        return empleadoid;
    }

    public void setEmpleadoid(Empleado empleadoid) {
        this.empleadoid = empleadoid;
    }

    public Partido getPartidoid() {
        return partidoid;
    }

    public void setPartidoid(Partido partidoid) {
        this.partidoid = partidoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apuesta)) {
            return false;
        }
        Apuesta other = (Apuesta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mundial2018.Database.Entities.Apuesta[ id=" + id + " ]";
    }
    
}
