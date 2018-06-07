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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author WVQ
 */
@Entity
@Table(name = "resultado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resultado.findAll", query = "SELECT r FROM Resultado r"),
    @NamedQuery(name = "Resultado.findById", query = "SELECT r FROM Resultado r WHERE r.id = :id"),
    @NamedQuery(name = "Resultado.findByPartidosExactos", query = "SELECT r FROM Resultado r WHERE r.partidosExactos = :partidosExactos"),
    @NamedQuery(name = "Resultado.findByPartidosGanados", query = "SELECT r FROM Resultado r WHERE r.partidosGanados = :partidosGanados"),
    @NamedQuery(name = "Resultado.findByPartidosEmpatados", query = "SELECT r FROM Resultado r WHERE r.partidosEmpatados = :partidosEmpatados"),
    @NamedQuery(name = "Resultado.findByEmpleadoId", query = "SELECT r FROM Resultado r WHERE r.empleadoId.id = :empleadoId"),
    @NamedQuery(name = "Resultado.findAllByPuntosSort", query = "SELECT r FROM Resultado r  order by r.puntos desc"),
    @NamedQuery(name = "Resultado.findByPuntos", query = "SELECT r FROM Resultado r WHERE r.puntos = :puntos")})
public class Resultado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
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
    @JoinColumn(name = "empleado_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Empleado empleadoId;

    public Resultado() {
    }

    public Resultado(Integer id) {
        this.id = id;
    }
    
    public Resultado (Integer pExactos, Integer pGanados, Integer pEmpatados, Integer pPuntos) {
        partidosExactos = pExactos;
        partidosGanados = pGanados;
        partidosEmpatados = pEmpatados;
        puntos = pPuntos;
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

    public Empleado getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Empleado empleadoId) {
        this.empleadoId = empleadoId;
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
        if (!(object instanceof Resultado)) {
            return false;
        }
        Resultado other = (Resultado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mundial2018.Database.Entities.Resultado[ id=" + id + " ]";
    }
    
}
