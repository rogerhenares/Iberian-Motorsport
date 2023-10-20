CREATE TABLE `BOP` (
    `race_id` int NOT NULL,
    `car_id` int NOT NULL,
    `ballastKg` int DEFAULT NULL,
    `restrictor` int DEFAULT NULL,
    PRIMARY KEY (`race_id`, `car_id`),
    KEY `race_id` (`race_id`),
    CONSTRAINT `BOP_ibfk_1` FOREIGN KEY (`race_id`) REFERENCES `RACE` (`id`),
    CONSTRAINT `BOP_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `CAR` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;