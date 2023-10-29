DELETE FROM `GRID_RACE`;
DELETE FROM `GRID_USER`;
DELETE FROM `GRID`;
DELETE FROM `BOP`;
DELETE FROM `CAR`;
LOCK TABLES `CAR` WRITE;
/*!40000 ALTER TABLE `CAR` DISABLE KEYS */;
INSERT INTO `CAR` VALUES
  (2,'AMR','V8 Vantage','GT3', 20),
  (3,'AMR','V8 Vantage (GT4)','GT4', 51),
  (4,'Audi','R8 LMS','GT3', 3),
  (5,'Audi','R8 LMS (GT4)','GT4', 52),
  (6,'Audi','R8 LMS evo','GT3', 19),
  (7,'Audi','R8 LMS evo II','GT3', 31),
  (8,'Bentley','Continental 2018','GT3', 8),
  (9,'Bentley','Continental 2016','GT3', 11),
  (10,'BMW','M4','GT3', 30),
  (11,'BMW','M4 (GT4)','GT4', 53),
  (12,'BMW','M6','GT3', 7),
  (13,'BMW','M2 CS','TCX', 27),
  (14,'Jaguar','M2 CS','GT3', 14),
  (15,'Ferrari','296','GT3', 32),
  (16,'Ferrari','488','GT3', 2),
  (17,'Ferrari','488 evo','GT3', 24),
  (18,'Ferrari','488 Challenge Evo','CHL', 26),
  (19,'Honda','NSX','GT3', 17),
  (20,'Honda','NSX Evo','GT3', 21),
  (21,'Lamborghini','Huracan','GT3', 4),
  (22,'Lamborghini','Huracan Evo','GT3', 16),
  (23,'Lamborghini','Huracan Evo2','GT3', 33),
  (24,'Lamborghini','Huracan ST','ST', 18),
  (25,'Lamborghini','Huracan ST EVO2','ST', 29),
  (26,'Lexus','RC F','GT3', 15),
  (27,'McLaren','650S','GT3', 5),
  (28,'McLaren','720S','GT3', 22),
  (29,'McLaren','720S Evo','GT3', 35),
  (30,'Mercedes','Mercedes-AMG','GT3', 1),
  (31,'Mercedes','Mercedes-AMG 2020','GT3', 25),
  (32,'Mercedes','Mercedes-AMG (GT4)','GT4', 60),
  (33,'Nissan','GT-R Nismo 2017','GT3', 10),
  (34,'Nissan','GT-R Nismo 2018','GT3', 6),
  (35,'Porsche','991 R','GT3', 0),
  (36,'Porsche','992 R','GT3', 34),
  (37,'Porsche','911 II R','GT3', 23),
  (38,'Porsche','718 Cayman Clubsport','GT4', 61),
  (39,'Porsche','991 II Cup','CUP', 9),
  (40,'Porsche','992 Cup','CUP', 28),
  (41,'Reiter','Engineering R-EX','GT3', 13),
  (42,'Alpine','A110','GT4', 50),
  (43,'Chevrolet','Camaro','GT4', 55),
  (44,'Ginetta','G55','GT4', 56),
  (45,'KTM','X-Bow','GT4', 57),
  (46,'Maserati','GranTurismo MC','GT4', 58),
  (47,'AMR','V12 Vantage','GT3', 12),
  (48,'McLaren','570S','GT4', 59);
/*!40000 ALTER TABLE `CAR` ENABLE KEYS */;
UNLOCK TABLES;
