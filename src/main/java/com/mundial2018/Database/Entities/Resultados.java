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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author WVQ
 */
@Entity
@Table(name = "resultados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resultados.findAll", query = "SELECT r FROM Resultados r"),
    @NamedQuery(name = "Resultados.findById", query = "SELECT r FROM Resultados r WHERE r.id = :id"),
    @NamedQuery(name = "Resultados.findByGanados", query = "SELECT r FROM Resultados r WHERE r.ganados = :ganados"),
    @NamedQuery(name = "Resultados.findByPerdidos", query = "SELECT r FROM Resultados r WHERE r.perdidos = :perdidos"),
    @NamedQuery(name = "Resultados.findByEmpatados", query = "SELECT r FROM Resultados r WHERE r.empatados = :empatados"),
    @NamedQuery(name = "Resultados.findByPuntos", query = "SELECT r FROM Resultados r WHERE r.puntos = :puntos")})
public class Resultados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ganados")
    private Integer ganados;
    @Column(name = "perdidos")
    private Integer perdidos;
    @Column(name = "empatados")
    private Integer empatados;
    @Column(name = "puntos")
    private Integer puntos;
    @JoinColumn(name = "Empleado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado empleadoid;

    public Resultados() {
    }

    public Resultados(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGanados() {
        return ganados;
    }

    public void setGanados(Integer ganados) {
        this.ganados = ganados;
    }

    public Integer getPerdidos() {
        return perdidos;
    }

    public void setPerdidos(Integer perdidos) {
        this.perdidos = perdidos;
    }

    public Integer getEmpatados() {
        return empatados;
    }

    public void setEmpatados(Integer empatados) {
        this.empatados = empatados;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Empleado getEmpleadoid() {
        return empleadoid;
    }

    public void setEmpleadoid(Empleado empleadoid) {
        this.empleadoid = empleadoid;
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
        if (!(object instanceof Resultados)) {
            return false;
        }
        Resultados other = (Resultados) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mundial2018.Database.Entities.Resultados[ id=" + id + " ]";
    }
    
}
