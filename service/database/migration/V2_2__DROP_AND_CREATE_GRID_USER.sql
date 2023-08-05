DROP TABLE IF EXISTS `iberian-motorsport-db`.`GRID_USER`;
CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`GRID_USER` (
    `user_id` INT NOT NULL,
    `grid_id` INT,
    `grid_manager` TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`user_id`, `grid_id`),
    FOREIGN KEY (`user_id`) REFERENCES USER(`id`),
    FOREIGN KEY (`grid_id`) REFERENCES GRID(`id`))
ENGINE = InnoDB;