/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundial2018.Classes;

/**
 *
 * @author ariel
 */
public class ImageHelper {

    public String findLocationOfFlag(String countryName) {
        String location;

        switch (countryName) {
            case "Rusia":
                location = "banderas/Flag of Russia.png";
                break;

            case "Arabia Saudí":
                location = "banderas/Flag of Saudi Arabia.png";
                break;            
            case "Egipto":
                location = "banderas/Flag of Egypt.png";
                break;
            case "Uruguay":
                location = "banderas/Flag of Uruguay.png";
                break;
            case "Portugal":
                location = "banderas/Flag of Portugal.png";
                break;
            case "España":
                location = "banderas/Flag of Spain.png";
                break;
            case "Marruecos":
                location = "banderas/Flag of Morocco.png";
                break;
            case "RI de Irán":
                location = "banderas/Flag of Iran.png";
                break;
            case "Francia":
                location = "banderas/Flag of France.png";
                break;
            case "Australia":
                location = "banderas/Flag of Australia.png";
                break;
            case "Perú":
                location = "banderas/Flag of Peru.png";
                break;
            case "Dinamarca":
                location = "banderas/Flag of Denmark.png";
                break;
            case "Argentina":
                location = "banderas/Flag of Argentina.png";
                break;
            case "Islandia":
                location = "banderas/Flag of Iceland.png";
                break;
            case "Croacia":
                location = "banderas/Flag of Croatia.png";
                break;
            case "Nigeria":
                location = "banderas/Flag of Nigeria.png";
                break;
            case "Brasil":
                location = "banderas/Flag of Brazil.png";
                break;
            case "Suiza":
                location = "banderas/Flag of Switzerland.png";
                break;
            case "Costa Rica":
                location = "banderas/Flag of Costa Rica.png";
                break;
            case "Serbia":
                location = "banderas/Flag of Serbia.png";
                break;
            case "Alemania":
                location = "banderas/Flag of Germany.png";
                break;
            case "México":
                location = "banderas/Flag of Mexico.png";
                break;
            case "Suecia":
                location = "banderas/Flag of Sweden.png";
                break;
            case "República de Corea":
                location = "banderas/Flag of South Korea.png";
                break;
            case "Bélgica":
                location = "banderas/Flag of Belgium.png";
                break;
            case "Panamá":
                location = "banderas/Flag of Panama.png";
                break;
            case "Túnez":
                location = "banderas/Flag of Tunisia.png";
                break;
            case "Inglaterra":
                location = "banderas/Flag of England.png";
                break;
            case "Polonia":
                location = "banderas/Flag of Poland.png";
                break;

            case "Senegal":
                location = "banderas/Flag of Senegal.png";
                break;

            case "Colombia":
                location = "banderas/Flag of Colombia.png";
                break;
            case "Japón":
                location = "banderas/Flag of Japan.png";
                break;

            default:
                location = "banderas/logo.png";
                break;

        }

        return location;
    }

}
