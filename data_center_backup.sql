-- MySQL dump 10.13  Distrib 5.7.24, for osx11.1 (x86_64)
--
-- Host: localhost    Database: data_center
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account_inf`
--

DROP TABLE IF EXISTS `account_inf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_inf` (
  `_id` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `num` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `create_date` varchar(50) DEFAULT NULL,
  `activated` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_inf`
--

LOCK TABLES `account_inf` WRITE;
/*!40000 ALTER TABLE `account_inf` DISABLE KEYS */;
INSERT INTO `account_inf` VALUES ('1','12345','James','123@qq.com','10086','21311111','1','2021-01-01','true');
/*!40000 ALTER TABLE `account_inf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `adminID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `readable` varchar(255) DEFAULT NULL,
  `writable` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`adminID`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `branch` (
  `branch_name` varchar(50) DEFAULT NULL,
  `branch_create_date` varchar(50) DEFAULT NULL,
  `college_id` varchar(50) DEFAULT NULL,
  `branch_id` varchar(50) NOT NULL,
  PRIMARY KEY (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES ('中山大学支部','2020-01-01','1','1'),('党支部','2020-01-01','1','2'),('软工','2024-01-01','2','3');
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_center_admin`
--

DROP TABLE IF EXISTS `data_center_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_center_admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_center_admin`
--

LOCK TABLES `data_center_admin` WRITE;
/*!40000 ALTER TABLE `data_center_admin` DISABLE KEYS */;
INSERT INTO `data_center_admin` VALUES (1,'admin0','abc'),(2,'admin1','abc');
/*!40000 ALTER TABLE `data_center_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `party_member`
--

DROP TABLE IF EXISTS `party_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `party_member` (
  `person_id` varchar(50) NOT NULL,
  `year` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `period` varchar(50) DEFAULT NULL,
  `outlook` varchar(50) DEFAULT NULL,
  `branch_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `party_member`
--

LOCK TABLES `party_member` WRITE;
/*!40000 ALTER TABLE `party_member` DISABLE KEYS */;
INSERT INTO `party_member` VALUES ('1','2021','本科生','积极分子','团员','1');
/*!40000 ALTER TABLE `party_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_study_ability`
--

DROP TABLE IF EXISTS `student_study_ability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_study_ability` (
  `GR_GPA` float DEFAULT NULL,
  `ME_GPA` float DEFAULT NULL,
  `student_id` int NOT NULL,
  `MR_GPA` float DEFAULT NULL,
  `Party_Political_Award` int DEFAULT NULL,
  `Major_competition_Award` int DEFAULT NULL,
  `Art_Award` int DEFAULT NULL,
  `Sports_Award` int DEFAULT NULL,
  `Startup_Award` int DEFAULT NULL,
  `scholar_Award` int DEFAULT NULL,
  `paper_publication` int DEFAULT NULL,
  `volunteer` int DEFAULT NULL,
  `patent` int DEFAULT NULL,
  `software_patent` int DEFAULT NULL,
  `book_publication` int DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_study_ability`
--

LOCK TABLES `student_study_ability` WRITE;
/*!40000 ALTER TABLE `student_study_ability` DISABLE KEYS */;
INSERT INTO `student_study_ability` VALUES (3.5,3.6,1,3.7,5,3,2,1,1,2,4,4,1,2,3),(3.4,3.5,2,3.6,4,1,1,2,2,1,3,3,2,1,4),(3.3,3.4,3,3.5,3,2,2,3,3,4,1,2,1,2,1),(3.2,3.3,4,3.4,1,3,3,1,3,2,2,1,3,4,2),(3.1,3.2,5,3.3,2,1,1,2,1,3,3,2,4,3,1),(3,3.1,6,3.2,3,4,2,3,2,4,1,3,2,1,4),(2.9,3,7,3.1,1,2,3,1,3,1,3,1,4,2,3),(2.8,2.9,8,3,4,3,1,2,1,3,2,4,3,1,2),(2.7,2.8,9,2.9,3,1,2,3,4,1,4,2,1,2,3),(2.6,2.7,10,2.8,2,2,3,1,2,4,1,3,4,3,1);
/*!40000 ALTER TABLE `student_study_ability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_permissions`
--

DROP TABLE IF EXISTS `table_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_permissions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `permission` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_permissions`
--

LOCK TABLES `table_permissions` WRITE;
/*!40000 ALTER TABLE `table_permissions` DISABLE KEYS */;
INSERT INTO `table_permissions` VALUES (8,'party_member',1),(9,'branch',1),(10,'account_inf',1),(12,'student_study_ability',1);
/*!40000 ALTER TABLE `table_permissions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-28 13:32:40
