/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : localhost:3306
 Source Schema         : houses

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : 65001

 Date: 07/04/2019 17:06:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for house_user
-- ----------------------------
DROP TABLE IF EXISTS `house_user`;
CREATE TABLE `house_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `house_id` bigint(20) NOT NULL COMMENT '房屋id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `create_time` date NOT NULL COMMENT '创建时间',
  `type` tinyint(1) NOT NULL COMMENT '1-售卖 2-收藏',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `house_id_user_id_type`(`house_id`, `user_id`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
