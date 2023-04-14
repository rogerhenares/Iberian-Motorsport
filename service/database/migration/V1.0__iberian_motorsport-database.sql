-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema iberian-motorsport-db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema iberian-motorsport-db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `iberian-motorsport-db` DEFAULT CHARACTER SET utf8 ;
USE `iberian-motorsport-db` ;

-- -----------------------------------------------------
-- Table `iberian-motorsport-db`.`USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`USER` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `player_id` BIGINT(25) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `short_name` VARCHAR(45) NULL,
  `nationality` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idUSER_UNIQUE` (`id` ASC),
  UNIQUE INDEX `steam_id_UNIQUE` (`player_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iberian-motorsport-db`.`CHAMPIONSHIP`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`CHAMPIONSHIP` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(256) NULL,
  `admin_password` VARCHAR(45) NOT NULL,
  `car_group` VARCHAR(45) NOT NULL,
  `track_medals_requirement` INT NULL DEFAULT 0,
  `safety_rating_requirement` INT NULL DEFAULT -1,
  `racecraft_rating_requirement` INT NULL DEFAULT -1,
  `password` VARCHAR(45) NOT NULL,
  `spectator_password` VARCHAR(45) NOT NULL,
  `max_car_slots` INT NOT NULL,
  `dump_leaderboards` INT NULL DEFAULT 1,
  `is_race_locked` INT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iberian-motorsport-db`.`RACE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`RACE` (
  `id` INT NOT NULL,
  `championship_id` INT NOT NULL,
  `track` VARCHAR(45) NOT NULL,
  `pre_race_waiting_time_seconds` INT NOT NULL,
  `session_over_time_seconds` INT NOT NULL,
  `ambient_temp` INT NOT NULL,
  `cloud_level` FLOAT NOT NULL,
  `rain` FLOAT NOT NULL,
  `weather_randomness` INT NOT NULL,
  `post_qualy_seconds` INT NOT NULL,
  `post_race_seconds` INT NOT NULL,
  `server_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `RACE_CHAMPIONSHIP_FK_idx` (`championship_id` ASC),
  CONSTRAINT `RACE_CHAMPIONSHIP_FK`
    FOREIGN KEY (`championship_id`)
    REFERENCES `iberian-motorsport-db`.`CHAMPIONSHIP` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iberian-motorsport-db`.`SESSION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`SESSION` (
  `id` INT NOT NULL,
  `hour_of_day` INT NOT NULL,
  `day_of_weekend` INT NOT NULL,
  `time_multiplier` INT NOT NULL,
  `session_type` VARCHAR(1) NOT NULL,
  `session_duration_minutes` INT NOT NULL,
  `race_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `SESSION_RACE_FG_idx` (`race_id` ASC),
  CONSTRAINT `SESSION_RACE_FG`
    FOREIGN KEY (`race_id`)
    REFERENCES `iberian-motorsport-db`.`RACE` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iberian-motorsport-db`.`USER_CHAMPIONSHIP`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`USER_CHAMPIONSHIP` (
  `user_id` INT NOT NULL,
  `championship_id` INT NOT NULL,
  `car` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`, `championship_id`),
  INDEX `CHAMPIONSHIP_KF_idx` (`championship_id` ASC),
  CONSTRAINT `USER_FK`
    FOREIGN KEY (`user_id`)
    REFERENCES `iberian-motorsport-db`.`USER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CHAMPIONSHIP_KF`
    FOREIGN KEY (`championship_id`)
    REFERENCES `iberian-motorsport-db`.`CHAMPIONSHIP` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `iberian-motorsport-db`.`RACE_RULES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`RACE_RULES` (
  `qualify_standing_type` INT NOT NULL,
  `id` INT NOT NULL,
  `race_id` INT NOT NULL,
  `pit_window_length_sec` INT NOT NULL,
  `driver_stint_time_sec` INT NOT NULL,
  `mandatory_pitstop_count` INT NOT NULL,
  `max_total_driving_time` INT NOT NULL,
  `max_drivers_count` INT NOT NULL,
  `is_refuelling_allowed_in_race` TINYINT NOT NULL,
  `is_refuelling_time_fixed` TINYINT NOT NULL,
  `is_mandatory_pitstop_refuelling_required` TINYINT NOT NULL,
  `is_mandatory_pitstop_tyre_change_required` TINYINT NOT NULL,
  `is_mandatory_pitstop_swap_driver_required` TINYINT NOT NULL,
  `tyre_set_count` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `RACE_RULES_FK_idx` (`race_id` ASC),
  CONSTRAINT `RACE_RULES_FK`
    FOREIGN KEY (`race_id`)
    REFERENCES `iberian-motorsport-db`.`RACE` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
