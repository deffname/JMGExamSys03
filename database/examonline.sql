-- --------------------------------------------------------
-- 主机:                           
-- 服务器版本:                        8.0.34 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 examonline 的数据库结构
CREATE DATABASE IF NOT EXISTS `examonline` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `examonline`;

-- 导出  表 examonline.admin 结构
CREATE TABLE IF NOT EXISTS `admin` (
  `aid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 数据导出被取消选择。

-- 导出  表 examonline.comparsiontable 结构
CREATE TABLE IF NOT EXISTS `comparsiontable` (
  `uid` bigint NOT NULL DEFAULT '0',
  `sid` bigint DEFAULT NULL,
  `tid` bigint DEFAULT NULL,
  `aid` bigint DEFAULT NULL,
  PRIMARY KEY (`uid`),
  KEY `FK_sid_stuc` (`sid`),
  KEY `FK_tid_teac` (`tid`),
  KEY `FK_aid_adminc` (`aid`),
  CONSTRAINT `FK_aid_adminc` FOREIGN KEY (`aid`) REFERENCES `admin` (`aid`),
  CONSTRAINT `FK_comparsiontable_user` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`),
  CONSTRAINT `FK_sid_stuc` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`),
  CONSTRAINT `FK_tid_teac` FOREIGN KEY (`tid`) REFERENCES `teacher` (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 数据导出被取消选择。

-- 导出  表 examonline.compexamstu 结构
CREATE TABLE IF NOT EXISTS `compexamstu` (
  `sid` bigint NOT NULL,
  `eid` bigint NOT NULL,
  `anspaper` varchar(255) DEFAULT NULL,
  `sekey` varchar(50) NOT NULL,
  PRIMARY KEY (`sekey`),
  KEY `FK_sid_stucc` (`sid`),
  KEY `FK_eid_examc` (`eid`),
  CONSTRAINT `FK_eid_examc` FOREIGN KEY (`eid`) REFERENCES `exam` (`eid`) ON DELETE CASCADE,
  CONSTRAINT `FK_sid_stucc` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 数据导出被取消选择。

-- 导出  表 examonline.exam 结构
CREATE TABLE IF NOT EXISTS `exam` (
  `eid` bigint NOT NULL AUTO_INCREMENT,
  `tid` bigint DEFAULT NULL,
  `examname` varchar(50) DEFAULT NULL,
  `starttime` datetime DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  `exampaper` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `state` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'notstart',
  `pretime` bigint DEFAULT '15',
  PRIMARY KEY (`eid`),
  KEY `FK_tid_teae` (`tid`),
  CONSTRAINT `FK_tid_teae` FOREIGN KEY (`tid`) REFERENCES `teacher` (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 数据导出被取消选择。

-- 导出  表 examonline.student 结构
CREATE TABLE IF NOT EXISTS `student` (
  `sid` bigint NOT NULL AUTO_INCREMENT,
  `sname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 数据导出被取消选择。

-- 导出  表 examonline.teacher 结构
CREATE TABLE IF NOT EXISTS `teacher` (
  `tid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 数据导出被取消选择。

-- 导出  表 examonline.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `uid` bigint NOT NULL AUTO_INCREMENT,
  `fullname` varchar(50) NOT NULL DEFAULT '0',
  `password` varchar(50) NOT NULL DEFAULT '0',
  `identity` varchar(50) NOT NULL DEFAULT '0',
  `username` varchar(50) NOT NULL DEFAULT '0',
  `salt` varchar(50) DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 数据导出被取消选择。

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
