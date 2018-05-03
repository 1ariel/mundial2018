/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Controller;

import com.mundial2018.Classes.excelManipulation;
import com.mundial2018.Database.Entities.Empleado;
import com.mundial2018.Database.Persistance.EmpleadoDBA;
import com.sun.org.apache.xerces.internal.impl.io.ASCIIReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
/**
 *
 * @author ariel
 */
@ManagedBean
@SessionScoped
public class empleadosController implements Serializable {

    private UploadedFile file;
    /**
     * Creates a new instance of empleados
     */
    public empleadosController() {
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

 
 
    public void subirArchivo(FileUploadEvent event) {
        if (event != null) {
             file = event.getFile();

            excelManipulation auxExcel = new excelManipulation();

            try {
       
            
            
           ArrayList<HashMap> mapDeEmpleados=   auxExcel.getAllRowsFromCVSfile(file.getInputstream());
                
           
           
            } catch (IOException ex) {
                FacesMessage message = new FacesMessage("Error", file.getFileName()+ " No es un archivo valido de excel.");
                Logger.getLogger(empleadosController.class.getName()).log(Level.SEVERE, null, ex);

                return;

            }

            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
