CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`SANCTION` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `time` BIGINT,
    `lap` INT,
    `sector` INT,
    `penalty` BIGINT,
    `description` VARCHAR(256),
    `race_id` INT,
    `grid_id` INT,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`race_id`) REFERENCES `RACE`(`id`),
    FOREIGN KEY (`grid_id`) REFERENCES `GRID`(`id`)
    ) ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`GRID_RACE` (
    `race_id` INT,
    `grid_id` INT,
    `points` BIGINT,
    `first_sector` BIGINT,
    `second_sector` BIGINT,
    `third_sector` BIGINT,
    `final_time` BIGINT,
    `total_laps` INTEGER,
    PRIMARY KEY (`race_id`, `grid_id`),
    FOREIGN KEY (`race_id`) REFERENCES `RACE`(`id`),
    FOREIGN KEY (`grid_id`) REFERENCES `GRID`(`id`)
    ) ENGINE = InnoDB;
