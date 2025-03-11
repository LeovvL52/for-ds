/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : fire_monitoring

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 15/02/2025 21:16:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for camera
-- ----------------------------
DROP TABLE IF EXISTS `camera`;
CREATE TABLE `camera`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '设备ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备名称',
  `location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '安装位置',
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `rtsp_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'RTSP地址',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(0:离线 1:在线 2:故障)',
  `fault_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '故障类型',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '摄像头设备表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of camera
-- ----------------------------
INSERT INTO `camera` VALUES (1, 'CAM001', '急诊部门走廊', '192.168.1.101', 'rtsp://127.0.0.1:8554/camera_test', 0, NULL, '2025-02-12 01:08:53', '2025-02-12 01:08:53');
INSERT INTO `camera` VALUES (2, 'CAM002', '住院部一楼大厅', '192.168.1.102', 'rtsp://localhost:8554/test_fire_video_1', 0, NULL, '2025-02-12 01:08:53', '2025-02-12 01:08:53');
INSERT INTO `camera` VALUES (3, 'CAM003', '药房仓库', '192.168.1.103', 'rtsp://localhost:8554/test_fire_video_2', 0, NULL, '2025-02-12 01:08:53', '2025-02-12 01:08:53');
INSERT INTO `camera` VALUES (4, 'CAM004', '手术室外走廊', '192.168.1.104', 'rtsp://localhost:8554/test_fire_video_3', 0, NULL, '2025-02-12 01:08:53', '2025-02-12 01:08:53');
INSERT INTO `camera` VALUES (5, 'SuperCAM', '老板办公室', '192.168.0.36', 'rtsp://localhost:8554/test_fire_video_4', 2, 'hardware', '2025-02-13 09:52:43', '2025-02-13 09:52:43');

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '反馈ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `type` tinyint NOT NULL COMMENT '反馈类型(1:故障报修 2:意见建议 3:其他)',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '反馈内容',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '处理状态(0:未处理 1:处理中 2:已处理)',
  `reply` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '回复内容',
  `satisfaction_score` double NULL DEFAULT NULL COMMENT '满意度评分',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户反馈表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES (1, 2, 1, '3号摄像头图像不清晰，需要调试', 2, '已完成摄像头调试和清洁', NULL, '2025-02-12 01:08:57', '2025-02-12 01:08:57');
INSERT INTO `feedback` VALUES (2, 3, 2, '建议增加实时警报声音提醒功能', 1, '正在评估实施方案', NULL, '2025-02-12 01:08:57', '2025-02-12 01:08:57');
INSERT INTO `feedback` VALUES (3, 4, 1, '2号摄像头离线，请检查', 0, NULL, NULL, '2025-02-12 01:08:57', '2025-02-12 01:08:57');
INSERT INTO `feedback` VALUES (4, 1, 1, '监控摄像头1号出现画面卡顿现象，请及时处理', 2, '已派维修人员处理，问题已解决', NULL, '2025-02-12 01:09:22', '2025-02-12 01:09:22');
INSERT INTO `feedback` VALUES (5, 2, 2, '建议增加移动端的监控功能，方便随时查看', 2, '你看错了', NULL, '2025-02-12 01:09:22', '2025-02-12 01:09:22');
INSERT INTO `feedback` VALUES (6, 1, 1, '3号摄像头无法连接，请检查', 0, NULL, NULL, '2025-02-12 01:09:22', '2025-02-12 01:09:22');
INSERT INTO `feedback` VALUES (7, 3, 2, '希望能增加历史记录的导出功能', 2, '该功能已在最新版本中添加，请更新后使用', NULL, '2025-02-12 01:09:22', '2025-02-12 01:09:22');
INSERT INTO `feedback` VALUES (8, 2, 1, '2号摄像头图像模糊，需要调整焦距', 1, '已安排技术人员处理，预计今天下午完成', NULL, '2025-02-12 01:09:22', '2025-02-12 01:09:22');
INSERT INTO `feedback` VALUES (9, 4, 3, '系统操作流程不够清晰，建议增加新手引导', 2, '已优化操作流程并添加引导说明', NULL, '2025-02-12 01:09:22', '2025-02-12 01:09:22');
INSERT INTO `feedback` VALUES (10, 3, 1, '5号摄像头位置需要调整，当前视角不佳', 0, NULL, NULL, '2025-02-12 01:09:22', '2025-02-12 01:09:22');
INSERT INTO `feedback` VALUES (11, 1, 2, '建议增加自动报警提示音功能', 1, '该功能正在开发中，预计下月上线', NULL, '2025-02-12 01:09:22', '2025-02-12 01:09:22');
INSERT INTO `feedback` VALUES (12, 4, 1, '监控室显示器出现闪烁现象', 2, '已更换显示器，问题已解决', NULL, '2025-02-12 01:09:22', '2025-02-12 01:09:22');
INSERT INTO `feedback` VALUES (13, 2, 3, '系统响应速度较慢，需要优化', 0, NULL, NULL, '2025-02-12 01:09:22', '2025-02-12 01:09:22');
INSERT INTO `feedback` VALUES (14, 1, 3, '我就是来测试的', 0, NULL, NULL, '2025-02-15 16:34:23', '2025-02-15 16:34:23');

-- ----------------------------
-- Table structure for fire_event
-- ----------------------------
DROP TABLE IF EXISTS `fire_event`;
CREATE TABLE `fire_event`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '事件ID',
  `camera_id` bigint NOT NULL COMMENT '关联摄像头ID',
  `event_time` datetime(0) NOT NULL COMMENT '事件发生时间',
  `location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发生位置',
  `level` tinyint NOT NULL COMMENT '火情等级(1-5)',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '火灾类型',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '处理状态(0:未处理 1:处理中 2:已处理)',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '现场图片URL',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '事件描述',
  `handler_id` bigint NULL DEFAULT NULL COMMENT '处理人ID',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '处理时间',
  `handle_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '处理结果',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 159 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '火灾事件记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fire_event
-- ----------------------------
INSERT INTO `fire_event` VALUES (139, 2, '2025-02-15 07:14:22', '住院部一楼大厅', 4, NULL, 2, 'http://localhost:5001/api/file/fire-images/2594f15d-72d5-4c74-b8bc-0c93f74707c5.jpg', '火灾置信度：0.0%，烟雾置信度：87.0%，哈哈哈', 1, '2025-02-15 15:14:56', '罚款', '2025-02-15 15:14:22', '2025-02-15 15:14:22');
INSERT INTO `fire_event` VALUES (140, 3, '2025-02-15 07:14:25', '药房仓库', 3, NULL, 0, 'http://localhost:5001/api/file/fire-images/40e44e36-3243-466e-aac6-415fd319062d.jpg', '火灾置信度：77.9%，烟雾置信度：0.0%', NULL, NULL, NULL, '2025-02-15 15:14:25', '2025-02-15 15:14:25');
INSERT INTO `fire_event` VALUES (141, 2, '2025-02-15 07:37:23', '住院部一楼大厅', 4, NULL, 0, 'http://localhost:5001/api/file/fire-images/f86f4b1e-1ca0-4cda-9b45-55d3b6e3238a.jpg', '火灾置信度：0.0%，烟雾置信度：84.0%', NULL, NULL, NULL, '2025-02-15 15:37:23', '2025-02-15 15:37:23');
INSERT INTO `fire_event` VALUES (142, 3, '2025-02-15 07:37:27', '药房仓库', 3, NULL, 0, 'http://localhost:5001/api/file/fire-images/071ec5b2-7495-4fbd-9257-63f35409d98d.jpg', '火灾置信度：73.2%，烟雾置信度：0.0%', NULL, NULL, NULL, '2025-02-15 15:37:27', '2025-02-15 15:37:27');
INSERT INTO `fire_event` VALUES (143, 2, '2025-02-15 07:37:29', '住院部一楼大厅', 4, NULL, 0, 'http://localhost:5001/api/file/fire-images/0a8c56e1-ab5a-4112-a0a5-a6a6d35e0f3b.jpg', '火灾置信度：0.0%，烟雾置信度：83.4%', NULL, NULL, NULL, '2025-02-15 15:37:29', '2025-02-15 15:37:29');
INSERT INTO `fire_event` VALUES (144, 3, '2025-02-15 07:37:32', '药房仓库', 3, NULL, 0, 'http://localhost:5001/api/file/fire-images/3b02cdfb-1a53-4f04-bb09-18a3e9d964df.jpg', '火灾置信度：74.8%，烟雾置信度：0.0%', NULL, NULL, NULL, '2025-02-15 15:37:32', '2025-02-15 15:37:32');
INSERT INTO `fire_event` VALUES (145, 2, '2025-02-15 07:37:33', '住院部一楼大厅', 3, NULL, 0, 'http://localhost:5001/api/file/fire-images/0dc39dcc-caec-4f7d-9d34-da233f342ae6.jpg', '火灾置信度：0.0%，烟雾置信度：79.0%', NULL, NULL, NULL, '2025-02-15 15:37:33', '2025-02-15 15:37:33');
INSERT INTO `fire_event` VALUES (146, 3, '2025-02-15 07:37:36', '药房仓库', 3, NULL, 0, 'http://localhost:5001/api/file/fire-images/e5347961-90b4-46a9-b2b4-aa80b1e268de.jpg', '火灾置信度：74.2%，烟雾置信度：0.0%', NULL, NULL, NULL, '2025-02-15 15:37:36', '2025-02-15 15:37:36');
INSERT INTO `fire_event` VALUES (147, 2, '2025-02-15 07:37:38', '住院部一楼大厅', 3, NULL, 0, 'http://localhost:5001/api/file/fire-images/b39ee556-be80-43cf-9743-a8d2b5afeaef.jpg', '火灾置信度：0.0%，烟雾置信度：74.0%', NULL, NULL, NULL, '2025-02-15 15:37:38', '2025-02-15 15:37:38');
INSERT INTO `fire_event` VALUES (148, 2, '2025-02-15 07:58:45', '住院部一楼大厅', 4, NULL, 0, 'http://localhost:5001/api/file/fire-images/0dda4bd3-fe9f-476b-8d41-dee7b7484e83.jpg', '火灾置信度：0.0%，烟雾置信度：82.8%', NULL, NULL, NULL, '2025-02-15 15:58:45', '2025-02-15 15:58:45');
INSERT INTO `fire_event` VALUES (149, 3, '2025-02-15 07:58:49', '药房仓库', 3, NULL, 0, 'http://localhost:5001/api/file/fire-images/08b2e698-dcf7-4266-9a17-e450f47dad40.jpg', '火灾置信度：74.3%，烟雾置信度：0.0%', NULL, NULL, NULL, '2025-02-15 15:58:49', '2025-02-15 15:58:49');
INSERT INTO `fire_event` VALUES (150, 2, '2025-02-15 07:58:52', '住院部一楼大厅', 4, NULL, 0, 'http://localhost:5001/api/file/fire-images/88a5dafc-f73f-43a7-9f6d-aeb06c2254cf.jpg', '火灾置信度：0.0%，烟雾置信度：80.0%', NULL, NULL, NULL, '2025-02-15 15:58:52', '2025-02-15 15:58:52');
INSERT INTO `fire_event` VALUES (152, 2, '2025-02-15 07:58:59', '住院部一楼大厅', 4, NULL, 0, 'http://localhost:5001/api/file/fire-images/774335f2-bb59-43d7-825f-ac0d74887d2a.jpg', '火灾置信度：0.0%，烟雾置信度：80.2%', NULL, NULL, NULL, '2025-02-15 15:58:59', '2025-02-15 15:58:59');
INSERT INTO `fire_event` VALUES (154, 1, '2025-02-15 08:55:30', '急诊部门走廊', 2, NULL, 0, 'http://localhost:5001/api/file/fire-images/6f776524-c018-4121-ae0c-4edb6dfaa25a.jpg', '火灾置信度：68.4%，烟雾置信度：0.0%', NULL, NULL, NULL, '2025-02-15 16:55:30', '2025-02-15 16:55:30');
INSERT INTO `fire_event` VALUES (155, 1, '2025-02-15 08:55:32', '急诊部门走廊', 2, NULL, 0, 'http://localhost:5001/api/file/fire-images/825bdee5-cd78-4e3c-a327-f04c0e118d1a.jpg', '火灾置信度：62.5%，烟雾置信度：0.0%', NULL, NULL, NULL, '2025-02-15 16:55:32', '2025-02-15 16:55:32');
INSERT INTO `fire_event` VALUES (156, 1, '2025-02-15 08:55:35', '急诊部门走廊', 2, NULL, 0, 'http://localhost:5001/api/file/fire-images/4364c7a6-4940-42b8-8090-e2dbf386c473.jpg', '火灾置信度：62.8%，烟雾置信度：0.0%', NULL, NULL, NULL, '2025-02-15 16:55:35', '2025-02-15 16:55:35');
INSERT INTO `fire_event` VALUES (157, 1, '2025-02-15 08:55:39', '急诊部门走廊', 2, NULL, 0, 'http://localhost:5001/api/file/fire-images/1b21a278-641f-4a10-9241-b283b88f32cd.jpg', '火灾置信度：67.9%，烟雾置信度：0.0%', NULL, NULL, NULL, '2025-02-15 16:55:39', '2025-02-15 16:55:39');

-- ----------------------------
-- Table structure for inspection_record
-- ----------------------------
DROP TABLE IF EXISTS `inspection_record`;
CREATE TABLE `inspection_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `inspector_id` bigint NOT NULL COMMENT '检查人ID',
  `inspection_time` datetime(0) NOT NULL COMMENT '检查时间',
  `location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '检查位置',
  `result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '检查结果',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(0:异常 1:正常)',
  `image_urls` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '检查图片URLs',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '消防检查记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inspection_record
