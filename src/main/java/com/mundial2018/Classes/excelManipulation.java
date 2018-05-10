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

    private int colNombre;
    private int colEmail;
    private int colUser;
    private int colrol;

    public excelManipulation() {
        colNombre = 0;
        colEmail = 0;
        colUser = 0;
        colrol = 0;
    }
    @Deprecated
    public List getAllRowsFromExcelEmpleado(InputStream fileLocation) {
        try {
            /*
         colNombre;
      colEmail;
      colUser;
      rol;
             */

            if (colNombre == 0) {
                colNombre = 1;
            }
            if (colEmail == 0) {
                colEmail = 2;
            }
            if (colUser == 0) {
                colUser = 3;
            }
            if (colrol == 0) {
                colrol = 4;
            }

            String nombre;
            String email;
            String user;
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
                    nombre = currentCell.getRow().getCell(colNombre).getStringCellValue();
                    email = currentCell.getRow().getCell(colEmail).getStringCellValue();

                    user = currentCell.getRow().getCell(colUser).getStringCellValue();

                    rol = currentCell.getRow().getCell(colrol).getStringCellValue();

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
                    .withHeader("nombre","email","user","rol","codigo")
                    .withFirstRecordAsHeader()
                    .parse(red);
            
            for (CSVRecord record : records) {
                HashMap aux = new HashMap<String, String>();
                String nombre = record.get("nombre");
                String email = record.get("email");
                String user = record.get("user");
                String rol = record.get("rol");
                String codigo = record.get("codigo");
                
                if (user == null || user.isEmpty()) {
                    user = email;
                }
                aux.put("nombre", nombre);
                aux.put("email", email);
                aux.put("user", user);
                aux.put("rol", rol);
                aux.put("codigo", codigo);

                listaDeEmpleados.add(aux);
            }
            
            
            return listaDeEmpleados;
        } catch (IOException ex) {
            Logger.getLogger(excelManipulation.class.getName()).log(Level.SEVERE, null, ex);
        }
                    return null;

}
}
