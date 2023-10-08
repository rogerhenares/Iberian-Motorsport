-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: iberian-motorsport-db
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `CAR`
--

DROP TABLE IF EXISTS `CAR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CAR` (
                       `id` int NOT NULL AUTO_INCREMENT,
                       `manufacturer` varchar(45) NOT NULL,
                       `model` varchar(45) NOT NULL,
                       `category` varchar(45) NOT NULL,
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CHAMPIONSHIP`
--

DROP TABLE IF EXISTS `CHAMPIONSHIP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CHAMPIONSHIP` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `name` varchar(45) NOT NULL,
                                `description` varchar(256) DEFAULT NULL,
                                `admin_password` varchar(45) NOT NULL,
                                `car_group` varchar(45) NOT NULL,
                                `track_medals_requirement` int DEFAULT '0',
                                `safety_rating_requirement` int DEFAULT '-1',
                                `racecraft_rating_requirement` int DEFAULT '-1',
                                `password` varchar(45) NOT NULL,
                                `spectator_password` varchar(45) NOT NULL,
                                `max_car_slots` int NOT NULL,
                                `dump_leaderboards` int DEFAULT '1',
                                `is_race_locked` int DEFAULT '1',
                                `image_content` longtext,
                                `randomize_track_when_empty` int DEFAULT NULL,
                                `central_entry_list_path` varchar(45) DEFAULT NULL,
                                `allow_auto_dq` int DEFAULT NULL,
                                `short_formation_lap` int DEFAULT NULL,
                                `dump_entry_list` int DEFAULT NULL,
                                `formation_lap_type` int DEFAULT NULL,
                                `ignore_premature_disconnects` int DEFAULT NULL,
                                `start_date` datetime DEFAULT NULL,
                                `disabled` tinyint NOT NULL DEFAULT '1',
                                `finished` tinyint NOT NULL DEFAULT '0',
                                `started` tinyint NOT NULL DEFAULT '0',
                                `style` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=421 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CHAMPIONSHIP_CATEGORY`
--

DROP TABLE IF EXISTS `CHAMPIONSHIP_CATEGORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CHAMPIONSHIP_CATEGORY` (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `category` varchar(45) DEFAULT NULL,
                                         `max` int DEFAULT NULL,
                                         `championship_id` int DEFAULT NULL,
                                         `car_id` int DEFAULT NULL,
                                         PRIMARY KEY (`id`),
                                         KEY `championship_id` (`championship_id`),
                                         KEY `car_id` (`car_id`),
                                         CONSTRAINT `CHAMPIONSHIP_CATEGORY_ibfk_1` FOREIGN KEY (`championship_id`) REFERENCES `CHAMPIONSHIP` (`id`),
                                         CONSTRAINT `CHAMPIONSHIP_CATEGORY_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `CAR` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `GRID`
--

DROP TABLE IF EXISTS `GRID`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GRID` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `championship_id` int DEFAULT NULL,
                        `car_id` int DEFAULT NULL,
                        `car_number` int DEFAULT NULL,
                        `car_license` varchar(45) NOT NULL,
                        `disabled` tinyint NOT NULL DEFAULT '0',
                        `team_name` varchar(45) NOT NULL,
                        `license_points` decimal(10,0) NOT NULL DEFAULT '12',
                        `password` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        KEY `championship_id` (`championship_id`),
                        KEY `car_id` (`car_id`),
                        CONSTRAINT `GRID_ibfk_1` FOREIGN KEY (`championship_id`) REFERENCES `CHAMPIONSHIP` (`id`),
                        CONSTRAINT `GRID_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `CAR` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `GRID_RACE`
--

DROP TABLE IF EXISTS `GRID_RACE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GRID_RACE` (
                             `race_id` int NOT NULL,
                             `grid_id` int NOT NULL,
                             `points` bigint DEFAULT NULL,
                             `first_sector` bigint DEFAULT NULL,
                             `second_sector` bigint DEFAULT NULL,
                             `third_sector` bigint DEFAULT NULL,
                             `final_time` bigint DEFAULT NULL,
                             `total_laps` int DEFAULT NULL,
                             `qualy_position` decimal(10,0) DEFAULT NULL,
                             `qualy_time` decimal(10,0) DEFAULT NULL,
                             `qualy_first_sector` decimal(10,0) DEFAULT NULL,
                             `qualy_second_sector` decimal(10,0) DEFAULT NULL,
                             `qualy_third_sector` decimal(10,0) DEFAULT NULL,
                             `sanction_time` decimal(10,0) DEFAULT NULL,
                             PRIMARY KEY (`race_id`,`grid_id`),
                             KEY `grid_id` (`grid_id`),
                             CONSTRAINT `GRID_RACE_ibfk_1` FOREIGN KEY (`race_id`) REFERENCES `RACE` (`id`),
                             CONSTRAINT `GRID_RACE_ibfk_2` FOREIGN KEY (`grid_id`) REFERENCES `GRID` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `GRID_USER`
--

DROP TABLE IF EXISTS `GRID_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GRID_USER` (
                             `user_id` int NOT NULL,
                             `grid_id` int NOT NULL,
                             `grid_manager` tinyint NOT NULL DEFAULT '0',
                             PRIMARY KEY (`user_id`,`grid_id`),
                             KEY `grid_id` (`grid_id`),
                             CONSTRAINT `GRID_USER_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`),
                             CONSTRAINT `GRID_USER_ibfk_2` FOREIGN KEY (`grid_id`) REFERENCES `GRID` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RACE`
--

DROP TABLE IF EXISTS `RACE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RACE` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `championship_id` int NOT NULL,
                        `track` varchar(45) NOT NULL,
                        `pre_race_waiting_time_seconds` int NOT NULL,
                        `session_over_time_seconds` int NOT NULL,
                        `ambient_temp` int NOT NULL,
                        `cloud_level` float NOT NULL,
                        `rain` float NOT NULL,
                        `weather_randomness` int NOT NULL,
                        `post_qualy_seconds` int NOT NULL,
                        `post_race_seconds` int NOT NULL,
                        `server_name` varchar(45) NOT NULL,
                        `start_date` datetime DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        KEY `RACE_CHAMPIONSHIP_FK_idx` (`championship_id`),
                        CONSTRAINT `RACE_CHAMPIONSHIP_FK` FOREIGN KEY (`championship_id`) REFERENCES `CHAMPIONSHIP` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=452 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RACE_RULES`
--

DROP TABLE IF EXISTS `RACE_RULES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RACE_RULES` (
                              `qualify_standing_type` int NOT NULL,
                              `id` int NOT NULL AUTO_INCREMENT,
                              `race_id` int NOT NULL,
                              `pit_window_length_sec` int NOT NULL,
                              `driver_stint_time_sec` int NOT NULL,
                              `mandatory_pitstop_count` int NOT NULL,
                              `max_total_driving_time` int NOT NULL,
                              `max_drivers_count` int NOT NULL,
                              `is_refuelling_allowed_in_race` tinyint NOT NULL,
                              `is_refuelling_time_fixed` tinyint NOT NULL,
                              `is_mandatory_pitstop_refuelling_required` tinyint NOT NULL,
                              `is_mandatory_pitstop_tyre_change_required` tinyint NOT NULL,
                              `is_mandatory_pitstop_swap_driver_required` tinyint NOT NULL,
                              `tyre_set_count` tinyint NOT NULL,
                              PRIMARY KEY (`id`),
                              KEY `RACE_RULES_FK_idx` (`race_id`),
                              CONSTRAINT `RACE_RULES_FK` FOREIGN KEY (`race_id`) REFERENCES `RACE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ROLE`
--

DROP TABLE IF EXISTS `ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ROLE` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `role` varchar(45) NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `roleROLE_UNIQUE` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SANCTION`
--

DROP TABLE IF EXISTS `SANCTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SANCTION` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `lap` int DEFAULT NULL,
                            `penalty` varchar(45) NOT NULL,
                            `reason` varchar(256) NOT NULL,
                            `race_id` int DEFAULT NULL,
                            `grid_id` int DEFAULT NULL,
                            `in_game` tinyint NOT NULL DEFAULT '0',
                            `license_points` decimal(10,0) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `race_id` (`race_id`),
                            KEY `grid_id` (`grid_id`),
                            CONSTRAINT `SANCTION_ibfk_1` FOREIGN KEY (`race_id`) REFERENCES `RACE` (`id`),
                            CONSTRAINT `SANCTION_ibfk_2` FOREIGN KEY (`grid_id`) REFERENCES `GRID` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SESSION`
--

DROP TABLE IF EXISTS `SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SESSION` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `hour_of_day` int NOT NULL,
                           `day_of_weekend` int NOT NULL,
                           `time_multiplier` int NOT NULL,
                           `session_type` varchar(1) NOT NULL,
                           `session_duration_minutes` int NOT NULL,
                           `race_id` int NOT NULL,
                           PRIMARY KEY (`id`),
                           KEY `SESSION_RACE_FG_idx` (`race_id`),
                           CONSTRAINT `SESSION_RACE_FG` FOREIGN KEY (`race_id`) REFERENCES `RACE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `steam_id` bigint NOT NULL,
                        `first_name` varchar(45) NOT NULL,
                        `last_name` varchar(45) DEFAULT NULL,
                        `short_name` varchar(45) DEFAULT NULL,
                        `nationality` varchar(45) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `idUSER_UNIQUE` (`id`),
                        UNIQUE KEY `steam_id_UNIQUE` (`steam_id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `USER_AUTH`
--

DROP TABLE IF EXISTS `USER_AUTH`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_AUTH` (
                             `steam_id` bigint NOT NULL,
                             `token` varchar(150) NOT NULL,
                             `last_login` mediumtext NOT NULL,
                             PRIMARY KEY (`steam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `USER_CHAMPIONSHIP`
--

DROP TABLE IF EXISTS `USER_CHAMPIONSHIP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_CHAMPIONSHIP` (
                                     `user_id` int NOT NULL AUTO_INCREMENT,
                                     `championship_id` int NOT NULL,
                                     `car` varchar(45) DEFAULT NULL,
                                     PRIMARY KEY (`user_id`,`championship_id`),
                                     KEY `CHAMPIONSHIP_KF_idx` (`championship_id`),
                                     CONSTRAINT `CHAMPIONSHIP_KF` FOREIGN KEY (`championship_id`) REFERENCES `CHAMPIONSHIP` (`id`),
                                     CONSTRAINT `USER_FK` FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `USER_ROLE`
--

DROP TABLE IF EXISTS `USER_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_ROLE` (
                             `user_id` int NOT NULL,
                             `role_id` int NOT NULL,
                             PRIMARY KEY (`user_id`,`role_id`),
                             KEY `role_id` (`role_id`),
                             CONSTRAINT `USER_ROLE_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`),
                             CONSTRAINT `USER_ROLE_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `ROLE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-08 17:17:31
