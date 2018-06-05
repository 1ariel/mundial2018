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
import javax.persistence.Lob;
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
@Table(name = "equipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipo.findAll", query = "SELECT e FROM Equipo e"),
    @NamedQuery(name = "Equipo.findById", query = "SELECT e FROM Equipo e WHERE e.id = :id"),
    @NamedQuery(name = "Equipo.findByNombre", query = "SELECT e FROM Equipo e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Equipo.findByJugados", query = "SELECT e FROM Equipo e WHERE e.jugados = :jugados"),
    @NamedQuery(name = "Equipo.findByGanados", query = "SELECT e FROM Equipo e WHERE e.ganados = :ganados"),
    @NamedQuery(name = "Equipo.findByPerdidos", query = "SELECT e FROM Equipo e WHERE e.perdidos = :perdidos"),
    @NamedQuery(name = "Equipo.findByEmpatados", query = "SELECT e FROM Equipo e WHERE e.empatados = :empatados"),
    @NamedQuery(name = "Equipo.findByGolesFavor", query = "SELECT e FROM Equipo e WHERE e.golesFavor = :golesFavor"),
    @NamedQuery(name = "Equipo.findByGolesContra", query = "SELECT e FROM Equipo e WHERE e.golesContra = :golesContra"),
    @NamedQuery(name = "Equipo.findByPuntos", query = "SELECT e FROM Equipo e WHERE e.puntos = :puntos")})
public class Equipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "jugados")
    private Integer jugados;
    @Column(name = "ganados")
    private Integer ganados;
    @Column(name = "perdidos")
    private Integer perdidos;
    @Column(name = "empatados")
    private Integer empatados;
    @Column(name = "golesFavor")
    private Integer golesFavor;
    @Column(name = "golesContra")
    private Integer golesContra;
    @Column(name = "puntos")
    private Integer puntos;
    @JoinColumn(name = "Grupo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Grupo grupoid;

    public Equipo() {
    }

    public Equipo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Integer getJugados() {
        return jugados;
    }

    public void setJugados(Integer jugados) {
        this.jugados = jugados;
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

    public Integer getGolesFavor() {
        return golesFavor;
    }

    public void setGolesFavor(Integer golesFavor) {
        this.golesFavor = golesFavor;
    }

    public Integer getGolesContra() {
        return golesContra;
    }

    public void setGolesContra(Integer golesContra) {
        this.golesContra = golesContra;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Grupo getGrupoid() {
        return grupoid;
    }

    public void setGrupoid(Grupo grupoid) {
        this.grupoid = grupoid;
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
        if (!(object instanceof Equipo)) {
            return false;
        }
        Equipo other = (Equipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mundial2018.Database.Entities.Equipo[ id=" + id + " ]";
    }
    
}
