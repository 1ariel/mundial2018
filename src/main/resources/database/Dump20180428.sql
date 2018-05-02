CREATE DATABASE  IF NOT EXISTS `zeusco_mundial2018` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `zeusco_mundial2018`;
-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: zeusco_mundial2018
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.17.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Apuesta`
--

DROP TABLE IF EXISTS `Apuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Apuesta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `equipo1` varchar(45) DEFAULT NULL,
  `golesEquipo1` int(11) DEFAULT NULL,
  `equipo2` varchar(45) DEFAULT NULL,
  `golesEquipo2` int(11) DEFAULT NULL,
  `Empleado_id` int(11) NOT NULL,
  `Partido_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Apuesta_Empleado1_idx` (`Empleado_id`),
  KEY `fk_Apuesta_Partido1_idx` (`Partido_id`),
  CONSTRAINT `fk_Apuesta_Empleado1` FOREIGN KEY (`Empleado_id`) REFERENCES `Empleado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Apuesta_Partido1` FOREIGN KEY (`Partido_id`) REFERENCES `Partido` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Apuesta`
--

LOCK TABLES `Apuesta` WRITE;
/*!40000 ALTER TABLE `Apuesta` DISABLE KEYS */;
/*!40000 ALTER TABLE `Apuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Empleado`
--

DROP TABLE IF EXISTS `Empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Empleado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Empleado`
--

LOCK TABLES `Empleado` WRITE;
/*!40000 ALTER TABLE `Empleado` DISABLE KEYS */;
INSERT INTO `Empleado` VALUES (1,'a','a');
/*!40000 ALTER TABLE `Empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Equipo`
--

DROP TABLE IF EXISTS `Equipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Equipo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `ganados` int(11) DEFAULT NULL,
  `perdidos` int(11) DEFAULT NULL,
  `empatados` int(11) DEFAULT NULL,
  `golesFavor` int(11) DEFAULT NULL,
  `golesContra` int(11) DEFAULT NULL,
  `puntos` int(11) DEFAULT NULL,
  `Grupo_id` int(11) NOT NULL,
  `Partido_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Equipo_Grupo1_idx` (`Grupo_id`),
  KEY `fk_Equipo_Partido1_idx` (`Partido_id`),
  CONSTRAINT `fk_Equipo_Grupo1` FOREIGN KEY (`Grupo_id`) REFERENCES `Grupo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Equipo_Partido1` FOREIGN KEY (`Partido_id`) REFERENCES `Partido` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Equipo`
--

LOCK TABLES `Equipo` WRITE;
/*!40000 ALTER TABLE `Equipo` DISABLE KEYS */;
/*!40000 ALTER TABLE `Equipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Grupo`
--

DROP TABLE IF EXISTS `Grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Grupo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Grupo`
--

LOCK TABLES `Grupo` WRITE;
/*!40000 ALTER TABLE `Grupo` DISABLE KEYS */;
/*!40000 ALTER TABLE `Grupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Partido`
--

DROP TABLE IF EXISTS `Partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Partido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `lugar` varchar(45) DEFAULT NULL,
  `equipo1` varchar(45) DEFAULT NULL,
  `golesEquipo1` int(11) DEFAULT NULL,
  `equipo2` varchar(45) DEFAULT NULL,
  `golesEquipo2` int(11) DEFAULT NULL,
  `Ronda_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Partido_Ronda1_idx` (`Ronda_id`),
  CONSTRAINT `fk_Partido_Ronda1` FOREIGN KEY (`Ronda_id`) REFERENCES `Ronda` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Partido`
--

LOCK TABLES `Partido` WRITE;
/*!40000 ALTER TABLE `Partido` DISABLE KEYS */;
/*!40000 ALTER TABLE `Partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Resultados`
--

DROP TABLE IF EXISTS `Resultados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Resultados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ganados` int(11) DEFAULT NULL,
  `perdidos` int(11) DEFAULT NULL,
  `empatados` int(11) DEFAULT NULL,
  `puntos` int(11) DEFAULT NULL,
  `Empleado_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Resultados_Empleado1_idx` (`Empleado_id`),
  CONSTRAINT `fk_Resultados_Empleado1` FOREIGN KEY (`Empleado_id`) REFERENCES `Empleado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Resultados`
--

LOCK TABLES `Resultados` WRITE;
/*!40000 ALTER TABLE `Resultados` DISABLE KEYS */;
/*!40000 ALTER TABLE `Resultados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ronda`
--

DROP TABLE IF EXISTS `Ronda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Ronda` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ronda`
--

LOCK TABLES `Ronda` WRITE;
/*!40000 ALTER TABLE `Ronda` DISABLE KEYS */;
/*!40000 ALTER TABLE `Ronda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `rol` varchar(45) DEFAULT NULL,
  `Empleado_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`Empleado_id`),
  UNIQUE KEY `user_UNIQUE` (`user`),
  KEY `fk_User_Empleado1_idx` (`Empleado_id`),
  CONSTRAINT `fk_User_Empleado1` FOREIGN KEY (`Empleado_id`) REFERENCES `Empleado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'a','a','admin',1);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'zeusco_mundial2018'
--

--
-- Dumping routines for database 'zeusco_mundial2018'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-28 20:25:56
