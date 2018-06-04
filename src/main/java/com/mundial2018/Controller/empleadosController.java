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
import java.io.IOException;
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
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ariel
 */
@ManagedBean
@RequestScoped
public class empleadosController implements Serializable {

    private UploadedFile file;
    private EntityManagerFactoria facto;
    //private Empleado empleado;
    private Login login;

    /**
     * Creates a new instance of empleados
     */
    public empleadosController() {
        facto = new EntityManagerFactoria();
        //empleado = new Empleado();
        login = new Login();

    }
    List<Empleado> emp = new ArrayList<Empleado>();

    @PostConstruct
    public void init() {
        EmpleadoDBA empDB = new EmpleadoDBA();
        emp = empDB.getAllEmpleados();
    }

    public void lolol() {
        Empleado emp2 = new Empleado();
        emp2.setNombre("test");
        emp.add(emp2);
    }

    public List<Empleado> getAllEmpleados() {

        return emp;

    }

    public List<String> getAllEmpleados2() {
        List<String> aux = new ArrayList<String>();
        aux.add("test");

        return aux;

    }

    /*Crea en la base de datos empleados basados en los archivos que se subieron en el excel.     
     */
    public void subirArchivo(FileUploadEvent event) {
        if (event != null) {
            file = event.getFile();

            excelManipulation auxExcel = new excelManipulation();

            try {

                EmpleadoJpaController empController = new EmpleadoJpaController(facto.getEMF());
                LoginJpaController loginJpaController = new LoginJpaController(facto.getEMF());

                ArrayList<HashMap> mapDeEmpleados = auxExcel.getAllRowsFromCVSfile(file.getInputstream());

                for (HashMap mappeo : mapDeEmpleados) {
                    try {

                        Empleado emp = new Empleado();
                        emp.setEmail(mappeo.get("email").toString());
                        emp.setNombre(mappeo.get("nombre").toString());

                        empController.create(emp);

                        int test = emp.getId();

                        Login log = new Login();
                        log.setRol(mappeo.get("rol").toString());
                        log.setUsername(mappeo.get("email").toString());
                        log.setPassword(mappeo.get("codigo").toString());
                        log.setEmpleado(emp);
                        loginJpaController.create(log);

                    } catch (Exception e) {
                        Logger.getLogger("Hubo un error al subir un empleado a la base de datos");

                    }

                }

            } catch (IOException ex) {
                FacesMessage message = new FacesMessage("Error", file.getFileName() + " No es un archivo valido de excel.");
                Logger.getLogger(empleadosController.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

            PrimeFaces.current().executeScript("PF('listEmployees').update();");

            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
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
                EmpleadoDBA.create(aux);

                login.setEmpleado(aux);
                login.setUsername(aux.getNombre());
                LoginDBA.create(login);
              
            } catch (Exception ex) {

                Logger.getLogger(empleadosController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /*
    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }*/
    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

}
