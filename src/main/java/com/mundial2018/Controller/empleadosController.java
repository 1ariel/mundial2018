/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Controller;

import com.mundial2018.Classes.excelManipulation;
import com.mundial2018.Database.Entities.Empleado;
import com.mundial2018.Database.Entities.Login;
import com.mundial2018.Database.Persistance.EmpleadoDBA;
import com.mundial2018.Database.Persistance.EntityManagerFactoria;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ariel
 */
@ManagedBean
@ViewScoped 
public class empleadosController implements Serializable {
    private UploadedFile file;
    private final EmpleadoDBA empDB;
    private final EntityManagerFactoria facto;
    //private Empleado empleado;
    List<Empleado> listaEmpleados = new ArrayList<>();
    private Login login;

    /**
     * Creates a new instance of empleados
     */
    public empleadosController() {
        facto = new EntityManagerFactoria();
        empDB = new EmpleadoDBA();
        login = new Login();
    }

    @PostConstruct
    public void init() {
        listaEmpleados = empDB.getAllEmpleados();
    }

    public List<Empleado> getAllEmpleados() {
        return listaEmpleados;
    }

    public List<String> getAllEmpleados2() {
        List<String> aux = new ArrayList<String>();
        aux.add("test");

        return aux;
    }

    /**
     * Crea en la base de datos empleados basados en los archivos que se subieron en el excel.
     * @param event 
     */
    public void subirArchivo(FileUploadEvent event) {
        if (event != null) {
            file = event.getFile();
            excelManipulation auxExcel = new excelManipulation();
            FacesMessage message = null;
            
            try {
                EmpleadoJpaController empleadojc = new EmpleadoJpaController(facto.getEMF());
                LoginJpaController loginjc = new LoginJpaController(facto.getEMF());
                ArrayList<HashMap> mapDeEmpleados = auxExcel.getAllRowsFromCVSfile(file.getInputstream());

                for (HashMap mappeo : mapDeEmpleados) {
                    try {
                        Integer codigo = Integer.parseInt(mappeo.get("codigo").toString());
                        String username = mappeo.get("usuario").toString();
                        
                        Empleado auxEmpleado = empleadojc.findEmpleado(codigo);
                        Login auxLogin = loginjc.findLoginByUser(username);
                        
                        if(Objects.isNull(auxEmpleado) && 
                                Objects.isNull(auxLogin)) {
                            Empleado empleado = new Empleado();
                            empleado.setId(codigo);
                            empleado.setEmail(mappeo.get("email").toString());
                            empleado.setNombre(mappeo.get("nombre").toString());
                            empleadojc.create(empleado);
                            
                            Login nuevo = new Login();
                            nuevo.setUsername(mappeo.get("usuario").toString());
                            nuevo.setPassword(mappeo.get("cedula").toString());
                            nuevo.setRol(mappeo.get("rol").toString());
                            nuevo.setEmpleado(empleado);
                            loginjc.create(nuevo);
                        }
                    } catch (Exception e) {
                        Logger.getLogger("Hubo un error al subir un empleado a la base de datos: " + e.getMessage());
                    }
                }
                
                listaEmpleados = empDB.getAllEmpleados();
                message = new FacesMessage("Archivo '" + file.getFileName() + "' subido exitosamente", null);
                
            } catch (Exception ex) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El formato del archivo no es válido", null);
                Logger.getLogger(empleadosController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void agregarManualEmpleado() {
        if (Objects.nonNull(login.getRol())
                && Objects.nonNull(login.getPassword())
                && Objects.nonNull(login.getEmpleado().getNombre())) {
            LoginJpaController LoginDBA = new LoginJpaController(facto.getEMF());
            EmpleadoJpaController EmpleadoDBA = new EmpleadoJpaController(facto.getEMF());

            try {
                //todo validacion si el empleado ya existe.
                Empleado aux = login.getEmpleado();

                Query query1 = EmpleadoDBA.getEntityManager().createNamedQuery("Empleado.findByNombre").setParameter("nombre", aux.getNombre());
                Empleado existe = null;

                if (!query1.getResultList().isEmpty()) {
                    existe = (Empleado) query1.getResultList().get(0);
                }
                if (existe == null) {
                    EmpleadoDBA.create(aux);

                } else {
                    aux = existe;
                }

                login.setEmpleado(aux);
                login.setUsername(aux.getNombre());
                LoginDBA.create(login);
                listaEmpleados.add(aux);
            } catch (Exception ex) {
                Logger.getLogger(empleadosController.class.getName()).log(Level.SEVERE, null, ex);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya existe un empleado con ese Nombre", null);
                FacesContext.getCurrentInstance().addMessage(null, message);
                
                return;
            }
            
            FacesMessage message = new FacesMessage("Empleado agregado exitosamente", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre, Codigo o Rol no seleccionado, por favor no deje estos espacios en blanco", null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

}
