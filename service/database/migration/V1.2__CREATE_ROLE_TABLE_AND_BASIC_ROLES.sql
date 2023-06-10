CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`ROLE` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `role` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `roleROLE_UNIQUE` (`role` ASC))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`USER_ROLE` (
    `user_id` INT,
    `role_id` INT,
   PRIMARY KEY (`user_id`, `role_id`),
   FOREIGN KEY (`user_id`) REFERENCES USER(`id`),
   FOREIGN KEY (`role_id`) REFERENCES ROLE(`id`))
ENGINE = InnoDB;

INSERT INTO `iberian-motorsport-db`.`ROLE`
    (`id`,`role`)
VALUES
    (1,"ADMIN");

INSERT INTO `iberian-motorsport-db`.`ROLE`
    (`id`,`role`)
VALUES
    (2,"BASIC_USER");
