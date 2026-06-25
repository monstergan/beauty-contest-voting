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

 Date: 25/06/2026 10:39:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for vote_records
-- ----------------------------
DROP TABLE IF EXISTS `vote_records`;
CREATE TABLE `vote_records` (
  `id` bigint NOT NULL COMMENT '自增长主键',
  `candidate_id` bigint NOT NULL COMMENT '候选人ID',
  `voter_id` bigint NOT NULL COMMENT '用户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `candidate_id_index` (`candidate_id`) USING BTREE COMMENT '候选人ID索引',
  KEY `voter_id_index` (`voter_id`) USING BTREE COMMENT '用户ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='投票记录表';

SET FOREIGN_KEY_CHECKS = 1;
