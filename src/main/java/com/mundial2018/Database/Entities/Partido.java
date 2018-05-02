/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Database.Entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ariel
 */
@Entity
@Table(name = "Partido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partido.findAll", query = "SELECT p FROM Partido p")
    , @NamedQuery(name = "Partido.findById", query = "SELECT p FROM Partido p WHERE p.id = :id")
    , @NamedQuery(name = "Partido.findByFecha", query = "SELECT p FROM Partido p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Partido.findByLugar", query = "SELECT p FROM Partido p WHERE p.lugar = :lugar")
    , @NamedQuery(name = "Partido.findByEquipo1", query = "SELECT p FROM Partido p WHERE p.equipo1 = :equipo1")
    , @NamedQuery(name = "Partido.findByGolesEquipo1", query = "SELECT p FROM Partido p WHERE p.golesEquipo1 = :golesEquipo1")
    , @NamedQuery(name = "Partido.findByEquipo2", query = "SELECT p FROM Partido p WHERE p.equipo2 = :equipo2")
    , @NamedQuery(name = "Partido.findByGolesEquipo2", query = "SELECT p FROM Partido p WHERE p.golesEquipo2 = :golesEquipo2")})
public class Partido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 45)
    @Column(name = "lugar")
    private String lugar;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partidoid")
    private Collection<Equipo> equipoCollection;
    @JoinColumn(name = "Ronda_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ronda rondaid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partidoid")
    private Collection<Apuesta> apuestaCollection;

    public Partido() {
    }

    public Partido(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
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

    @XmlTransient
    public Collection<Equipo> getEquipoCollection() {
        return equipoCollection;
    }

    public void setEquipoCollection(Collection<Equipo> equipoCollection) {
        this.equipoCollection = equipoCollection;
    }

    public Ronda getRondaid() {
        return rondaid;
    }

    public void setRondaid(Ronda rondaid) {
        this.rondaid = rondaid;
    }

    @XmlTransient
    public Collection<Apuesta> getApuestaCollection() {
        return apuestaCollection;
    }

    public void setApuestaCollection(Collection<Apuesta> apuestaCollection) {
        this.apuestaCollection = apuestaCollection;
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
        if (!(object instanceof Partido)) {
            return false;
        }
        Partido other = (Partido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mundial2018.Database.Entities.Partido[ id=" + id + " ]";
    }
    
}
