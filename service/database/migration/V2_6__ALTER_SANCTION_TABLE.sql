ALTER TABLE `iberian-motorsport-db`.`SANCTION`
    MODIFY `penalty` VARCHAR(45) NOT NULL;
ALTER TABLE `iberian-motorsport-db`.`SANCTION`
    ADD COLUMN `in_game` TINYINT NOT NULL DEFAULT 0;
ALTER TABLE `iberian-motorsport-db`.`SANCTION`
    CHANGE `description` `reason` varchar(256) NOT NULL;
ALTER TABLE `iberian-motorsport-db`.`SANCTION`
    DROP COLUMN `time`;
ALTER TABLE `iberian-motorsport-db`.`SANCTION`
    DROP COLUMN `sector`;
