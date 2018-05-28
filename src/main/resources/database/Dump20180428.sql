CREATE DATABASE  IF NOT EXISTS `zeusco_mundial2018` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `zeusco_mundial2018`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: zeusco_mundial2018
-- ------------------------------------------------------
-- Server version	5.7.15-log

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
-- Table structure for table `apuesta`
--

DROP TABLE IF EXISTS `apuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `apuesta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `equipo1` varchar(45) DEFAULT NULL,
  `golesEquipo1` int(11) DEFAULT NULL,
  `equipo2` varchar(45) DEFAULT NULL,
  `golesEquipo2` int(11) DEFAULT NULL,
  `Empleado_id` int(11) NOT NULL,
  `partido_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Apuesta_Empleado1_idx` (`Empleado_id`),
  KEY `fk_apuesta_partido1_idx` (`partido_id`),
  CONSTRAINT `fk_Apuesta_Empleado1` FOREIGN KEY (`Empleado_id`) REFERENCES `empleado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_apuesta_partido1` FOREIGN KEY (`partido_id`) REFERENCES `partido` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apuesta`
--

LOCK TABLES `apuesta` WRITE;
/*!40000 ALTER TABLE `apuesta` DISABLE KEYS */;
/*!40000 ALTER TABLE `apuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empleado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES (1,'Administrador','administrador@email.com');
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipo`
--

DROP TABLE IF EXISTS `equipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `ganados` int(11) DEFAULT NULL,
  `perdidos` int(11) DEFAULT NULL,
  `empatados` int(11) DEFAULT NULL,
  `golesFavor` int(11) DEFAULT NULL,
  `golesContra` int(11) DEFAULT NULL,
  `puntos` int(11) DEFAULT NULL,
  `Grupo_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Equipo_Grupo1_idx` (`Grupo_id`),
  CONSTRAINT `fk_Equipo_Grupo1` FOREIGN KEY (`Grupo_id`) REFERENCES `grupo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipo`
--

