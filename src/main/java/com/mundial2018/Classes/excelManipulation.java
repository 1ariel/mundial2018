/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Classes;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.HashMap;
/**
 *
 * @author ariel
 */
public class excelManipulation {
    private int colCodigo;
    private int colEmail;
    private int colNombre;
    private int colUsuario;
    private int colCedula;
    private int colRol;

    public excelManipulation() {
        colCodigo = 0;
        colEmail = 0;
        colNombre = 0;
        colUsuario = 0;
        colCedula = 0;
        colRol = 0;
    }
    
    @Deprecated
    public List getAllRowsFromExcelEmpleado(InputStream fileLocation) {
        try {
            if (colCodigo == 0) {
                colCodigo = 1;
            }
            if (colEmail == 0) {
                colEmail = 2;
            }
            if (colNombre == 0) {
                colNombre = 3;
            }
            if (colUsuario == 0) {
                colUsuario = 4;
            }
            if (colCedula == 0) {
                colCedula = 5;
            }
            if (colRol == 0) {
                colRol = 6;
            }

            String codigo;
            String email;
            String nombre;
            String usuario;
            String cedula;
            String rol;

            //   FileInputStream excelFile = new FileInputStream(fileLocation);
            Workbook workbook = new XSSFWorkbook(fileLocation);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    codigo = currentCell.getRow().getCell(colCodigo).getStringCellValue();
                    email = currentCell.getRow().getCell(colEmail).getStringCellValue();
                    nombre = currentCell.getRow().getCell(colNombre).getStringCellValue();
                    usuario = currentCell.getRow().getCell(colUsuario).getStringCellValue();
                    cedula = currentCell.getRow().getCell(colCedula).getStringCellValue();
                    rol = currentCell.getRow().getCell(colRol).getStringCellValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public ArrayList getAllRowsFromCVSfile(InputStream in){
        try {
            ArrayList<HashMap> listaDeEmpleados = new ArrayList<HashMap>();
            Reader red = new InputStreamReader(in);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    //.withHeader("nombre","email","user","rol","codigo")
                    .withHeader("codigo","email","nombre","usuario","cedula","rol")
                    .withFirstRecordAsHeader()
                    .parse(red);
            
            for (CSVRecord record : records) {
                HashMap aux = new HashMap<String, String>();
                String codigo = record.get("codigo");
                String email = record.get("email");
                String nombre = record.get("nombre");
                String usuario = record.get("usuario");
                String cedula = record.get("cedula");
                String rol = record.get("rol");
//                String nombre = record.get("nombre");
//                String email = record.get("email");
//                String user = record.get("user");
//                String rol = record.get("rol");
//                String codigo = record.get("codigo");
                
                if (usuario == null || usuario.isEmpty()) {
                    usuario = email;
                }
                
                aux.put("codigo", codigo);
                aux.put("email", email);
                aux.put("nombre", nombre);
                aux.put("usuario", usuario);
                aux.put("cedula", cedula);
                aux.put("rol", rol);
//                aux.put("nombre", nombre);
//                aux.put("email", email);
//                aux.put("user", user);
//                aux.put("rol", rol);
//                aux.put("codigo", codigo);
                listaDeEmpleados.add(aux);
            }
            
            return listaDeEmpleados;
        } catch (IOException ex) {
            Logger.getLogger(excelManipulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
