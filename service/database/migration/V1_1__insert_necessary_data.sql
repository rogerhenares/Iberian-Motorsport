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
-- Dumping data for table `CAR`
--

LOCK TABLES `CAR` WRITE;
/*!40000 ALTER TABLE `CAR` DISABLE KEYS */;
INSERT INTO `CAR` VALUES (2,'AMR','V8 Vantage','GT3'),(3,'AMR','V8 Vantage','GT4'),(4,'Audi','R8 LMS','GT3'),(5,'Audi','R8 LMS','GT4'),(6,'Audi','R8 LMS evo','GT3'),(7,'Audi','R8 LMS evo II','GT3'),(8,'Bentley','Continental 2018','GT3'),(9,'Bentley','Continental 2015','GT3'),(10,'BMW','M4','GT3'),(11,'BMW','M4','GT4'),(12,'BMW','M6','GT3'),(13,'BMW','M2 CS','TCX'),(14,'Jaguar','M2 CS','TCX'),(15,'Ferrari','296','GT3'),(16,'Ferrari','488','GT3'),(17,'Ferrari','488 evo','GT3'),(18,'Ferrari','488 Challenge Evo','CHL'),(19,'Honda','NSX','GT3'),(20,'Honda','NSX Evo','GT3'),(21,'Lamborghini','Huracan','GT3'),(22,'Lamborghini','Huracan Evo','GT3'),(23,'Lamborghini','Huracan Evo2','GT3'),(24,'Lamborghini','Huracan ST','ST'),(25,'Lamborghini','Huracan ST EVO2','ST'),(26,'Lexus','RC F','GT3'),(27,'McLaren','650S','GT3'),(28,'McLaren','720S','GT3'),(29,'McLaren','720S Evo','GT3'),(30,'Mercedes','Mercedes-AMG','GT3'),(31,'Mercedes','Mercedes-AMG 2020','GT3'),(32,'Mercedes','Mercedes-AMG','GT4'),(33,'Nissan','GT-R Nismo 2015','GT3'),(34,'Nissan','GT-R Nismo 2018','GT3'),(35,'Porsche','991 R','GT3'),(36,'Porsche','992 R','GT3'),(37,'Porsche','991 II R','GT3'),(38,'Porsche','718 Cayman Clubsport','GT4'),(39,'Porsche','991 II Cup','CUP'),(40,'Porsche','992 Cup','CUP'),(41,'Reiter','Engineering R-EX','GT3'),(42,'Alpine','A110','GT4'),(43,'Chevrolet','Camaro','GT4'),(44,'Ginetta','G55','GT4'),(45,'KTM','X-Bow','GT4'),(46,'Maserati','GranTurismo MC','GT4'),(47,'AMR','V12 Vantage','GT3');
/*!40000 ALTER TABLE `CAR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ROLE`
--

LOCK TABLES `ROLE` WRITE;
/*!40000 ALTER TABLE `ROLE` DISABLE KEYS */;
INSERT INTO `ROLE` VALUES (1,'ADMIN'),(2,'BASIC_USER'),(3,'STEWARD');
/*!40000 ALTER TABLE `ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES (82,76561198056844833,'Roger','Henares','BLER','ES'),(83,76561197994761085,'Abel','Negruzco','Cabezon','VE'),(84,76561199560241237,'IML','Dev','Dev','ES');
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `USER_AUTH`
--

LOCK TABLES `USER_AUTH` WRITE;
/*!40000 ALTER TABLE `USER_AUTH` DISABLE KEYS */;
INSERT INTO `USER_AUTH` VALUES (76561198056844833,'NzY1NjExOTgwNTY4NDQ4MzMyMDIzLTEwLTA1VDE0OjEzOjM4WnR1TFQ3MlJFZVloWU5lU21iQXljS3phc1FkQT0=','1696613142734');
/*!40000 ALTER TABLE `USER_AUTH` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `USER_ROLE`
--

LOCK TABLES `USER_ROLE` WRITE;
/*!40000 ALTER TABLE `USER_ROLE` DISABLE KEYS */;
INSERT INTO `USER_ROLE` VALUES (82,1),(83,1),(84,1);
/*!40000 ALTER TABLE `USER_ROLE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-08 17:13:02
