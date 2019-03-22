/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50642
 Source Host           : localhost:3306
 Source Schema         : laboratory

 Target Server Type    : MySQL
 Target Server Version : 50642
 File Encoding         : 65001

 Date: 22/03/2019 18:08:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `create_instant` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `date_time_test` datetime(6) DEFAULT NULL,
  `date_test` date DEFAULT NULL,
  `time_test` time(6) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'ceshi', '2019-03-22 18:07:38.074466', '2019-03-22 18:07:18.000000', '2019-03-22', '18:07:36.000000');
INSERT INTO `user` VALUES (2, 'ceshi', '2019-03-22 18:07:45.197873', '2019-03-22 18:07:24.000000', '2019-03-21', '18:07:39.000000');

SET FOREIGN_KEY_CHECKS = 1;
