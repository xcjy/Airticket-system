-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: airlineticket
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `identifier` varchar(12) NOT NULL,
  `adpss` varchar(12) NOT NULL,
  `adname` varchar(10) NOT NULL,
  PRIMARY KEY (`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('1','1','测试'),('ad001','123456','小管'),('ad002','123456','小理'),('admin','admin','小陈');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flight`
--

DROP TABLE IF EXISTS `flight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flight` (
  `f_id` varchar(10) NOT NULL,
  `f_com` varchar(10) NOT NULL,
  `f_model` varchar(10) NOT NULL,
  `f_stime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `f_etime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `f_start` varchar(10) NOT NULL,
  `f_dist` varchar(10) NOT NULL,
  `f_price` float NOT NULL,
  `f_left` int(10) NOT NULL,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight`
--

LOCK TABLES `flight` WRITE;
/*!40000 ALTER TABLE `flight` DISABLE KEYS */;
INSERT INTO `flight` VALUES ('AB2222','东方航空','波音747','2018-06-20 09:00:00','2018-06-20 14:00:00','南京','北京',9999,52),('ABC523','南方航空','空客320','2018-06-19 07:00:00','2018-06-19 10:00:00','北京','深圳',777,18),('BJ333','春秋航空','空客320','2018-06-28 09:55:00','2018-06-28 14:10:00','深圳','成都',768,100),('CA333','四川航空','空客747','2018-06-30 09:55:00','2018-06-30 14:10:00','上海','成都',1420,57);
/*!40000 ALTER TABLE `flight` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `orderid` varchar(10) NOT NULL,
  `p_name` varchar(10) NOT NULL,
  `p_id` varchar(18) NOT NULL,
  `f_id` varchar(10) NOT NULL,
  `f_com` varchar(10) NOT NULL,
  `f_model` varchar(10) NOT NULL,
  `f_stime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `f_etime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `f_start` varchar(10) NOT NULL,
  `f_end` varchar(10) NOT NULL,
  `f_price` float NOT NULL,
  `user` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES ('2262838122','Rose','440306199811070426','ABC523','南方航空','空客320','2019-06-19 07:00:00','2018-06-19 10:00:00','北京','深圳',777,'1'),('4717600051','chen3','44030619951109053X','CA333','四川航空','空客747','2018-09-10 09:55:00','2018-09-10 14:10:00','上海','成都',1420,'1');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `passenger` (
  `id` varchar(18) NOT NULL,
  `name` varchar(12) NOT NULL,
  `user` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passenger`
--

LOCK TABLES `passenger` WRITE;
/*!40000 ALTER TABLE `passenger` DISABLE KEYS */;
INSERT INTO `passenger` VALUES ('44030619951109053X','chen','1'),('123','123','2'),('123','123','44'),('440306199811070426','Rose','1');
/*!40000 ALTER TABLE `passenger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user` char(12) NOT NULL,
  `pass` char(12) NOT NULL,
  `name` varchar(10) NOT NULL,
  `sex` char(2) NOT NULL,
  `sfz` char(19) NOT NULL,
  `phone` varchar(13) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1','1','张伟','男','440306196781010538','13208188247','123456@qq.com'),('2','3','王祥','男','110101197900078072','',''),('3','3','李丹','男','110101199503079710','',''),('44','44','武当','男','	310101199007079371','','');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-16 14:06:00
