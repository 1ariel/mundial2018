-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema zeusco_mundial2018
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `zeusco_mundial2018` ;

-- -----------------------------------------------------
-- Schema zeusco_mundial2018
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `zeusco_mundial2018` DEFAULT CHARACTER SET utf8 ;
USE `zeusco_mundial2018` ;

-- -----------------------------------------------------
-- Table `zeusco_mundial2018`.`empleado`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `zeusco_mundial2018`.`empleado` ;

CREATE TABLE IF NOT EXISTS `zeusco_mundial2018`.`empleado` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zeusco_mundial2018`.`ronda`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `zeusco_mundial2018`.`ronda` ;

CREATE TABLE IF NOT EXISTS `zeusco_mundial2018`.`ronda` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `fecha` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zeusco_mundial2018`.`partido`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `zeusco_mundial2018`.`partido` ;

CREATE TABLE IF NOT EXISTS `zeusco_mundial2018`.`partido` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `fecha` DATE NULL DEFAULT NULL,
  `lugar` VARCHAR(45) NULL DEFAULT NULL,
  `equipo1` VARCHAR(45) NULL DEFAULT NULL,
  `golesEquipo1` INT(11) NULL DEFAULT NULL,
  `equipo2` VARCHAR(45) NULL DEFAULT NULL,
  `golesEquipo2` INT(11) NULL DEFAULT NULL,
  `Ronda_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Partido_Ronda1_idx` (`Ronda_id` ASC),
  CONSTRAINT `fk_Partido_Ronda1`
    FOREIGN KEY (`Ronda_id`)
    REFERENCES `zeusco_mundial2018`.`ronda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zeusco_mundial2018`.`apuesta`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `zeusco_mundial2018`.`apuesta` ;

CREATE TABLE IF NOT EXISTS `zeusco_mundial2018`.`apuesta` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `equipo1` VARCHAR(45) NULL DEFAULT NULL,
  `golesEquipo1` INT(11) NULL DEFAULT NULL,
  `equipo2` VARCHAR(45) NULL DEFAULT NULL,
  `golesEquipo2` INT(11) NULL DEFAULT NULL,
  `Empleado_id` INT(11) NOT NULL,
  `Partido_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Apuesta_Empleado1_idx` (`Empleado_id` ASC),
  INDEX `fk_Apuesta_Partido1_idx` (`Partido_id` ASC),
  CONSTRAINT `fk_Apuesta_Empleado1`
    FOREIGN KEY (`Empleado_id`)
    REFERENCES `zeusco_mundial2018`.`empleado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Apuesta_Partido1`
    FOREIGN KEY (`Partido_id`)
    REFERENCES `zeusco_mundial2018`.`partido` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zeusco_mundial2018`.`grupo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `zeusco_mundial2018`.`grupo` ;

CREATE TABLE IF NOT EXISTS `zeusco_mundial2018`.`grupo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zeusco_mundial2018`.`equipo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `zeusco_mundial2018`.`equipo` ;

CREATE TABLE IF NOT EXISTS `zeusco_mundial2018`.`equipo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `ganados` INT(11) NULL DEFAULT NULL,
  `perdidos` INT(11) NULL DEFAULT NULL,
  `empatados` INT(11) NULL DEFAULT NULL,
  `golesFavor` INT(11) NULL DEFAULT NULL,
  `golesContra` INT(11) NULL DEFAULT NULL,
  `puntos` INT(11) NULL DEFAULT NULL,
  `Grupo_id` INT(11) NOT NULL,
  `Partido_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Equipo_Grupo1_idx` (`Grupo_id` ASC),
  INDEX `fk_Equipo_Partido1_idx` (`Partido_id` ASC),
  CONSTRAINT `fk_Equipo_Grupo1`
    FOREIGN KEY (`Grupo_id`)
    REFERENCES `zeusco_mundial2018`.`grupo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Equipo_Partido1`
    FOREIGN KEY (`Partido_id`)
    REFERENCES `zeusco_mundial2018`.`partido` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zeusco_mundial2018`.`resultados`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `zeusco_mundial2018`.`resultados` ;

CREATE TABLE IF NOT EXISTS `zeusco_mundial2018`.`resultados` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `ganados` INT(11) NULL DEFAULT NULL,
  `perdidos` INT(11) NULL DEFAULT NULL,
  `empatados` INT(11) NULL DEFAULT NULL,
  `puntos` INT(11) NULL DEFAULT NULL,
  `Empleado_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Resultados_Empleado1_idx` (`Empleado_id` ASC),
  CONSTRAINT `fk_Resultados_Empleado1`
    FOREIGN KEY (`Empleado_id`)
    REFERENCES `zeusco_mundial2018`.`empleado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zeusco_mundial2018`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `zeusco_mundial2018`.`user` ;

CREATE TABLE IF NOT EXISTS `zeusco_mundial2018`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `rol` VARCHAR(45) NULL DEFAULT NULL,
  `Empleado_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `Empleado_id`),
  UNIQUE INDEX `user_UNIQUE` (`user` ASC),
  INDEX `fk_User_Empleado1_idx` (`Empleado_id` ASC),
  CONSTRAINT `fk_User_Empleado1`
    FOREIGN KEY (`Empleado_id`)
    REFERENCES `zeusco_mundial2018`.`empleado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
