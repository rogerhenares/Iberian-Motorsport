CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`CHAMPIONSHIP_CATEGORY` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `category` VARCHAR(45),
    `max` INT,
    `championship_id` INT,
    `car_id` INT,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`championship_id`) REFERENCES CHAMPIONSHIP(`id`),
    FOREIGN KEY (`car_id`) REFERENCES CAR(`id`))
    ENGINE = InnoDB;
