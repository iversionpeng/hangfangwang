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

 Date: 07/04/2019 17:06:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for house
-- ----------------------------
DROP TABLE IF EXISTS `house`;
CREATE TABLE `house`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL DEFAULT '' COMMENT '房产名称',
  `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1：销售  2:出租',
  `price` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '单位元',
  `image` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '图片地址',
  `area` int(11) NOT NULL DEFAULT 0 COMMENT '面积',
  `beds` int(11) NOT NULL DEFAULT 0 COMMENT '卧室数量',
  `baths` int(11) NOT NULL DEFAULT 0 COMMENT '卫生间数量',
  `rating` double NOT NULL DEFAULT 0 COMMENT '评级',
  `remarks` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '房产描述',
  `properties` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '属性',
  `floor_plan` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '户型图',
  `tags` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标签',
  `create_time` datetime(0) NOT NULL DEFAULT '2019-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `city_id` int(11) NOT NULL DEFAULT 0 COMMENT '城市名称',
  `community_id` int(11) NOT NULL DEFAULT 0 COMMENT '小区名称',
  `address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '房产地址',
  `state` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1:上架 2:下架',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '房产表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
