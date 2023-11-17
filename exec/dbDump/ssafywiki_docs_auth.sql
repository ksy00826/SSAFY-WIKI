-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: database-ssafywiki.cnbro2eo1odc.ap-northeast-2.rds.amazonaws.com    Database: ssafywiki
-- ------------------------------------------------------
-- Server version	8.0.33

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `docs_auth`
--

DROP TABLE IF EXISTS `docs_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docs_auth` (
  `docs_auth_id` bigint NOT NULL,
  `docs_auth_created_at` timestamp NULL DEFAULT NULL,
  `docs_auth_modified_at` timestamp NULL DEFAULT NULL,
  `docs_auth_docs_id` bigint DEFAULT NULL,
  PRIMARY KEY (`docs_auth_id`),
  KEY `FK6hsq7y9qh5cn5gu1pfoe9oxk` (`docs_auth_docs_id`),
  CONSTRAINT `FK6hsq7y9qh5cn5gu1pfoe9oxk` FOREIGN KEY (`docs_auth_docs_id`) REFERENCES `documents` (`docs_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docs_auth`
--

LOCK TABLES `docs_auth` WRITE;
/*!40000 ALTER TABLE `docs_auth` DISABLE KEYS */;
INSERT INTO `docs_auth` VALUES (1000,'2023-11-14 16:39:34','2023-11-14 16:39:34',2),(1001,'2023-11-15 16:16:19','2023-11-15 16:16:19',85),(1002,'2023-11-16 10:17:26','2023-11-16 10:17:26',86),(1003,'2023-11-16 10:58:09','2023-11-16 10:58:09',88),(1004,'2023-11-16 11:12:39','2023-11-16 11:12:39',89),(1005,'2023-11-16 14:05:59','2023-11-16 14:05:59',84),(1006,'2023-11-16 14:07:04','2023-11-16 14:07:04',95),(1007,'2023-11-16 14:35:32','2023-11-16 14:35:32',99),(1008,'2023-11-16 15:17:21','2023-11-16 15:17:21',100),(1009,'2023-11-17 09:07:52','2023-11-17 09:07:52',102),(1010,'2023-11-17 11:31:36','2023-11-17 11:31:36',103);
/*!40000 ALTER TABLE `docs_auth` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-17 11:44:37
