/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Database.Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author WVQ
 */
@Entity
@Table(name = "equipo_hist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EquipoHist.findAll", query = "SELECT e FROM EquipoHist e"),
    @NamedQuery(name = "EquipoHist.findById", query = "SELECT e FROM EquipoHist e WHERE e.id = :id"),
    @NamedQuery(name = "EquipoHist.findByJugados", query = "SELECT e FROM EquipoHist e WHERE e.jugados = :jugados"),
    @NamedQuery(name = "EquipoHist.findByGanados", query = "SELECT e FROM EquipoHist e WHERE e.ganados = :ganados"),
    @NamedQuery(name = "EquipoHist.findByPerdidos", query = "SELECT e FROM EquipoHist e WHERE e.perdidos = :perdidos"),
    @NamedQuery(name = "EquipoHist.findByEmpatados", query = "SELECT e FROM EquipoHist e WHERE e.empatados = :empatados"),
    @NamedQuery(name = "EquipoHist.findByGolesFavor", query = "SELECT e FROM EquipoHist e WHERE e.golesFavor = :golesFavor"),
    @NamedQuery(name = "EquipoHist.findByGolesContra", query = "SELECT e FROM EquipoHist e WHERE e.golesContra = :golesContra"),
    @NamedQuery(name = "EquipoHist.findByPuntos", query = "SELECT e FROM EquipoHist e WHERE e.puntos = :puntos"),
    @NamedQuery(name = "EquipoHist.findByGrupoId", query = "SELECT e FROM EquipoHist e WHERE e.grupoId = :grupoId"),
    @NamedQuery(name = "EquipoHist.findByFechaModificacion", query = "SELECT e FROM EquipoHist e WHERE e.fechaModificacion = :fechaModificacion")})
public class EquipoHist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
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
    @Column(name = "grupo_id")
    private Integer grupoId;
    @Column(name = "fechaModificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    public EquipoHist() {
    }

    public EquipoHist(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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
        if (!(object instanceof EquipoHist)) {
            return false;
        }
        EquipoHist other = (EquipoHist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mundial2018.Database.Entities.EquipoHist[ id=" + id + " ]";
    }
    
}