LOCK TABLES `equipo` WRITE;
/*!40000 ALTER TABLE `equipo` DISABLE KEYS */;
INSERT INTO `equipo` VALUES (1,'Rusia',0,0,0,0,0,0,1),(2,'Arabia Saudí',0,0,0,0,0,0,1),(3,'Egipto',0,0,0,0,0,0,1),(4,'Uruguay',0,0,0,0,0,0,1),(5,'Portugal',0,0,0,0,0,0,2),(6,'España',0,0,0,0,0,0,2),(7,'Marruecos',0,0,0,0,0,0,2),(8,'RI de Irán',0,0,0,0,0,0,2),(9,'Francia',0,0,0,0,0,0,3),(10,'Australia',0,0,0,0,0,0,3),(11,'Perú',0,0,0,0,0,0,3),(12,'Dinamarca',0,0,0,0,0,0,3),(13,'Argentina',0,0,0,0,0,0,4),(14,'Islandia',0,0,0,0,0,0,4),(15,'Croacia',0,0,0,0,0,0,4),(16,'Nigeria',0,0,0,0,0,0,4),(17,'Brasil',0,0,0,0,0,0,5),(18,'Suiza',0,0,0,0,0,0,5),(19,'Costa Rica',0,0,0,0,0,0,5),(20,'Serbia',0,0,0,0,0,0,5),(21,'Alemania',0,0,0,0,0,0,6),(22,'México',0,0,0,0,0,0,6),(23,'Suecia',0,0,0,0,0,0,6),(24,'República de Corea',0,0,0,0,0,0,6),(25,'Bélgica',0,0,0,0,0,0,7),(26,'Panamá',0,0,0,0,0,0,7),(27,'Túnez',0,0,0,0,0,0,7),(28,'Inglaterra',0,0,0,0,0,0,7),(29,'Polonia',0,0,0,0,0,0,8),(30,'Senegal',0,0,0,0,0,0,8),(31,'Colombia',0,0,0,0,0,0,8),(32,'Japón',0,0,0,0,0,0,8);
/*!40000 ALTER TABLE `equipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupo`
--

DROP TABLE IF EXISTS `grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupo`
--

LOCK TABLES `grupo` WRITE;
/*!40000 ALTER TABLE `grupo` DISABLE KEYS */;
INSERT INTO `grupo` VALUES (1,'A'),(2,'B'),(3,'C'),(4,'D'),(5,'E'),(6,'F'),(7,'G'),(8,'H');
/*!40000 ALTER TABLE `grupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `rol` varchar(45) DEFAULT NULL,
  `Empleado_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`Empleado_id`),
  UNIQUE KEY `user_UNIQUE` (`username`),
  KEY `fk_User_Empleado1_idx` (`Empleado_id`),
  CONSTRAINT `fk_User_Empleado1` FOREIGN KEY (`Empleado_id`) REFERENCES `empleado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (1,'admin','admin','admin',1);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partido`
--

DROP TABLE IF EXISTS `partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partido` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NULL DEFAULT NULL,
  `lugar` VARCHAR(45) NULL DEFAULT NULL,
  `equipo1` INT NULL DEFAULT NULL,
  `golesEquipo1` INT NULL DEFAULT NULL,
  `penalesEquipo1` INT NULL,
  `equipo2` INT NULL DEFAULT NULL,
  `golesEquipo2` INT NULL DEFAULT NULL,
  `penalesEquipo2` INT NULL,
  `ronda_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_partido_ronda1_idx` (`ronda_id`),
  CONSTRAINT `fk_partido_ronda1` FOREIGN KEY (`ronda_id`) REFERENCES `ronda` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partido`
--

LOCK TABLES `partido` WRITE;
/*!40000 ALTER TABLE `partido` DISABLE KEYS */;
INSERT INTO `partido` VALUES (1,'2018-06-14','Luzhniki Stadium',1,NULL,NULL,2,NULL,NULL,1),(2,'2018-06-15','Ekaterinburg Arena',3,NULL,NULL,4,NULL,NULL,2),(3,'2018-06-15','Estadio de San Petersburgo',7,NULL,NULL,8,NULL,NULL,2),(4,'2018-06-15','Fisht Stadium',5,NULL,NULL,6,NULL,NULL,2),(5,'2018-06-16','Kazan Arena',9,NULL,NULL,10,NULL,NULL,3),(6,'2018-06-16','Spartak Stadium',13,NULL,NULL,14,NULL,NULL,3),(7,'2018-06-16','Mordovia Arena',11,NULL,NULL,12,NULL,NULL,3),(8,'2018-06-16','Kaliningrad Stadium',15,NULL,NULL,16,NULL,NULL,3),(9,'2018-06-17','Samara Arena',19,NULL,NULL,20,NULL,NULL,4),(10,'2018-06-17','Luzhniki Stadium',21,NULL,NULL,22,NULL,NULL,4),(11,'2018-06-17','Rostov Arena',17,NULL,NULL,18,NULL,NULL,4),(12,'2018-06-18','Nizhny Novgorod Stadium',23,NULL,NULL,24,NULL,NULL,5),(13,'2018-06-18','Fisht Stadium',25,NULL,NULL,26,NULL,NULL,5),(14,'2018-06-18','Volgograd Arena',27,NULL,NULL,28,NULL,NULL,5),(15,'2018-06-19','Mordovia Arena',31,NULL,NULL,32,NULL,NULL,6),(16,'2018-06-19','Spartak Stadium',29,NULL,NULL,30,NULL,NULL,6),(17,'2018-06-19','Estadio de San Petersburgo',1,NULL,NULL,3,NULL,NULL,6),(18,'2018-06-20','Luzhniki Stadium',5,NULL,NULL,7,NULL,NULL,7),(19,'2018-06-20','Rostov Arena',4,NULL,NULL,2,NULL,NULL,7),(20,'2018-06-20','Kazan Arena',8,NULL,NULL,6,NULL,NULL,7),(21,'2018-06-21','Samara Arena',12,NULL,NULL,10,NULL,NULL,8),(22,'2018-06-21','Ekaterinburg Arena',9,NULL,NULL,11,NULL,NULL,8),(23,'2018-06-21','Nizhny Novgorod Stadium',13,NULL,NULL,15,NULL,NULL,8),(24,'2018-06-22','Estadio de San Petersburgo',17,NULL,NULL,19,NULL,NULL,9),(25,'2018-06-22','Volgograd Arena',16,NULL,NULL,14,NULL,NULL,9),(27,'2018-06-22','Kaliningrad Stadium',20,NULL,NULL,18,NULL,NULL,9),(28,'2018-06-23','Spartak Stadium',25,NULL,NULL,27,NULL,NULL,10),(29,'2018-06-23','Rostov Arena',24,NULL,NULL,22,NULL,NULL,10),(30,'2018-06-23','Fisht Stadium',21,NULL,NULL,23,NULL,NULL,10),(31,'2018-06-24','Nizhny Novgorod Stadium',28,NULL,NULL,26,NULL,NULL,11),(32,'2018-06-24','Ekaterinburg Arena',32,NULL,NULL,30,NULL,NULL,11),(33,'2018-06-24','Kazan Arena',29,NULL,NULL,31,NULL,NULL,11),(34,'2018-06-25','Samara Arena',4,NULL,NULL,1,NULL,NULL,12),(35,'2018-06-25','Volgograd Arena',2,NULL,NULL,3,NULL,NULL,12),(36,'2018-06-25','Kaliningrad Stadium',6,NULL,NULL,7,NULL,NULL,12),(37,'2018-06-25','Mordovia Arena',8,NULL,NULL,5,NULL,NULL,12),(38,'2018-06-26','Fisht Stadium',10,NULL,NULL,11,NULL,NULL,13),(39,'2018-06-26','Luzhniki Stadium',12,NULL,NULL,9,NULL,NULL,13),(40,'2018-06-26','Estadio de San Petersburgo',16,NULL,NULL,13,NULL,NULL,13),(41,'2018-06-26','Rostov Arena',14,NULL,NULL,15,NULL,NULL,13),(42,'2018-06-27','Kazan Arena',24,NULL,NULL,21,NULL,NULL,14),(43,'2018-06-27','Ekaterinburg Arena',22,NULL,NULL,23,NULL,NULL,14),(44,'2018-06-27','Spartak Stadium',20,NULL,NULL,17,NULL,NULL,14),(45,'2018-06-27','Nizhny Novgorod Stadium',18,NULL,NULL,19,NULL,NULL,14),(46,'2018-06-28','Volgograd Arena',32,NULL,NULL,29,NULL,NULL,15),(47,'2018-06-28','Samara Arena',30,NULL,NULL,31,NULL,NULL,15),(48,'2018-06-28','Mordovia Arena',26,NULL,NULL,27,NULL,NULL,15),(49,'2018-06-28','Kaliningrad Stadium',28,NULL,NULL,25,NULL,NULL,15),(50,'2018-06-30','Kazan Arena',NULL,NULL,NULL,NULL,NULL,NULL,16),(51,'2018-06-30','Fisht Stadium',NULL,NULL,NULL,NULL,NULL,NULL,16),(52,'2018-07-01','Luzhniki Stadium',NULL,NULL,NULL,NULL,NULL,NULL,17),(53,'2018-07-01','Nizhny Novgorod Stadium',NULL,NULL,NULL,NULL,NULL,NULL,17),(54,'2018-07-02','Samara Arena',NULL,NULL,NULL,NULL,NULL,NULL,18),(55,'2018-07-02','Rostov Arena',NULL,NULL,NULL,NULL,NULL,NULL,18),(56,'2018-07-03','Estadio de San Petersburgo',NULL,NULL,NULL,NULL,NULL,NULL,19),(57,'2018-07-03','Spartak Stadium',NULL,NULL,NULL,NULL,NULL,NULL,19),(58,'2018-07-06','Nizhny Novgorod Stadium',NULL,NULL,NULL,NULL,NULL,NULL,20),(59,'2018-07-06','Kazan Arena',NULL,NULL,NULL,NULL,NULL,NULL,20),(60,'2018-07-07','Samara Arena',NULL,NULL,NULL,NULL,NULL,NULL,21),(61,'2018-07-07','Fisht Stadium',NULL,NULL,NULL,NULL,NULL,NULL,21),(62,'2018-07-10','Estadio de San Petersburgo',NULL,NULL,NULL,NULL,NULL,NULL,22),(63,'2018-07-11','Luzhniki Stadium',NULL,NULL,NULL,NULL,NULL,NULL,23),(64,'2018-07-14','Estadio de San Petersburgo',NULL,NULL,NULL,NULL,NULL,NULL,24),(65,'2018-07-15','Luzhniki Stadium',NULL,NULL,NULL,NULL,NULL,NULL,25);
/*!40000 ALTER TABLE `partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resultado`
--

DROP TABLE IF EXISTS `resultado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resultado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partidosExactos` int(11) DEFAULT NULL,
  `partidosGanados` int(11) DEFAULT NULL,
  `partidosEmpatados` int(11) DEFAULT NULL,
  `puntos` int(11) DEFAULT NULL,
  `empleado_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_resultado_empleado1_idx` (`empleado_id`),
  CONSTRAINT `fk_resultado_empleado1` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resultado`
--

LOCK TABLES `resultado` WRITE;
/*!40000 ALTER TABLE `resultado` DISABLE KEYS */;
/*!40000 ALTER TABLE `resultado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ronda`
--

DROP TABLE IF EXISTS `ronda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ronda` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ronda`
--

LOCK TABLES `ronda` WRITE;
/*!40000 ALTER TABLE `ronda` DISABLE KEYS */;
INSERT INTO `ronda` VALUES (1,'Fase de grupos','2018-06-14'),(2,'Fase de grupos','2018-06-15'),(3,'Fase de grupos','2018-06-16'),(4,'Fase de grupos','2018-06-17'),(5,'Fase de grupos','2018-06-18'),(6,'Fase de grupos','2018-06-19'),(7,'Fase de grupos','2018-06-20'),(8,'Fase de grupos','2018-06-21'),(9,'Fase de grupos','2018-06-22'),(10,'Fase de grupos','2018-06-23'),(11,'Fase de grupos','2018-06-24'),(12,'Fase de grupos','2018-06-25'),(13,'Fase de grupos','2018-06-26'),(14,'Fase de grupos','2018-06-27'),(15,'Fase de grupos','2018-06-28'),(16,'Octavos de final','2018-06-30'),(17,'Octavos de final','2018-07-01'),(18,'Octavos de final','2018-07-02'),(19,'Octavos de final','2018-07-03'),(20,'Cuartos de final','2018-07-06'),(21,'Cuartos de final','2018-07-07'),(22,'Semifinales','2018-07-10'),(23,'Semifinales','2018-07-11'),(24,'Partido por el tercer lugar','2018-07-14'),(25,'Final','2018-07-15');
/*!40000 ALTER TABLE `ronda` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-12 18:11:18
