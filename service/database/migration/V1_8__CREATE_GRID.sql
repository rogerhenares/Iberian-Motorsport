CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`GRID` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `championship_id` INT,
    `car_id` INT,
    `car_number` INT,
    `car_license` VARCHAR(45) NOT NULL,
    `car_number` INT,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`championship_id`) REFERENCES CHAMPIONSHIP(`id`),
    FOREIGN KEY (`car_id`) REFERENCES CAR(`id`),
    UNIQUE INDEX `roleROLE_UNIQUE` (`role` ASC))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`GRID_USER` (
    `steam_id` BIGINT(25) NOT NULL,
    `grid_id` INT,
    `grid_manager` TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`steam_id`, `grid_id`),
    FOREIGN KEY (`steam_id`) REFERENCES USER(`steam_id`),
    FOREIGN KEY (`grid_id`) REFERENCES ROLE(`id`))
ENGINE = InnoDB;
