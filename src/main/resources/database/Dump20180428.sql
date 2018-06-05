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
  `penalesEquipo1` INT NULL,
  `equipo2` INT NULL DEFAULT NULL,
  `golesEquipo2` INT NULL DEFAULT NULL,
  `penalesEquipo2` INT NULL,
  `editado` BIT NULL DEFAULT 0,
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
  `equipo1` VARCHAR(45) NULL DEFAULT NULL,
  `golesEquipo1` INT NULL DEFAULT NULL,
  `equipo2` VARCHAR(45) NULL DEFAULT NULL,
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
  `jugados` INT NULL DEFAULT 0,
  `ganados` INT NULL DEFAULT 0,
  `perdidos` INT NULL DEFAULT 0,
  `empatados` INT NULL DEFAULT 0,
  `golesFavor` INT NULL DEFAULT 0,
  `golesContra` INT NULL DEFAULT 0,
  `puntos` INT NULL DEFAULT 0,
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
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `rol` VARCHAR(45) NULL DEFAULT NULL,
  `Empleado_id` INT NOT NULL,
  PRIMARY KEY (`id`, `Empleado_id`),
  UNIQUE INDEX `user_UNIQUE` (`username` ASC),
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
  UNIQUE INDEX `empleado_id_UNIQUE` (`empleado_id` ASC),
  CONSTRAINT `fk_resultado_empleado1`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `zeusco_mundial2018`.`empleado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `zeusco_mundial2018`.`equipo_hist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `zeusco_mundial2018`.`equipo_hist` ;

