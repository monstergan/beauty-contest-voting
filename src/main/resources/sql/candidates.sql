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

 Date: 25/06/2026 10:39:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for candidates
-- ----------------------------
DROP TABLE IF EXISTS `candidates`;
CREATE TABLE `candidates` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `candidate_name` varchar(125) NOT NULL COMMENT '候选人姓名',
  `age` int DEFAULT NULL COMMENT '候选人年龄',
  `video_url` varchar(500) NOT NULL COMMENT '候选人视频链接',
  `introduction` varchar(1000) DEFAULT NULL COMMENT '候选人介绍',
  `votes_number` int NOT NULL DEFAULT '0' COMMENT '投票数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `candidate_name_index` (`candidate_name`) USING BTREE COMMENT '候选人唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='候选人信息表';

SET FOREIGN_KEY_CHECKS = 1;
