ALTER TABLE `iberian-motorsport-db`.`GRID`
    ADD COLUMN `disabled` TINYINT NOT NULL DEFAULT 0;

ALTER TABLE `iberian-motorsport-db`.`CHAMPIONSHIP`
    ADD COLUMN `disabled` TINYINT NOT NULL DEFAULT 1;

ALTER TABLE `iberian-motorsport-db`.`CHAMPIONSHIP`
    ADD COLUMN `finished` TINYINT NOT NULL DEFAULT 0;

ALTER TABLE `iberian-motorsport-db`.`CHAMPIONSHIP`
    ADD COLUMN `started` TINYINT NOT NULL DEFAULT 0;