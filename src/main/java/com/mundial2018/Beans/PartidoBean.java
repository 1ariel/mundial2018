/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Beans;

import com.mundial2018.Classes.ImageHelper;
import com.mundial2018.Controller.ApuestaJpaController;
import com.mundial2018.Controller.EmpleadoJpaController;
import com.mundial2018.Controller.EquipoJpaController;
import com.mundial2018.Controller.PartidoJpaController;
import com.mundial2018.Controller.ResultadoHistJpaController;
import com.mundial2018.Controller.ResultadoJpaController;
import com.mundial2018.Controller.RondaJpaController;
import com.mundial2018.Controller.exceptions.NonexistentEntityException;
import com.mundial2018.Database.Entities.Apuesta;
import com.mundial2018.Database.Entities.Empleado;
import com.mundial2018.Database.Entities.Login;
import com.mundial2018.Database.Entities.Partido;
import com.mundial2018.Database.Entities.Resultado;
import com.mundial2018.Database.Entities.ResultadoHist;
import com.mundial2018.Database.Entities.Ronda;
import com.mundial2018.Database.Persistance.EntityManagerFactoria;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.primefaces.PrimeFaces;

/**
 *
 * @author ariel
 */
@ManagedBean
@ViewScoped
public class PartidoBean implements Serializable {
    private final RondaJpaController rondajc;
    private final EquipoJpaController equipojc;
    private final EmpleadoJpaController empleadojc;
    private final PartidoJpaController partidojc;
    private final ApuestaJpaController apuestajc;
    private final ResultadoJpaController resultadojc;
    private final ResultadoHistJpaController resultadoHistjc;
    private List<Ronda> listaRondas;
    private List<Apuesta> listaApuestas;
    private Login login;
    private Partido partido;
    private GrupoBean grupoBean;

    public PartidoBean() {
        EntityManagerFactoria aux = new EntityManagerFactoria();
        EntityManagerFactory emf = aux.getEMF();
        partido = new Partido();
        rondajc = new RondaJpaController(emf);
        equipojc = new EquipoJpaController(emf);
        empleadojc = new EmpleadoJpaController(emf);
        partidojc = new PartidoJpaController(emf);
        apuestajc = new ApuestaJpaController(emf);
        resultadojc = new ResultadoJpaController(emf);
        resultadoHistjc = new ResultadoHistJpaController(emf);
    }

