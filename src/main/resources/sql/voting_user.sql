/*
 Navicat Premium Dump SQL

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80410 (8.4.10)
 Source Host           : localhost:3306
 Source Schema         : voter

 Target Server Type    : MySQL
 Target Server Version : 80410 (8.4.10)
 File Encoding         : 65001

 Date: 25/06/2026 10:39:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for voting_user
-- ----------------------------
DROP TABLE IF EXISTS `voting_user`;
CREATE TABLE `voting_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `birthday_year` smallint NOT NULL COMMENT '出生年',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `is_voted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已经投票',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_user` varchar(125) NOT NULL COMMENT '创建人信息',
  `update_user` varchar(125) DEFAULT NULL COMMENT '更新人名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_index` (`user_name`) USING BTREE COMMENT '用户名唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='投票系统用户表';

SET FOREIGN_KEY_CHECKS = 1;
