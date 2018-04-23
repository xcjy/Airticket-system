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
  `identifier` char(8) NOT NULL,
  `adpss` char(8) NOT NULL,
  `adname` varchar(5) NOT NULL,
  `phonenumber` varchar(12) NOT NULL,
  PRIMARY KEY (`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('ad001','123456','小管',''),('ad002','123456','小理','15666688686'),('admin','admin','小陈','12345678910');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flight`
--

DROP TABLE IF EXISTS `flight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flight` (
  `f_id` varchar(45) NOT NULL,
  `f_com` varchar(45) NOT NULL,
  `f_model` varchar(45) NOT NULL,
  `f_stime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `f_etime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `f_start` varchar(45) NOT NULL,
  `f_dist` varchar(45) NOT NULL,
  `f_price` float NOT NULL,
  `f_left` int(11) NOT NULL,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight`
--

LOCK TABLES `flight` WRITE;
/*!40000 ALTER TABLE `flight` DISABLE KEYS */;
INSERT INTO `flight` VALUES ('AB2222','东方航空','波音747','2018-05-10 09:00:00','2018-05-10 21:00:00','南京','北京',9999,30),('ABC125','北方航空','波音747','2018-08-15 07:00:00','2018-08-16 10:00:00','成都','深圳',1005,65),('ABC523','南方航空','空客320','2018-05-01 07:00:00','2018-05-02 07:00:00','北京','深圳',777,20),('CA333','四川航空','空客747','2018-09-10 09:55:00','2018-09-10 14:10:00','上海','成都',1420,60),('CA8847','我的航','空客445','2018-09-10 09:55:00','2018-09-10 14:10:00','上海','成都',1420,100);
/*!40000 ALTER TABLE `flight` ENABLE KEYS */;
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
  `name` varchar(45) NOT NULL,
  `sex` char(2) NOT NULL,
  `old` char(2) NOT NULL,
  `sfz` char(19) NOT NULL,
  PRIMARY KEY (`sfz`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('13876523988','ddddd123456','小马','女','17','092343167892394881'),('1','321','1','1','1','1'),('12','1','1','男','1','112'),('2345567','321','ere','男','12','1234567890'),('18380107881','123','wqw','男','43','12423546435756765'),('7778','7778','44','男','44','22'),('15880245966','1234567890','小王','男','22','440583199603291211'),('18380107887','zaijian','小陈','女','20','440583199708194546'),('13085892464','aaaa123456','小李','女','18','650573169708194509'),('666','666','66','男','66','66'),('13509239662','bbbb123456','小周','男','23','873543169728194283'),('13512093008','ffff123456','小黄','女','17','887213466321831877'),('110200330','123456','小可','女','26','887384777289318233'),('13693894676','cccc123456','小杨','女','28','985343167898194000');
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

-- Dump completed on 2018-04-23 22:24:25
