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
  `id` INT NOT NULL AUTO_INCREMENT,
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
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `fecha` DATE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `zeusco_mundial2018`.`partido`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `zeusco_mundial2018`.`partido` ;

CREATE TABLE IF NOT EXISTS `zeusco_mundial2018`.`partido` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NULL DEFAULT NULL,
  `lugar` VARCHAR(45) NULL DEFAULT NULL,
  `equipo1` INT NULL DEFAULT NULL,
  `golesEquipo1` INT NULL DEFAULT NULL,
  `equipo2` INT NULL DEFAULT NULL,
  `golesEquipo2` INT NULL DEFAULT NULL,
  `ronda_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_partido_ronda1_idx` (`ronda_id` ASC),
  CONSTRAINT `fk_partido_ronda1`
    FOREIGN KEY (`ronda_id`)
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
  `id` INT NOT NULL AUTO_INCREMENT,
  `equipo1` INT NULL DEFAULT NULL,
  `golesEquipo1` INT NULL DEFAULT NULL,
  `equipo2` INT NULL DEFAULT NULL,
  `golesEquipo2` INT NULL DEFAULT NULL,
  `Empleado_id` INT NOT NULL,
  `partido_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Apuesta_Empleado1_idx` (`Empleado_id` ASC),
  INDEX `fk_apuesta_partido1_idx` (`partido_id` ASC),
  CONSTRAINT `fk_Apuesta_Empleado1`
    FOREIGN KEY (`Empleado_id`)
    REFERENCES `zeusco_mundial2018`.`empleado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_apuesta_partido1`
    FOREIGN KEY (`partido_id`)
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
  `id` INT NOT NULL AUTO_INCREMENT,
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
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `ganados` INT NULL DEFAULT NULL,
  `perdidos` INT NULL DEFAULT NULL,
  `empatados` INT NULL DEFAULT NULL,
  `golesFavor` INT NULL DEFAULT NULL,
  `golesContra` INT NULL DEFAULT NULL,
  `puntos` INT NULL DEFAULT NULL,
  `Grupo_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Equipo_Grupo1_idx` (`Grupo_id` ASC),
  CONSTRAINT `fk_Equipo_Grupo1`
    FOREIGN KEY (`Grupo_id`)
    REFERENCES `zeusco_mundial2018`.`grupo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `zeusco_mundial2018`.`login`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `zeusco_mundial2018`.`login` ;

CREATE TABLE IF NOT EXISTS `zeusco_mundial2018`.`login` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `rol` VARCHAR(45) NULL DEFAULT NULL,
  `Empleado_id` INT NOT NULL,
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


-- -----------------------------------------------------
-- Table `zeusco_mundial2018`.`resultado`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `zeusco_mundial2018`.`resultado` ;

CREATE TABLE IF NOT EXISTS `zeusco_mundial2018`.`resultado` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `partidosExactos` INT NULL,
  `partidosGanados` INT NULL,
  `partidosEmpatados` INT NULL,
  `puntos` INT NULL,
  `empleado_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_resultado_empleado1_idx` (`empleado_id` ASC),
  CONSTRAINT `fk_resultado_empleado1`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `zeusco_mundial2018`.`empleado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;