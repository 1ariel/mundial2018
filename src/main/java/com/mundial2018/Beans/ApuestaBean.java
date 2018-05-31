/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Beans;

import com.mundial2018.Classes.ImageHelper;
import com.mundial2018.Controller.ApuestaJpaController;
import com.mundial2018.Controller.EquipoJpaController;
import com.mundial2018.Controller.ResultadoJpaController;
import com.mundial2018.Controller.RondaJpaController;
import com.mundial2018.Controller.exceptions.NonexistentEntityException;
import com.mundial2018.Database.Entities.Apuesta;
import com.mundial2018.Database.Entities.Empleado;
import com.mundial2018.Database.Entities.Login;
import com.mundial2018.Database.Entities.Partido;
import com.mundial2018.Database.Entities.Ronda;
import com.mundial2018.Database.Persistance.EntityManagerFactoria;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
 * @author WVQ
 */
@ManagedBean
@ViewScoped
public class ApuestaBean {
    private Login login;
    private Ronda ronda;
    private Apuesta apuesta;
    private Empleado empleado;
    private List<Apuesta> listaApuestas;
    private List<Ronda> listaRondas;
    private List<Partido> listaPartidos;
    private final RondaJpaController rjc;
    private final EquipoJpaController ejc;
    private final ApuestaJpaController ajc;
    private final ResultadoJpaController rejc;
    
    public ApuestaBean() {
        EntityManagerFactoria aux = new EntityManagerFactoria();
        EntityManagerFactory emf = aux.getEMF();
        rjc = new RondaJpaController(emf);
        ejc = new EquipoJpaController(emf);
        ajc = new ApuestaJpaController(emf);
        rejc = new ResultadoJpaController(emf);
    }

    @PostConstruct
    public void init() {
        apuesta = new Apuesta();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        login = (Login) ec.getSessionMap().get("login");
        empleado = login.getEmpleado();
        listaRondas = rjc.findRondaEntities();
    }

    public String formatearFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("EEEE d 'de' MMMM", new Locale("es", "ES"));
        formato.setTimeZone(TimeZone.getTimeZone("UTC"));