CREATE TABLE IF NOT EXISTS `zeusco_mundial2018`.`equipo_hist` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `jugados` INT NULL,
  `ganados` INT NULL,
  `perdidos` INT NULL,
  `empatados` INT NULL,
  `golesFavor` INT NULL,
  `golesContra` INT NULL,
  `puntos` INT NULL,
  `equipo_id` INT NULL,
  `grupo_id` INT NULL,
  `fechaModificacion` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `zeusco_mundial2018`.`resultado_hist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `zeusco_mundial2018`.`resultado_hist` ;

CREATE TABLE IF NOT EXISTS `zeusco_mundial2018`.`resultado_hist` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `partidosExactos` INT NULL,
  `partidosGanados` INT NULL,
  `partidosEmpatados` INT NULL,
  `puntos` INT NULL,
  `empleado_id` INT NULL,
  `fechaModificacion` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

USE `zeusco_mundial2018`;

DELIMITER $$

USE `zeusco_mundial2018`$$
DROP TRIGGER IF EXISTS `zeusco_mundial2018`.`equipo_AFTER_UPDATE` $$
USE `zeusco_mundial2018`$$
CREATE DEFINER = CURRENT_USER TRIGGER `zeusco_mundial2018`.`equipo_AFTER_UPDATE` AFTER UPDATE ON `equipo` FOR EACH ROW
BEGIN
    insert into `zeusco_mundial2018`.`equipo_hist` (`id`,`jugados`,`ganados`,`perdidos`,`empatados`,`golesFavor`,`golesContra`,`puntos`,`equipo_id`,`grupo_id`,`fechaModificacion`) values (null, OLD.jugados, OLD.ganados, OLD.perdidos, OLD.empatados, OLD.golesFavor, OLD.golesContra, OLD.puntos, OLD.id, OLD.grupo_id, now());
END$$


USE `zeusco_mundial2018`$$
DROP TRIGGER IF EXISTS `zeusco_mundial2018`.`resultado_AFTER_UPDATE` $$
USE `zeusco_mundial2018`$$
CREATE DEFINER = CURRENT_USER TRIGGER `zeusco_mundial2018`.`resultado_AFTER_UPDATE` AFTER UPDATE ON `resultado` FOR EACH ROW
BEGIN
    insert into `zeusco_mundial2018`.`resultado_hist` (`id`,`partidosExactos`,`partidosGanados`,`partidosEmpatados`,`puntos`,`empleado_id`,`fechaModificacion`) values (null, OLD.partidosExactos, OLD.partidosGanados, OLD.partidosEmpatados, OLD.puntos, OLD.empleado_id, now());
END$$


USE `zeusco_mundial2018`$$
DROP TRIGGER IF EXISTS `zeusco_mundial2018`.`equipo_AFTER_INSERT` $$
USE `zeusco_mundial2018`$$
CREATE DEFINER = CURRENT_USER TRIGGER `zeusco_mundial2018`.`equipo_AFTER_INSERT` AFTER INSERT ON `equipo` FOR EACH ROW
BEGIN
    insert into `zeusco_mundial2018`.`equipo_hist` (`id`,`jugados`,`ganados`,`perdidos`,`empatados`,`golesFavor`,`golesContra`,`puntos`,`equipo_id`,`grupo_id`,`fechaModificacion`) values (null, 0, 0, 0, 0, 0, 0, 0, NEW.id, NEW.grupo_id, now() - interval 1 day);
END$$


USE `zeusco_mundial2018`$$
DROP TRIGGER IF EXISTS `zeusco_mundial2018`.`resultado_AFTER_INSERT` $$
USE `zeusco_mundial2018`$$
CREATE DEFINER = CURRENT_USER TRIGGER `zeusco_mundial2018`.`resultado_AFTER_INSERT` AFTER INSERT ON `resultado` FOR EACH ROW
BEGIN
	insert into `zeusco_mundial2018`.`resultado_hist` (`id`,`partidosExactos`,`partidosGanados`,`partidosEmpatados`,`puntos`,`empleado_id`,`fechaModificacion`) values (null, 0, 0, 0, 0, new.empleado_id, now() - interval 1 day);
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `zeusco_mundial2018`.`empleado`
-- -----------------------------------------------------
START TRANSACTION;
USE `zeusco_mundial2018`;
INSERT INTO `zeusco_mundial2018`.`empleado` (`id`, `nombre`, `email`) VALUES (1, 'Administrador', 'admin');

COMMIT;


-- -----------------------------------------------------
-- Data for table `zeusco_mundial2018`.`ronda`
-- -----------------------------------------------------
START TRANSACTION;
USE `zeusco_mundial2018`;
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (1, 'Fase de grupos', '2018-06-14');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (2, 'Fase de grupos', '2018-06-15');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (3, 'Fase de grupos', '2018-06-16');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (4, 'Fase de grupos', '2018-06-17');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (5, 'Fase de grupos', '2018-06-18');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (6, 'Fase de grupos', '2018-06-19');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (7, 'Fase de grupos', '2018-06-20');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (8, 'Fase de grupos', '2018-06-21');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (9, 'Fase de grupos', '2018-06-22');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (10, 'Fase de grupos', '2018-06-23');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (11, 'Fase de grupos', '2018-06-24');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (12, 'Fase de grupos', '2018-06-25');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (13, 'Fase de grupos', '2018-06-26');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (14, 'Fase de grupos', '2018-06-27');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (15, 'Fase de grupos', '2018-06-28');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (16, 'Octavos de final', '2018-06-30');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (17, 'Octavos de final', '2018-07-01');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (18, 'Octavos de final', '2018-07-02');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (19, 'Octavos de final', '2018-07-03');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (20, 'Cuartos de final', '2018-07-06');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (21, 'Cuartos de final', '2018-07-07');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (22, 'Semifinales', '2018-07-10');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (23, 'Semifinales', '2018-07-11');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (24, 'Partido por el tercer lugar', '2018-07-14');
INSERT INTO `zeusco_mundial2018`.`ronda` (`id`, `nombre`, `fecha`) VALUES (25, 'Final', '2018-07-15');

COMMIT;


-- -----------------------------------------------------
-- Data for table `zeusco_mundial2018`.`partido`
-- -----------------------------------------------------
START TRANSACTION;
USE `zeusco_mundial2018`;
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (1, '2018-06-14', 'Luzhniki Stadium', 1, NULL, NULL, 2, NULL, NULL, 0, 1);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (2, '2018-06-15', 'Ekaterinburg Arena', 3, NULL, NULL, 4, NULL, NULL, 0, 2);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (3, '2018-06-15', 'Estadio de San Petersburgo', 7, NULL, NULL, 8, NULL, NULL, 0, 2);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (4, '2018-06-15', 'Fisht Stadium', 5, NULL, NULL, 6, NULL, NULL, 0, 2);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (5, '2018-06-16', 'Kazan Arena', 9, NULL, NULL, 10, NULL, NULL, 0, 3);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (6, '2018-06-16', 'Spartak Stadium', 13, NULL, NULL, 14, NULL, NULL, 0, 3);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (7, '2018-06-16', 'Mordovia Arena', 11, NULL, NULL, 12, NULL, NULL, 0, 3);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (8, '2018-06-16', 'Kaliningrad Stadium', 15, NULL, NULL, 16, NULL, NULL, 0, 3);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (9, '2018-06-17', 'Samara Arena', 19, NULL, NULL, 20, NULL, NULL, 0, 4);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (10, '2018-06-17', 'Luzhniki Stadium', 21, NULL, NULL, 22, NULL, NULL, 0, 4);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (11, '2018-06-17', 'Rostov Arena', 17, NULL, NULL, 18, NULL, NULL, 0, 4);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (12, '2018-06-18', 'Nizhny Novgorod Stadium', 23, NULL, NULL, 24, NULL, NULL, 0, 5);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (13, '2018-06-18', 'Fisht Stadium', 25, NULL, NULL, 26, NULL, NULL, 0, 5);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (14, '2018-06-18', 'Volgograd Arena', 27, NULL, NULL, 28, NULL, NULL, 0, 5);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (15, '2018-06-19', 'Mordovia Arena', 31, NULL, NULL, 32, NULL, NULL, 0, 6);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (16, '2018-06-19', 'Spartak Stadium', 29, NULL, NULL, 30, NULL, NULL, 0, 6);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (17, '2018-06-19', 'Estadio de San Petersburgo', 1, NULL, NULL, 3, NULL, NULL, 0, 6);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (18, '2018-06-20', 'Luzhniki Stadium', 5, NULL, NULL, 7, NULL, NULL, 0, 7);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (19, '2018-06-20', 'Rostov Arena', 4, NULL, NULL, 2, NULL, NULL, 0, 7);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (20, '2018-06-20', 'Kazan Arena', 8, NULL, NULL, 6, NULL, NULL, 0, 7);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (21, '2018-06-21', 'Samara Arena', 12, NULL, NULL, 10, NULL, NULL, 0, 8);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (22, '2018-06-21', 'Ekaterinburg Arena', 9, NULL, NULL, 11, NULL, NULL, 0, 8);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (23, '2018-06-21', 'Nizhny Novgorod Stadium', 13, NULL, NULL, 15, NULL, NULL, 0, 8);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (24, '2018-06-22', 'Estadio de San Petersburgo', 17, NULL, NULL, 19, NULL, NULL, 0, 9);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (25, '2018-06-22', 'Volgograd Arena', 16, NULL, NULL, 14, NULL, NULL, 0, 9);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (27, '2018-06-22', 'Kaliningrad Stadium', 20, NULL, NULL, 18, NULL, NULL, 0, 9);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (28, '2018-06-23', 'Spartak Stadium', 25, NULL, NULL, 27, NULL, NULL, 0, 10);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (29, '2018-06-23', 'Rostov Arena', 24, NULL, NULL, 22, NULL, NULL, 0, 10);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (30, '2018-06-23', 'Fisht Stadium', 21, NULL, NULL, 23, NULL, NULL, 0, 10);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (31, '2018-06-24', 'Nizhny Novgorod Stadium', 28, NULL, NULL, 26, NULL, NULL, 0, 11);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (32, '2018-06-24', 'Ekaterinburg Arena', 32, NULL, NULL, 30, NULL, NULL, 0, 11);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (33, '2018-06-24', 'Kazan Arena', 29, NULL, NULL, 31, NULL, NULL, 0, 11);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (34, '2018-06-25', 'Samara Arena', 4, NULL, NULL, 1, NULL, NULL, 0, 12);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (35, '2018-06-25', 'Volgograd Arena', 2, NULL, NULL, 3, NULL, NULL, 0, 12);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (36, '2018-06-25', 'Kaliningrad Stadium', 6, NULL, NULL, 7, NULL, NULL, 0, 12);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (37, '2018-06-25', 'Mordovia Arena', 8, NULL, NULL, 5, NULL, NULL, 0, 12);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (38, '2018-06-26', 'Fisht Stadium', 10, NULL, NULL, 11, NULL, NULL, 0, 13);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (39, '2018-06-26', 'Luzhniki Stadium', 12, NULL, NULL, 9, NULL, NULL, 0, 13);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (40, '2018-06-26', 'Estadio de San Petersburgo', 16, NULL, NULL, 13, NULL, NULL, 0, 13);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (41, '2018-06-26', 'Rostov Arena', 14, NULL, NULL, 15, NULL, NULL, 0, 13);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (42, '2018-06-27', 'Kazan Arena', 24, NULL, NULL, 21, NULL, NULL, 0, 14);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (43, '2018-06-27', 'Ekaterinburg Arena', 22, NULL, NULL, 23, NULL, NULL, 0, 14);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (44, '2018-06-27', 'Spartak Stadium', 20, NULL, NULL, 17, NULL, NULL, 0, 14);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (45, '2018-06-27', 'Nizhny Novgorod Stadium', 18, NULL, NULL, 19, NULL, NULL, 0, 14);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (46, '2018-06-28', 'Volgograd Arena', 32, NULL, NULL, 29, NULL, NULL, 0, 15);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (47, '2018-06-28', 'Samara Arena', 30, NULL, NULL, 31, NULL, NULL, 0, 15);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (48, '2018-06-28', 'Mordovia Arena', 26, NULL, NULL, 27, NULL, NULL, 0, 15);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (49, '2018-06-28', 'Kaliningrad Stadium', 28, NULL, NULL, 25, NULL, NULL, 0, 15);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (50, '2018-06-30', 'Kazan Arena', NULL, NULL, NULL, NULL, NULL, NULL, 0, 16);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (51, '2018-06-30', 'Fisht Stadium', NULL, NULL, NULL, NULL, NULL, NULL, 0, 16);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (52, '2018-07-01', 'Luzhniki Stadium', NULL, NULL, NULL, NULL, NULL, NULL, 0, 17);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (53, '2018-07-01', 'Nizhny Novgorod Stadium', NULL, NULL, NULL, NULL, NULL, NULL, 0, 17);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (54, '2018-07-02', 'Samara Arena', NULL, NULL, NULL, NULL, NULL, NULL, 0, 18);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (55, '2018-07-02', 'Rostov Arena', NULL, NULL, NULL, NULL, NULL, NULL, 0, 18);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (56, '2018-07-03', 'Estadio de San Petersburgo', NULL, NULL, NULL, NULL, NULL, NULL, 0, 19);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (57, '2018-07-03', 'Spartak Stadium', NULL, NULL, NULL, NULL, NULL, NULL, 0, 19);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (58, '2018-07-06', 'Nizhny Novgorod Stadium', NULL, NULL, NULL, NULL, NULL, NULL, 0, 20);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (59, '2018-07-06', 'Kazan Arena', NULL, NULL, NULL, NULL, NULL, NULL, 0, 20);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (60, '2018-07-07', 'Samara Arena', NULL, NULL, NULL, NULL, NULL, NULL, 0, 21);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (61, '2018-07-07', 'Fisht Stadium', NULL, NULL, NULL, NULL, NULL, NULL, 0, 21);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (62, '2018-07-10', 'Estadio de San Petersburgo', NULL, NULL, NULL, NULL, NULL, NULL, 0, 22);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (63, '2018-07-11', 'Luzhniki Stadium', NULL, NULL, NULL, NULL, NULL, NULL, 0, 23);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (64, '2018-07-14', 'Estadio de San Petersburgo', NULL, NULL, NULL, NULL, NULL, NULL, 0, 24);
INSERT INTO `zeusco_mundial2018`.`partido` (`id`, `fecha`, `lugar`, `equipo1`, `golesEquipo1`, `penalesEquipo1`, `equipo2`, `golesEquipo2`, `penalesEquipo2`, `editado`, `ronda_id`) VALUES (65, '2018-07-15', 'Luzhniki Stadium', NULL, NULL, NULL, NULL, NULL, NULL, 0, 25);

COMMIT;


-- -----------------------------------------------------
-- Data for table `zeusco_mundial2018`.`grupo`
-- -----------------------------------------------------
START TRANSACTION;
USE `zeusco_mundial2018`;
INSERT INTO `zeusco_mundial2018`.`grupo` (`id`, `nombre`) VALUES (1, 'A');
INSERT INTO `zeusco_mundial2018`.`grupo` (`id`, `nombre`) VALUES (2, 'B');
INSERT INTO `zeusco_mundial2018`.`grupo` (`id`, `nombre`) VALUES (3, 'C');
INSERT INTO `zeusco_mundial2018`.`grupo` (`id`, `nombre`) VALUES (4, 'D');
INSERT INTO `zeusco_mundial2018`.`grupo` (`id`, `nombre`) VALUES (5, 'E');
INSERT INTO `zeusco_mundial2018`.`grupo` (`id`, `nombre`) VALUES (6, 'F');
INSERT INTO `zeusco_mundial2018`.`grupo` (`id`, `nombre`) VALUES (7, 'G');
INSERT INTO `zeusco_mundial2018`.`grupo` (`id`, `nombre`) VALUES (8, 'H');

COMMIT;


-- -----------------------------------------------------
-- Data for table `zeusco_mundial2018`.`equipo`
-- -----------------------------------------------------
START TRANSACTION;
USE `zeusco_mundial2018`;
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (1, 'Rusia', 0, 0, 0, 0, 0, 0, 0, 1);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (2, 'Arabia Saudí', 0, 0, 0, 0, 0, 0, 0, 1);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (3, 'Egipto', 0, 0, 0, 0, 0, 0, 0, 1);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (4, 'Uruguay', 0, 0, 0, 0, 0, 0, 0, 1);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (5, 'Portugal', 0, 0, 0, 0, 0, 0, 0, 2);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (6, 'España', 0, 0, 0, 0, 0, 0, 0, 2);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (7, 'Marruecos', 0, 0, 0, 0, 0, 0, 0, 2);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (8, 'RI de Irán', 0, 0, 0, 0, 0, 0, 0, 2);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (9, 'Francia', 0, 0, 0, 0, 0, 0, 0, 3);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (10, 'Australia', 0, 0, 0, 0, 0, 0, 0, 3);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (11, 'Perú', 0, 0, 0, 0, 0, 0, 0, 3);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (12, 'Dinamarca', 0, 0, 0, 0, 0, 0, 0, 3);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (13, 'Argentina', 0, 0, 0, 0, 0, 0, 0, 4);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (14, 'Islandia', 0, 0, 0, 0, 0, 0, 0, 4);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (15, 'Croacia', 0, 0, 0, 0, 0, 0, 0, 4);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (16, 'Nigeria', 0, 0, 0, 0, 0, 0, 0, 4);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (17, 'Brasil', 0, 0, 0, 0, 0, 0, 0, 5);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (18, 'Suiza', 0, 0, 0, 0, 0, 0, 0, 5);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (19, 'Costa Rica', 0, 0, 0, 0, 0, 0, 0, 5);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (20, 'Serbia', 0, 0, 0, 0, 0, 0, 0, 5);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (21, 'Alemania', 0, 0, 0, 0, 0, 0, 0, 6);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (22, 'México', 0, 0, 0, 0, 0, 0, 0, 6);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (23, 'Suecia', 0, 0, 0, 0, 0, 0, 0, 6);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (24, 'República de Corea', 0, 0, 0, 0, 0, 0, 0, 6);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (25, 'Bélgica', 0, 0, 0, 0, 0, 0, 0, 7);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (26, 'Panamá', 0, 0, 0, 0, 0, 0, 0, 7);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (27, 'Túnez', 0, 0, 0, 0, 0, 0, 0, 7);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (28, 'Inglaterra', 0, 0, 0, 0, 0, 0, 0, 7);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (29, 'Polonia', 0, 0, 0, 0, 0, 0, 0, 8);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (30, 'Senegal', 0, 0, 0, 0, 0, 0, 0, 8);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (31, 'Colombia', 0, 0, 0, 0, 0, 0, 0, 8);
INSERT INTO `zeusco_mundial2018`.`equipo` (`id`, `nombre`, `jugados`, `ganados`, `perdidos`, `empatados`, `golesFavor`, `golesContra`, `puntos`, `Grupo_id`) VALUES (32, 'Japón', 0, 0, 0, 0, 0, 0, 0, 8);

COMMIT;


-- -----------------------------------------------------
-- Data for table `zeusco_mundial2018`.`login`
-- -----------------------------------------------------
START TRANSACTION;
USE `zeusco_mundial2018`;
INSERT INTO `zeusco_mundial2018`.`login` (`id`, `username`, `password`, `rol`, `Empleado_id`) VALUES (1, 'admin', 'admin', 'admin', 1);

COMMIT;
