/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Beans;

import com.mundial2018.Classes.ImageHelper;
import com.mundial2018.Controller.ApuestaJpaController;
import com.mundial2018.Controller.EquipoJpaController;
import com.mundial2018.Controller.PartidoJpaController;
import com.mundial2018.Controller.ResultadoJpaController;
import com.mundial2018.Controller.RondaJpaController;
import com.mundial2018.Controller.exceptions.NonexistentEntityException;
import com.mundial2018.Database.Entities.Apuesta;
import com.mundial2018.Database.Entities.Login;
import com.mundial2018.Database.Entities.Partido;
import com.mundial2018.Database.Entities.Ronda;
import com.mundial2018.Database.Persistance.EntityManagerFactoria;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
    private final RondaJpaController rjc;
    private final EquipoJpaController ejc;
    private final PartidoJpaController pjc;
    private final ApuestaJpaController ajc;
    private final ResultadoJpaController rejc;
    private List<Ronda> listaRondas;
    private List<Apuesta> listaApuestas;
    private Login login;
    private Partido partido;

    public PartidoBean() {
        EntityManagerFactoria aux = new EntityManagerFactoria();
        EntityManagerFactory emf = aux.getEMF();
        partido = new Partido();
        rjc = new RondaJpaController(emf);
        ejc = new EquipoJpaController(emf);
        pjc = new PartidoJpaController(emf);
        ajc = new ApuestaJpaController(emf);
        rejc = new ResultadoJpaController(emf);
    }

    @PostConstruct
    public void init() {
        listaRondas = rjc.findRondaEntities();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        login = (Login) ec.getSessionMap().get("login");
    }

    public String formatearFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("EEEE d 'de' MMMM", new Locale("es", "ES"));
        formato.setTimeZone(TimeZone.getTimeZone("UTC"));

        return formato.format(fecha);
    }

    public String buscarBandera(Integer equipoId) {
        try {
            ImageHelper img = new ImageHelper();
            return img.findLocationOfFlag(ejc.findEquipo(equipoId).getNombre());
        } catch (Exception e) {
            return "banderas/logo.png";
        }
    }

    public void asignacionDeGoles() {
        try {
            
            pjc.edit(partido);

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PartidoBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PartidoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.partido = new Partido();
        listaRondas = rjc.findRondaEntities();
        PrimeFaces.current().executeScript("PF('fasegrupo').update();");
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("msg", new FacesMessage("Partidos guardados exitosamente "));
    }

    public void calcularPuntosDeEmpleado(List<Partido> partidos) {
        for(Partido partidoActual: partidos) {
            List<Apuesta> apuestas = ajc.findApuestasById(partidoActual.getId());
            
            for(Apuesta apuestaActual : apuestas) {
                try {
                    // Adivinar marcador exacto
                    if(apuestaActual.getGolesEquipo1().equals(partidoActual.getGolesEquipo1()) &&
                        apuestaActual.getGolesEquipo2().equals(partidoActual.getGolesEquipo2())) {
                        apuestaActual.getEmpleadoid().getResultado().setPartidosExactos(+1);
                        apuestaActual.getEmpleadoid().getResultado().setPuntos(+3);
                    }
                    // Adivinar que el equipo 1 gana
                    if(apuestaActual.getGolesEquipo1() > apuestaActual.getGolesEquipo2() &&
                            partidoActual.getGolesEquipo1() > partidoActual.getGolesEquipo2()) {
                        apuestaActual.getEmpleadoid().getResultado().setPartidosGanados(+1);
                        apuestaActual.getEmpleadoid().getResultado().setPuntos(+1);
                        // Adivinar que el equipo 2 gana
                    } else if (apuestaActual.getGolesEquipo1() < apuestaActual.getGolesEquipo2() &&
                            partidoActual.getGolesEquipo1() < partidoActual.getGolesEquipo2()){
                        apuestaActual.getEmpleadoid().getResultado().setPartidosGanados(+1);
                        apuestaActual.getEmpleadoid().getResultado().setPuntos(+1);
                    }
                    // Adivinar empate
                    if(apuestaActual.getGolesEquipo1().equals(apuestaActual.getGolesEquipo2()) &&
                            partidoActual.getGolesEquipo1().equals(partidoActual.getGolesEquipo2())) {
                        apuestaActual.getEmpleadoid().getResultado().setPartidosEmpatados(+1);
                        apuestaActual.getEmpleadoid().getResultado().setPuntos(+1);
                    }
                    
                    rejc.edit(apuestaActual.getEmpleadoid().getResultado());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ApuestaBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ApuestaBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void recalcularPuntosDeEmpleado() {}
    
    public Boolean isPartidoActive(Partido p) {
        DateTimeZone zoneUTC = DateTimeZone.UTC;
        DateTime dt = new DateTime(new Date(p.getFecha().getTime()));
        DateTime actualDateOnDB = dt.toDateTime(zoneUTC);
        DateTime dateTime = new DateTime(DateTime.now());
        boolean test = dateTime.toDate().before(actualDateOnDB.toDate());

        return test;
    }

    public void findPartido(Partido p) {
        partido = pjc.findPartido(p.getId());
        PrimeFaces.current().executeScript("PF('assignacionPartidoDialog').show();");
    }

    public String getEquipoName(int equipoID) {
        String nombre;
        
        try {
            nombre = ejc.findEquipo(equipoID).getNombre();
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

}
