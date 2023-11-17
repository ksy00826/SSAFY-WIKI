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
-- Table structure for table `templates`
--

DROP TABLE IF EXISTS `templates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `templates` (
  `template_id` bigint NOT NULL AUTO_INCREMENT,
  `template_content` text NOT NULL,
  `template_created_at` timestamp NULL DEFAULT NULL,
  `template_modified_at` timestamp NULL DEFAULT NULL,
  `template_is_secret` bit(1) NOT NULL,
  `template_title` varchar(255) NOT NULL,
  `template_user_id` bigint NOT NULL,
  PRIMARY KEY (`template_id`),
  KEY `FKljr5200idof6pdy120m9ls0is` (`template_user_id`),
  CONSTRAINT `FKljr5200idof6pdy120m9ls0is` FOREIGN KEY (`template_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `templates`
--

LOCK TABLES `templates` WRITE;
/*!40000 ALTER TABLE `templates` DISABLE KEYS */;
INSERT INTO `templates` VALUES (1,'### Hi there ?\n\nI\'m Danny, a software engineer ? currently working at [Takeaway.com](https://takeaway.com) ??\n\nI have a passion for clean code, Java, teaching, PHP, Lifeguarding and Javascript\n\nMy current side project is [Markdown Profile](https://markdownprofile.com)\n\n[LinkedIn ?](https://linkedin.com/in/dannyverpoort)\n\n[Twitter ?](https://twitter.com/dannyverp)\n\n[Website ?](https://dannyverpoort.dev/)\n\n[Email ?](mailto:hallo@dannyverpoort.nl)','2023-11-10 16:25:23','2023-11-10 16:49:33',_binary '\0','심플인물',30),(2,'### Hi there ?\nI\'m Danny, a software engineer ? currently working at [Takeaway.com](https://takeaway.com) ??\n\nI have a passion for clean code, Java, teaching, PHP, Lifeguarding and Javascript\n\n# Here are some good things to introduce yourself\n###  change several \"kss4037\" to your github Id\n# 문서를 꾸미기 위한 마크다운 뱃지들\n![C++](https://img.shields.io/badge/c++-%2300599C.svg?style=for-the-badge&logo=c%2B%2B&logoColor=white)\n![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)\n![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)\n![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)\n![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)\n\nhttps://ileriayo.github.io/markdown-badges/#markdown-badges\n\n# 깃허브에서 사용한 언어 그래프\n[![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=kss4037&layout=compact)](https://github.com/anuraghazra/github-readme-stats)\n\n# 깃허브 스탯\n[![kss4037\'s github stats](https://github-readme-stats.vercel.app/api?username=kss4037&show_icons=true&theme=default)](https://github.com/kss4037/)\n\n### thema can be one of [ dark radical merko gruvbox tokyonight ondark cobalt synthwave highcontrast dracula ]\n\n# 하이퍼링크\n[Email ?](mailto:hallo@dannyverpoort.nl)\n[LinkedIn ?](https://linkedin.com/in/dannyverpoort)\n[Twitter ?](https://twitter.com/dannyverp)\n[Website ?](https://dannyverpoort.dev/)','2023-11-10 16:49:18','2023-11-10 16:49:32',_binary '\0','Complex 인물 템플릿 ',30),(3,'# 프로젝트\n\n# 기획배경\n\n#  개요\n\n# 주요기능\n\n# 기술스택\n','2023-11-15 14:56:14','2023-11-15 14:56:14',_binary '\0','프로젝트',39),(4,'# 개요\n# 특징\n# 활용\n# 종류\n# 사용한 프로젝트','2023-11-16 12:32:25','2023-11-16 12:32:25',_binary '\0','기술템플릿',56);
/*!40000 ALTER TABLE `templates` ENABLE KEYS */;
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

-- Dump completed on 2023-11-17 11:44:40
