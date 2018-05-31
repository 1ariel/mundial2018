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
@Table(name = "resultado_hist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResultadoHist.findAll", query = "SELECT r FROM ResultadoHist r"),
    @NamedQuery(name = "ResultadoHist.findById", query = "SELECT r FROM ResultadoHist r WHERE r.id = :id"),
    @NamedQuery(name = "ResultadoHist.findByPartidosExactos", query = "SELECT r FROM ResultadoHist r WHERE r.partidosExactos = :partidosExactos"),
    @NamedQuery(name = "ResultadoHist.findByPartidosGanados", query = "SELECT r FROM ResultadoHist r WHERE r.partidosGanados = :partidosGanados"),
    @NamedQuery(name = "ResultadoHist.findByPartidosEmpatados", query = "SELECT r FROM ResultadoHist r WHERE r.partidosEmpatados = :partidosEmpatados"),
    @NamedQuery(name = "ResultadoHist.findByPuntos", query = "SELECT r FROM ResultadoHist r WHERE r.puntos = :puntos"),
    @NamedQuery(name = "ResultadoHist.findByEmpleadoId", query = "SELECT r FROM ResultadoHist r WHERE r.empleadoId = :empleadoId"),
    @NamedQuery(name = "ResultadoHist.findByFechaModificacion", query = "SELECT r FROM ResultadoHist r WHERE r.fechaModificacion = :fechaModificacion")})
public class ResultadoHist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "partidosExactos")
    private Integer partidosExactos;
    @Column(name = "partidosGanados")
    private Integer partidosGanados;
    @Column(name = "partidosEmpatados")
    private Integer partidosEmpatados;
    @Column(name = "puntos")
    private Integer puntos;
    @Column(name = "empleado_id")
    private Integer empleadoId;
    @Column(name = "fechaModificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    public ResultadoHist() {
    }

    public ResultadoHist(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartidosExactos() {
        return partidosExactos;
    }

    public void setPartidosExactos(Integer partidosExactos) {
        this.partidosExactos = partidosExactos;
    }

    public Integer getPartidosGanados() {
        return partidosGanados;
    }

    public void setPartidosGanados(Integer partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    public Integer getPartidosEmpatados() {
        return partidosEmpatados;
    }

    public void setPartidosEmpatados(Integer partidosEmpatados) {
        this.partidosEmpatados = partidosEmpatados;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
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
        if (!(object instanceof ResultadoHist)) {
            return false;
        }
        ResultadoHist other = (ResultadoHist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mundial2018.Database.Entities.ResultadoHist[ id=" + id + " ]";
    }
    
}
