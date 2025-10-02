-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: bankinfo
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `ac_no` int NOT NULL AUTO_INCREMENT,
  `c_name` varchar(30) DEFAULT NULL,
  `balance` varchar(45) DEFAULT NULL,
  `pass_code` int DEFAULT NULL,
  PRIMARY KEY (`ac_no`),
  UNIQUE KEY `c_name` (`c_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Nazmul Islam','7197',20205),(2,'Shipon','2002',1111),(3,'Shuvon','1123',2222),(4,'Naeem','1000',23235),(5,'Naeem Ahmed','700',3344),(6,'Shila','200',11223),(7,'Habib','1056',77777),(8,'Demo Name','1000',12345),(9,'YYY','1000',4444);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_history`
--

DROP TABLE IF EXISTS `transaction_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sender_ac` int DEFAULT NULL,
  `receiver_ac` int DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_history`
--

LOCK TABLES `transaction_history` WRITE;
/*!40000 ALTER TABLE `transaction_history` DISABLE KEYS */;
INSERT INTO `transaction_history` VALUES (1,1,0,56,'Deposit','2025-09-02 15:06:54'),(2,1,2,345,'Transfer','2025-09-02 15:07:22'),(3,2,0,5657,'Deposit','2025-09-02 15:07:58'),(4,2,1,10000,'Transfer','2025-09-02 17:38:39'),(5,1,0,2,'Withdraw','2025-09-03 15:50:28'),(6,1,0,12000,'Withdraw','2025-09-03 15:51:07'),(7,1,9,34,'Transfer','2025-09-03 16:45:54'),(8,2,0,500,'Deposit','2025-09-03 16:52:46'),(9,1,0,200,'Withdraw','2025-09-03 17:49:00'),(10,1,0,5000,'Deposit','2025-09-03 17:49:29'),(11,5,0,500,'Deposit','2025-09-03 18:33:48'),(12,5,1,800,'Transfer','2025-09-03 18:34:46'),(13,1,10,345,'Transfer','2025-09-03 18:50:08'),(14,6,0,500,'Deposit','2025-09-04 13:28:16'),(15,6,0,300,'Withdraw','2025-09-04 13:28:43'),(16,6,1,1000,'Transfer','2025-09-04 13:29:33'),(17,7,0,56,'Deposit','2025-09-04 14:23:58'),(18,8,0,1000,'Deposit','2025-09-04 15:18:15'),(19,8,0,500,'Withdraw','2025-09-04 15:18:39'),(20,8,2,500,'Transfer','2025-09-04 15:19:13'),(21,1,0,45,'Deposit','2025-09-06 06:02:22');
/*!40000 ALTER TABLE `transaction_history` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-06 20:30:16
