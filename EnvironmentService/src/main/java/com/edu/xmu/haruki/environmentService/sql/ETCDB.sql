/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : ETCDB

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 18/07/2022 15:47:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for environment
-- ----------------------------
DROP TABLE IF EXISTS `environment`;
CREATE TABLE `environment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL COMMENT '场景名',
  `info` varchar(255) DEFAULT NULL COMMENT '场景信息',
  `status` tinyint DEFAULT '0' COMMENT '0禁用，1启用，-1删除',
  PRIMARY KEY (`id`),
  KEY `env_name_index` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for exception_record
-- ----------------------------
DROP TABLE IF EXISTS `exception_record`;
CREATE TABLE `exception_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sensor_id` int NOT NULL,
  `env_id` int DEFAULT NULL,
  `sense_value` decimal(10,2) DEFAULT NULL,
  `record_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sensor_id_index` (`sensor_id`) USING BTREE,
  KEY `env_id_index` (`env_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sensor_id` int NOT NULL,
  `env_id` int DEFAULT NULL,
  `sensor_type` varchar(15) NOT NULL,
  `sense_value` decimal(10,2) DEFAULT NULL,
  `record_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sensor_id_index` (`sensor_id`) USING BTREE,
  KEY `env_id_index` (`env_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for sensor
-- ----------------------------
DROP TABLE IF EXISTS `sensor`;
CREATE TABLE `sensor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `PAN_id` varchar(30) DEFAULT NULL,
  `type` varchar(15) DEFAULT NULL,
  `env_belongs` int DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name_sensor_index` (`name`) USING BTREE,
  KEY `env_id_index` (`env_belongs`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `admin` tinyint DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `uname_index` (`username`) USING BTREE,
  KEY `uname_passwd_index` (`username`,`password`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Triggers structure for table environment
-- ----------------------------
DROP TRIGGER IF EXISTS `on_ban_env_trigger`;
delimiter ;;
CREATE TRIGGER `on_ban_env_trigger` AFTER UPDATE ON `environment` FOR EACH ROW BEGIN
	IF NEW.`status`=0 OR NEW.`status`=-1 THEN
		UPDATE sensor SET sensor.status=NEW.`status` where sensor.env_belongs=NEW.id;
	END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table sensor
-- ----------------------------
DROP TRIGGER IF EXISTS `create_trigger_sensor`;
delimiter ;;
CREATE TRIGGER `create_trigger_sensor` BEFORE INSERT ON `sensor` FOR EACH ROW BEGIN
	SET NEW.create_time=NOW();
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table sensor
-- ----------------------------
DROP TRIGGER IF EXISTS `update_trigger_sensor`;
delimiter ;;
CREATE TRIGGER `update_trigger_sensor` BEFORE UPDATE ON `sensor` FOR EACH ROW BEGIN
	SET NEW.update_time=NOW();
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `create_trigger`;
delimiter ;;
CREATE TRIGGER `create_trigger` BEFORE INSERT ON `user` FOR EACH ROW BEGIN
	SET NEW.create_time=NOW();
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `update_trigger`;
delimiter ;;
CREATE TRIGGER `update_trigger` BEFORE INSERT ON `user` FOR EACH ROW BEGIN
	set NEW.update_time=NOW();
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
