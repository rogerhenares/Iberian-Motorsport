CREATE TABLE IF NOT EXISTS `iberian-motorsport-db`.`CAR` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `manufacturer` VARCHAR(45) NOT NULL,
    `model` VARCHAR(45) NOT NULL,
    `category` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`))
ENGINE = InnoDB;

INSERT INTO `iberian-motorsport-db`.`CAR`
    (`manufacturer`, `model`, `category`)
VALUES
    ("AMR", "V12 Vantage", "GT3"),
    ("AMR", "V8 Vantage", "GT3"),
    ("AMR", "V8 Vantage", "GT4"),
    ("Audi", "R8 LMS", "GT3"),
    ("Audi", "R8 LMS", "GT4"),
    ("Audi", "R8 LMS evo", "GT3"),
    ("Audi", "R8 LMS evo II", "GT3"),
    ("Bentley", "Continental 2018", "GT3"),
    ("Bentley", "Continental 2015", "GT3"),
    ("BMW", "M4", "GT3"),
    ("BMW", "M4", "GT4"),
    ("BMW", "M6", "GT3"),
    ("BMW", "M2 CS", "TCX"),
    ("Jaguar", "M2 CS", "TCX"),
    ("Ferrari", "296", "GT3"),
    ("Ferrari", "488", "GT3"),
    ("Ferrari", "488 evo", "GT3"),
    ("Ferrari", "488 Challenge Evo", "CHL"),
    ("Honda", "NSX", "GT3"),
    ("Honda", "NSX Evo", "GT3"),
    ("Lamborghini", "Huracan", "GT3"),
    ("Lamborghini", "Huracan Evo", "GT3"),
    ("Lamborghini", "Huracan Evo2", "GT3"),
    ("Lamborghini", "Huracan ST", "ST"),
    ("Lamborghini", "Huracan ST EVO2", "ST"),
    ("Lexus", "RC F", "GT3"),
    ("McLaren", "650S", "GT3"),
    ("McLaren", "720S", "GT3"),
    ("McLaren", "720S Evo", "GT3"),
    ("Mercedes", "Mercedes-AMG", "GT3"),
    ("Mercedes", "Mercedes-AMG 2020", "GT3"),
    ("Mercedes", "Mercedes-AMG", "GT4"),
    ("Nissan", "GT-R Nismo 2015", "GT3"),
    ("Nissan", "GT-R Nismo 2018", "GT3"),
    ("Porsche", "991 R", "GT3"),
    ("Porsche", "992 R", "GT3"),
    ("Porsche", "991 II R", "GT3"),
    ("Porsche", "718 Cayman Clubsport", "GT4"),
    ("Porsche", "991 II Cup", "CUP"),
    ("Porsche", "992 Cup", "CUP"),
    ("Reiter", "Engineering R-EX", "GT3"),
    ("Alpine", "A110", "GT4"),
    ("Chevrolet", "Camaro", "GT4"),
    ("Ginetta", "G55", "GT4"),
    ("KTM", "X-Bow", "GT4"),
    ("Maserati", "GranTurismo MC", "GT4");