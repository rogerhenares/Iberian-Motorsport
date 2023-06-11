CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`USER_AUTH` (
    `steam_id` BIGINT(25) NOT NULL,
    `token` VARCHAR(150) NOT NULL,
    `last_login` LONG NOT NULL,
    PRIMARY KEY (`steam_id`))
ENGINE = InnoDB;