    @PostConstruct
    public void init() {
        listaRondas = rondajc.findRondaEntities();
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        login = (Login) ec.getSessionMap().get("login");
        grupoBean = (GrupoBean)fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "grupoBean");
    }

    public String formatearFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("EEEE d 'de' MMMM", new Locale("es", "ES"));
        formato.setTimeZone(TimeZone.getTimeZone("UTC"));

        return formato.format(fecha);
    }

    public String buscarBandera(Integer equipoId) {
        try {
            ImageHelper img = new ImageHelper();
            return img.findLocationOfFlag(equipojc.findEquipo(equipoId).getNombre());
        } catch (Exception e) {
            return "banderas/logo.png";
        }
    }

    public void asignacionDeGoles() {
        try {
            List<Partido> partidos = new ArrayList<>();
            partidos.add(partido);
            
            if(partido.getEditado()) {
                recalcularPuntosDeEmpleado(partido);
                grupoBean.recalcularPuntos(partido);
                // Obtener la lista de partidos que se necesitan recalcular
                partidos = partidojc.findPartidosByDateRange(partido.getFecha());
                calcularPuntosDeEmpleado(partidos);
                grupoBean.calcularPuntos(partidos);
            } else {
                calcularPuntosDeEmpleado(partidos);
                grupoBean.calcularPuntos(partidos);
            }

            partidojc.edit(partido);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PartidoBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PartidoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        partido = new Partido();
        listaRondas = rondajc.findRondaEntities();
        PrimeFaces.current().executeScript("PF('fasegrupo').update();");
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("msg", new FacesMessage("Partidos guardados exitosamente"));
    }

    public void recalcularPuntosDeEmpleado(Partido partido) {
        List<Empleado> empleados = empleadojc.findEmpleadoEntities();
        
        for(Empleado empleado : empleados) {
            try {
                ResultadoHist resultadoHist = resultadoHistjc.findResultadoHist(partido.getFecha(), empleado.getId());
                // Reasignar valores anteriores a Resultado
                Resultado resultado = new Resultado();
                resultado.setId(empleado.getResultado().getId());
                resultado.setPartidosExactos(resultadoHist.getPartidosExactos());
                resultado.setPartidosGanados(resultadoHist.getPartidosGanados());
                resultado.setPartidosEmpatados(resultadoHist.getPartidosEmpatados());
                resultado.setPuntos(resultadoHist.getPuntos());
                resultado.setEmpleadoId(empleado);
                empleado.setResultado(resultado);
                
                resultadojc.edit(resultado);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(PartidoBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(PartidoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void calcularPuntosDeEmpleado(List<Partido> partidos) {
        for(Partido partidoActual: partidos) {
            List<Apuesta> apuestas = apuestajc.findApuestasById(partidoActual.getId());
            
            for(Apuesta apuestaActual : apuestas) {
                Resultado resultado = resultadojc.findResultado(apuestaActual.getEmpleadoid().getResultado().getId());
                
                if(!Objects.nonNull(resultado)) {
                    resultado = new Resultado(0, 0, 0, 0);
                }
                
                resultado.setEmpleadoId(apuestaActual.getEmpleadoid());
                
                try {
                    // Adivinar marcador exacto
                    if(apuestaActual.getGolesEquipo1().equals(partidoActual.getGolesEquipo1()) &&
                        apuestaActual.getGolesEquipo2().equals(partidoActual.getGolesEquipo2())) {
                        resultado.setPartidosExactos(+1);
                        resultado.setPuntos(+3);
                    }
                    // Adivinar que el equipo 1 gana
                    if(apuestaActual.getGolesEquipo1() > apuestaActual.getGolesEquipo2() &&
                            partidoActual.getGolesEquipo1() > partidoActual.getGolesEquipo2()) {
                        resultado.setPartidosGanados(+1);
                        resultado.setPuntos(+1);
                        // Adivinar que el equipo 2 gana
                    } else if (apuestaActual.getGolesEquipo1() < apuestaActual.getGolesEquipo2() &&
                            partidoActual.getGolesEquipo1() < partidoActual.getGolesEquipo2()){
                        resultado.setPartidosGanados(+1);
                        resultado.setPuntos(+1);
                    }
                    // Adivinar empate
                    if(apuestaActual.getGolesEquipo1().equals(apuestaActual.getGolesEquipo2()) &&
                            partidoActual.getGolesEquipo1().equals(partidoActual.getGolesEquipo2())) {
                        resultado.setPartidosEmpatados(+1);
                        resultado.setPuntos(+1);
                    }
                    
                    // Actualizar o insertar resultado
                    if(partidoActual.getEditado()) {
                        resultadojc.edit(resultado);
                    } else {
                        resultadojc.create(resultado);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ApuestaBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public Boolean isPartidoActive(Partido p) {
        DateTimeZone zoneUTC = DateTimeZone.UTC;
        DateTime dt = new DateTime(new Date(p.getFecha().getTime()));
        DateTime actualDateOnDB = dt.toDateTime(zoneUTC);
        DateTime dateTime = new DateTime(DateTime.now());
        boolean test = dateTime.toDate().before(actualDateOnDB.toDate());

        return test;
    }

    public void findPartido(Partido p) {
        partido = partidojc.findPartido(p.getId());
        PrimeFaces.current().executeScript("PF('assignacionPartidoDialog').show();");
    }

    public String getEquipoName(int equipoID) {
        String nombre;
        
        try {
            nombre = equipojc.findEquipo(equipoID).getNombre();
        } catch (Exception e) {
            nombre = " ";
        }
        
        return nombre;
    }

    public List<Ronda> getListaRondas() {
        return listaRondas;
    }

    public void setListaRondas(List<Ronda> listaRondas) {
        this.listaRondas = listaRondas;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
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

    /**
     * @return the listaApuestas
     */
    public List<Apuesta> getListaApuestas() {
        return listaApuestas;
    }

    /**
     * @param listaApuestas the listaApuestas to set
     */
    public void setListaApuestas(List<Apuesta> listaApuestas) {
        this.listaApuestas = listaApuestas;
    }

    /**
     * @return the grupoBean
     */
    public GrupoBean getGrupoBean() {
        return grupoBean;
    }

    /**
     * @param grupoBean the grupoBean to set
     */
    public void setGrupoBean(GrupoBean grupoBean) {
        this.grupoBean = grupoBean;
    }

}
