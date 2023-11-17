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
-- Table structure for table `redirect_keyword`
--

DROP TABLE IF EXISTS `redirect_keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `redirect_keyword` (
  `redir_id` bigint NOT NULL AUTO_INCREMENT,
  `redir_created_at` timestamp NULL DEFAULT NULL,
  `redir_keyword` varchar(255) DEFAULT NULL,
  `redir_modified_at` timestamp NULL DEFAULT NULL,
  `redir_docs_id` bigint DEFAULT NULL,
  PRIMARY KEY (`redir_id`),
  UNIQUE KEY `UK_e638rl8a6is3xttm8oeulirrj` (`redir_docs_id`),
  CONSTRAINT `FKlif1skvf3aju74bwvqp4eiymf` FOREIGN KEY (`redir_docs_id`) REFERENCES `documents` (`docs_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `redirect_keyword`
--

LOCK TABLES `redirect_keyword` WRITE;
/*!40000 ALTER TABLE `redirect_keyword` DISABLE KEYS */;
INSERT INTO `redirect_keyword` VALUES (1,'2023-11-09 23:50:57','싸피위키:대문','2023-11-09 23:50:57',3),(2,'2023-11-10 09:45:55','싸피위키:대문','2023-11-10 09:45:55',4),(3,'2023-11-13 10:46:58','정경훈(9기 코치)','2023-11-13 10:46:58',20),(4,'2023-11-13 10:54:23','정인모(9기 코치)','2023-11-13 10:54:23',22),(5,'2023-11-13 11:58:49','부울경캠','2023-11-13 11:58:49',25),(6,'2023-11-13 14:48:43','삼성 청년 SW 아카데미','2023-11-13 14:48:43',26),(7,'2023-11-14 12:09:31','김광표 (0953292)','2023-11-14 12:09:31',48),(8,'2023-11-14 12:15:43','김승연 (0956879)','2023-11-14 12:15:43',50),(9,'2023-11-15 15:10:55','김광표 (0953292)','2023-11-15 15:10:55',75);
/*!40000 ALTER TABLE `redirect_keyword` ENABLE KEYS */;
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

-- Dump completed on 2023-11-17 11:44:35