-- ----------------------------
INSERT INTO `inspection_record` VALUES (1, 1, '2025-02-11 01:08:53', '全院消防设施', '消防设施完好，运行正常', 0, 'http://localhost:5001/api/file/inspection-images/3b427dfe-7d4d-49af-b25e-981436ba5344.jpg,', '2025-02-12 01:08:53', '2025-02-12 01:08:53');
INSERT INTO `inspection_record` VALUES (2, 1, '2025-02-11 01:08:53', '急诊部门', '灭火器已过期，需要更换', 0, 'http://localhost:5001/api/file/inspection-images/2a1fbea5-418a-49ee-8ebf-03c13ae8a492.png,', '2025-02-12 01:08:53', '2025-02-12 01:08:53');
INSERT INTO `inspection_record` VALUES (3, 1, '2025-02-12 01:08:53', '住院部', '消防通道畅通，设施完好', 1, NULL, '2025-02-12 01:08:53', '2025-02-12 01:08:53');
INSERT INTO `inspection_record` VALUES (4, 1, '2025-02-15 00:00:00', '老板办公室', '没来上班', 0, 'http://localhost:5001/api/file/inspection-images/6a8617c0-c72b-4e7f-94b2-042b99720851.png', '2025-02-15 16:43:31', '2025-02-15 16:43:31');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `type` tinyint NOT NULL COMMENT '日志类型：1-登录日志，2-操作日志，3-异常日志',
  `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作模块',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作描述',
  `method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方法',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作IP',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作地点',
  `status` tinyint NOT NULL COMMENT '操作状态：0-失败，1-成功',
  `error` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '错误信息',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (1, 1, 1, '用户登录', '登录成功', 'POST', NULL, '192.168.1.100', '广东省广州市', 1, NULL, '2024-03-15 10:00:00');