        return formato.format(fecha);
    }

    public String buscarBandera(Integer equipoId) {
        ImageHelper img = new ImageHelper();
        return img.findLocationOfFlag(ejc.findEquipo(equipoId).getNombre());

    }

    public String buscarEquipo(Integer equipoId) {
        return (String) ejc.findEquipo(equipoId).getNombre();
    }

    public void onTabChange() {
        listaRondas = rjc.findRondaEntities();
    }

    public void asignarEquipos(Partido partido) {
        apuesta.setPartidoId(partido);
        apuesta.setEmpleadoid(empleado);
        apuesta.setEquipo1(ejc.findEquipo(partido.getEquipo1()).getNombre());
        apuesta.setEquipo2(ejc.findEquipo(partido.getEquipo2()).getNombre());

        //find if the user already made a bet on it 
        Apuesta existeApuesta = ajc.findViaEmpleadoAndPartido(apuesta.getEmpleadoid(), apuesta.getPartidoId());
        if (existeApuesta != null) {
            apuesta.setGolesEquipo1(existeApuesta.getGolesEquipo1());
            apuesta.setGolesEquipo2(existeApuesta.getGolesEquipo2());
        } else {
            apuesta.setGolesEquipo1(0);
            apuesta.setGolesEquipo2(0);
        }
        //end find
        PrimeFaces.current().executeScript("PF('apuestaPartidoDialog').show();");
    }

    public void asignarEquiposPorRonda(Ronda ronda) {
        int i = 0;
        listaApuestas = new ArrayList<>();
        List<Partido> partidos = (List<Partido>) ronda.getPartidoCollection();

        for (Partido partido : partidos) {
            listaApuestas.add(new Apuesta());
            listaApuestas.get(i).setPartidoId(partido);
            listaApuestas.get(i).setEquipo1(ejc.findEquipo(partido.getEquipo1()).getNombre());
            listaApuestas.get(i).setEquipo2(ejc.findEquipo(partido.getEquipo2()).getNombre());
            listaApuestas.get(i).setEmpleadoid(empleado);
            //find if the user already made a bet on it 
            Apuesta existeApuesta = ajc.findViaEmpleadoAndPartido(empleado, partido);
            if (existeApuesta != null) {
                listaApuestas.get(i).setGolesEquipo1(existeApuesta.getGolesEquipo1());
                listaApuestas.get(i).setGolesEquipo2(existeApuesta.getGolesEquipo2());
            } else {
                listaApuestas.get(i).setGolesEquipo1(0);
                listaApuestas.get(i).setGolesEquipo2(0);
            }
            //end find

            i++;
        }

        PrimeFaces.current().executeScript("PF('apuestaRondaDialog').show();");
    }

    public void CrearApuestaPorGrupo() {
        for (Apuesta apuestaAux : listaApuestas) {
            if (apuestaAux.getGolesEquipo1() >= 0 && apuestaAux.getGolesEquipo2() >= 0) {
                CrearActualizarApuesta(apuestaAux);
            }
        }
    }

    public void CrearApuestaIndividual() {
        CrearActualizarApuesta(apuesta);
    }

    private void CrearActualizarApuesta(Apuesta apuestaSeleccionada) {
        Apuesta existeApuesta = ajc.findViaEmpleadoAndPartido(apuestaSeleccionada.getEmpleadoid(), apuestaSeleccionada.getPartidoId());
      
        DateTimeZone zoneUTC = DateTimeZone.UTC;
        
        DateTime dt = new DateTime(new Date(apuestaSeleccionada.getPartidoId().getFecha().getTime()));
        DateTime actualDateOnDB = dt.toDateTime(zoneUTC);
        
        DateTime dateTime = new DateTime(DateTime.now());
        boolean test = dateTime.toDate().before(actualDateOnDB.toDate());
        
        
        if (test) {
            if (existeApuesta == null) {//exist do an update
                ajc.create(apuestaSeleccionada);
            } else {
                //update
                existeApuesta.setGolesEquipo1(apuestaSeleccionada.getGolesEquipo1());
                existeApuesta.setGolesEquipo2(apuestaSeleccionada.getGolesEquipo2());
                try {
                    ajc.edit(existeApuesta);
                } catch (Exception ex) {
                    Logger.getLogger(ApuestaBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void calcularPuntosDeEmpleado(List<Partido> partidos) {
        for(Partido partido: partidos) {
            List<Apuesta> apuestas = ajc.findApuestasById(partido.getId());
            
            for(Apuesta apuestaActual : apuestas) {
                try {
                    // Adivinar marcador exacto
                    if(apuestaActual.getGolesEquipo1().equals(partido.getGolesEquipo1()) &&
                        apuestaActual.getGolesEquipo2().equals(partido.getGolesEquipo2())) {
                        apuestaActual.getEmpleadoid().getResultado().setPartidosExactos(+1);
                        apuestaActual.getEmpleadoid().getResultado().setPuntos(+3);
                    }
                    // Adivinar que el equipo 1 gana
                    if(apuestaActual.getGolesEquipo1() > apuestaActual.getGolesEquipo2() &&
                            partido.getGolesEquipo1() > partido.getGolesEquipo2()) {
                        apuestaActual.getEmpleadoid().getResultado().setPartidosGanados(+1);
                        apuestaActual.getEmpleadoid().getResultado().setPuntos(+1);
                        // Adivinar que el equipo 2 gana
                    } else if (apuestaActual.getGolesEquipo1() < apuestaActual.getGolesEquipo2() &&
                            partido.getGolesEquipo1() < partido.getGolesEquipo2()){
                        apuestaActual.getEmpleadoid().getResultado().setPartidosGanados(+1);
                        apuestaActual.getEmpleadoid().getResultado().setPuntos(+1);
                    }
                    // Adivinar empate
                    if(apuestaActual.getGolesEquipo1().equals(apuestaActual.getGolesEquipo2()) &&
                            partido.getGolesEquipo1().equals(partido.getGolesEquipo2())) {
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
    
    /**
     * @return the ronda
     */
    public Ronda getRonda() {
        return ronda;
    }

    /**
     * @param ronda the ronda to set
     */
    public void setRonda(Ronda ronda) {
        this.ronda = ronda;
    }

    /**
     * @return the apuesta
     */
    public Apuesta getApuesta() {
        return apuesta;
    }

    /**
     * @param apuesta the apuesta to set
     */
    public void setApuesta(Apuesta apuesta) {
        this.apuesta = apuesta;
    }

    /**
     * @return the listaRondas
     */
    public List getListaRondas() {
        return listaRondas;
    }

    /**
     * @param listaRondas the listaRondas to set
     */
    public void setListaRondas(List listaRondas) {
        this.listaRondas = listaRondas;
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
     * @return the listaPartidos
     */
    public List<Partido> getListaPartidos() {
        return listaPartidos;
    }

    /**
     * @param listaPartidos the listaPartidos to set
     */
    public void setListaPartidos(List<Partido> listaPartidos) {
        this.listaPartidos = listaPartidos;
    }
}
