-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: greengrocerapp
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `orderinfo`
--

DROP TABLE IF EXISTS `orderinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderinfo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ordertime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `deliverytime` timestamp NULL DEFAULT NULL,
  `products` text,
  `user` varchar(50) DEFAULT NULL,
  `userID` int DEFAULT NULL,
  `userAddress` text,
  `carrier` varchar(50) DEFAULT NULL,
  `isdelivered` tinyint(1) DEFAULT NULL,
  `totalcost` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userID` (`userID`),
  CONSTRAINT `orderinfo_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `userinfo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderinfo`
--

LOCK TABLES `orderinfo` WRITE;
/*!40000 ALTER TABLE `orderinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productinfo`
--

DROP TABLE IF EXISTS `productinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productinfo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` enum('fruit','vegetable') DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `imagelocation` varchar(255) DEFAULT NULL,
  `threshold` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productinfo`
--

LOCK TABLES `productinfo` WRITE;
/*!40000 ALTER TABLE `productinfo` DISABLE KEYS */;
INSERT INTO `productinfo` VALUES (71,'fruit','Apple',2.00,12,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/fruits/apple.jpg',2),(73,'fruit','Banana',3.00,13,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/fruits/banana.jpg',2),(74,'fruit','Cherry',1.00,34,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/fruits/cherry.jpg',12),(75,'fruit','Date',5.00,12,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/fruits/date.jpg',2),(76,'fruit','Elderberry',5.20,12,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/fruits/elderberry.jpg',2),(77,'fruit','Fig',4.20,12,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/fruits/fig.jpg',2),(79,'fruit','Grape',3.00,23,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/fruits/grape.jpg',12),(80,'fruit','Honeydew',7.00,23,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/fruits/honeydew.jpg',12),(81,'fruit','Kiwi',1.20,23,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/fruits/kiwi.jpg',12),(82,'fruit','Lemon',2.00,12,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/fruits/lemon.jpg',3),(83,'fruit','Mango',4.00,32,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/fruits/mango.jpg',12),(84,'fruit','Nectarine',4.00,32,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/fruits/nectarine.jpg',12),(85,'vegetable','Asparagus',4.00,32,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/vegetables/asparagus.jpg',12),(86,'vegetable','Broccoli',3.00,23,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/vegetables/broccoli.jpg',12),(87,'vegetable','Carrot',2.00,23,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/vegetables/carrot.jpg',10),(88,'vegetable','Daikon',3.40,23,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/vegetables/daikon.jpg',10),(89,'vegetable','Eggplant',3.40,23,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/vegetables/eggplant.jpg',10),(90,'vegetable','Fennel',3.40,23,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/vegetables/fennel.jpg',10),(91,'vegetable','Garlic',2.00,13,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/vegetables/garlic.jpg',2),(92,'vegetable','Horseradish',2.00,13,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/vegetables/horseradish.jpg',2),(93,'vegetable','Iceberg',2.00,13,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/vegetables/iceberg.jpg',2),(94,'vegetable','Jalapeno',3.00,13,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/vegetables/jalapeno.jpg',2),(95,'vegetable','Kale',4.00,13,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/vegetables/kale.jpg',2),(96,'vegetable','Leek',4.00,13,'file:/C:/Users/emirc/OneDrive/Masaüstü/images/vegetables/leek.jpg',2);
/*!40000 ALTER TABLE `productinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userinfo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) DEFAULT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `address` text,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `role` enum('customer','carrier','owner') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinfo`
--

LOCK TABLES `userinfo` WRITE;
/*!40000 ALTER TABLE `userinfo` DISABLE KEYS */;
INSERT INTO `userinfo` VALUES (55,NULL,NULL,NULL,NULL,'admin','admin123','owner');
/*!40000 ALTER TABLE `userinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-12 19:16:42