INSERT INTO `log` VALUES (2, 2, 1, '用户登录', '登录失败', 'POST', NULL, '192.168.1.101', '广东省深圳市', 0, '密码错误', '2024-03-15 10:05:00');
INSERT INTO `log` VALUES (3, 1, 2, '用户管理', '新增用户：张三', 'POST', '{\"username\":\"zhangsan\",\"role\":0}', '192.168.1.100', '广东省广州市', 1, NULL, '2024-03-15 10:10:00');
INSERT INTO `log` VALUES (4, 3, 2, '设备管理', '更新摄像头状态', 'PUT', '{\"id\":1,\"status\":1}', '192.168.1.102', '广东省珠海市', 1, NULL, '2024-03-15 10:15:00');
INSERT INTO `log` VALUES (5, 1, 3, '火灾检测', '调用算法服务异常', 'POST', '{\"imageUrl\":\"http://xxx.jpg\"}', '192.168.1.100', '广东省广州市', 0, '连接超时', '2024-03-15 10:20:00');
INSERT INTO `log` VALUES (6, 4, 2, '系统设置', '修改系统配置', 'PUT', '{\"alarmThreshold\":0.8}', '192.168.1.103', '广东省佛山市', 1, NULL, '2024-03-15 10:25:00');
INSERT INTO `log` VALUES (7, 2, 2, '用户反馈', '回复用户反馈', 'PUT', '{\"id\":1,\"reply\":\"已处理\"}', '192.168.1.101', '广东省深圳市', 1, NULL, '2024-03-15 10:30:00');
INSERT INTO `log` VALUES (8, 3, 1, '用户登录', '登录成功', 'POST', NULL, '192.168.1.102', '广东省珠海市', 1, NULL, '2024-03-15 10:35:00');
INSERT INTO `log` VALUES (9, 1, 2, '日志管理', '清空系统日志', 'DELETE', NULL, '192.168.1.100', '广东省广州市', 1, NULL, '2024-03-15 10:40:00');
INSERT INTO `log` VALUES (10, 4, 3, '文件上传', '上传文件失败', 'POST', '{\"fileName\":\"test.jpg\"}', '192.168.1.103', '广东省佛山市', 0, '文件格式不支持', '2024-03-15 10:45:00');

