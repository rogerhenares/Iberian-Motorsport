ALTER TABLE `iberian-motorsport-db`.`GRID`
    ADD COLUMN `license_points` NUMERIC NOT NULL DEFAULT 12;
ALTER TABLE `iberian-motorsport-db`.`SANCTION`
    ADD COLUMN `license_points` NUMERIC NULL;