-- ----------------------------
-- Table structure for system_log
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `operation` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作内容',
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_log
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `role` tinyint NOT NULL DEFAULT 0 COMMENT '角色(0:普通用户 1:管理员)',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(0:禁用 1:启用)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', NULL, NULL, 1, 1, '2025-02-12 01:08:53', '2025-02-12 01:08:53');
INSERT INTO `user` VALUES (2, 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '张三', '13800138001', 'zhangsan@example.com', 0, 1, '2025-02-12 01:08:53', '2025-02-12 01:08:53');
INSERT INTO `user` VALUES (3, 'lisi', 'e10adc3949ba59abbe56e057f20f883e', '李四', '13800138002', 'lisi@example.com', 0, 0, '2025-02-12 01:08:53', '2025-02-12 01:08:53');
INSERT INTO `user` VALUES (4, 'wangwu', 'e10adc3949ba59abbe56e057f20f883e', '王五', '13800138003', 'wangwu@example.com', 0, 1, '2025-02-12 01:08:53', '2025-02-12 01:08:53');
INSERT INTO `user` VALUES (5, 'guojianqiang', '1d2b03e4a6d496dc8f1026b8a8a849e2', '羊羊小栈', '18753426320', 'kill@6356.com', 0, 1, '2025-02-12 02:49:24', '2025-02-12 02:49:24');
INSERT INTO `user` VALUES (6, 'wangxiaoyu', '001fe5b050857e8920ebc703731a0b72', '王小雨', '18759627892', '18759627892@qq.com', 0, 1, '2025-02-12 02:52:03', '2025-02-12 02:52:03');

SET FOREIGN_KEY_CHECKS = 1;
