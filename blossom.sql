/*
 Navicat Premium Dump SQL

 Source Server         : Blossom-FlowerShop
 Source Server Type    : MySQL
 Source Server Version : 80020 (8.0.20)
 Source Host           : 101.201.65.241:3306
 Source Schema         : blossom

 Target Server Type    : MySQL
 Target Server Version : 80020 (8.0.20)
 File Encoding         : 65001

 Date: 15/04/2025 15:35:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `status` int NOT NULL,
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `limit_per` int NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `createUser` bigint NOT NULL,
  `updateUser` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (1, '2025-06-09 22:40:47', '2025-06-10 22:00:00', 1, '促销活动', 3, '2025-06-09 22:00:00', '2025-06-09 22:00:00', 0, 0);

-- ----------------------------
-- Table structure for activity_sale
-- ----------------------------
DROP TABLE IF EXISTS `activity_sale`;
CREATE TABLE `activity_sale`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activity_id` bigint NOT NULL,
  `flower_id` bigint NOT NULL,
  `original_price` decimal(10, 2) NOT NULL,
  `discount_price` decimal(10, 2) NOT NULL,
  `stock` int NOT NULL,
  `sale` int NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `version` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `activity_sale_activity_id_uindex`(`activity_id` ASC) USING BTREE,
  UNIQUE INDEX `activity_sale_flower_id_uindex`(`flower_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of activity_sale
-- ----------------------------
INSERT INTO `activity_sale` VALUES (1, 1, 5, 56.00, 46.00, 114, 0, '2025-04-12 09:40:51', '2025-04-12 09:40:51', 1);

-- ----------------------------
-- Table structure for address_book
-- ----------------------------
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `consignee` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '收货人',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '默认 0 否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '地址簿' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of address_book
-- ----------------------------

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int NULL DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `sort` int NOT NULL DEFAULT 0 COMMENT '顺序',
  `status` int NULL DEFAULT NULL COMMENT '分类状态 0:禁用，1:启用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_category_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品及套餐分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (11, 1, '康乃馨', 10, 1, '2022-06-09 22:09:18', '2022-06-09 22:09:18', 1, 1);
INSERT INTO `category` VALUES (12, 1, '玩偶花束', 9, 1, '2022-06-09 22:09:32', '2022-06-09 22:18:53', 1, 1);
INSERT INTO `category` VALUES (15, 2, '玫瑰', 13, 1, '2022-06-09 22:14:10', '2025-01-25 21:56:16', 1, 1);
INSERT INTO `category` VALUES (16, 1, '推荐', 4, 1, '2022-06-09 22:15:37', '2025-01-21 23:48:06', 1, 1);
INSERT INTO `category` VALUES (17, 1, '郁金香', 5, 1, '2022-06-09 22:16:14', '2025-01-18 11:52:05', 1, 1);
INSERT INTO `category` VALUES (18, 1, '零食花束', 6, 1, '2022-06-09 22:17:42', '2022-06-09 22:17:42', 1, 1);
INSERT INTO `category` VALUES (19, 1, '百合花', 7, 1, '2022-06-09 22:18:12', '2025-01-23 18:29:18', 1, 1);
INSERT INTO `category` VALUES (20, 1, '白兰花', 8, 1, '2022-06-09 22:22:29', '2022-06-09 22:23:45', 1, 1);
INSERT INTO `category` VALUES (21, 1, '牡丹花', 11, 1, '2022-06-10 10:51:47', '2022-06-10 10:51:47', 1, 1);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `flower_id` bigint NOT NULL,
  `rating` int NOT NULL,
  `content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `like_count` int NOT NULL DEFAULT 0,
  `reply_count` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_flower_idx`(`flower_id` ASC) USING BTREE,
  INDEX `fk_user_idx`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_flower` FOREIGN KEY (`flower_id`) REFERENCES `flower` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 4, 3, 5, '五河琴里天下第一！', '2022-06-09 22:40:47', 520, 0);
INSERT INTO `comment` VALUES (2, 4, 3, 5, '收到玫瑰了，实物和图片一样，好评！', '2025-04-08 17:12:49', 0, 0);
INSERT INTO `comment` VALUES (4, 4, 15, 5, '送给女神洛琪希了，她很喜欢', '2025-04-08 21:56:47', 0, 0);
INSERT INTO `comment` VALUES (5, 7, 1, 5, '123', '2025-04-14 19:28:59', 0, 0);
INSERT INTO `comment` VALUES (6, 7, 1, 5, '123', '2025-04-14 19:31:23', 0, 0);
INSERT INTO `comment` VALUES (7, 7, 1, 5, '123', '2025-04-14 19:32:29', 0, 0);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '身份证号',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '员工信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '管理员', 'admin', '123456', '13812312312', '1', '110101199001010047', 1, '2022-02-15 15:51:20', '2022-02-17 09:16:20', 10, 1);
INSERT INTO `employee` VALUES (2, '', '', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', 1, '2025-01-15 20:34:47', '2025-04-08 02:36:54', 10, 1);
INSERT INTO `employee` VALUES (3, '', 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', 1, '2025-01-15 21:10:38', '2025-01-15 21:10:38', 10, 10);
INSERT INTO `employee` VALUES (8, 'Sayori', 'sayori', 'e10adc3949ba59abbe56e057f20f883e', '13956624569', '1', '445632145698745631', 1, '2025-01-16 10:39:05', '2025-01-17 20:22:09', 1, 1);
INSERT INTO `employee` VALUES (9, 'Monika', 'monika', 'e10adc3949ba59abbe56e057f20f883e', '13921165493', '女', '123', 1, '2025-01-16 14:02:31', '2025-01-21 23:47:26', 1, 1);
INSERT INTO `employee` VALUES (10, '管理', 'cuiyue', '114514', '10000000001', '1', '000000000000000000', 1, '2025-01-16 14:02:32', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for flower
-- ----------------------------
DROP TABLE IF EXISTS `flower`;
CREATE TABLE `flower`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
  `category_id` bigint NOT NULL COMMENT '菜品分类id',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品价格',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `status` int NULL DEFAULT 1 COMMENT '0 停售 1 起售',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_flower_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of flower
-- ----------------------------
INSERT INTO `flower` VALUES (1, '【满目星河】香槟玫瑰混搭', 15, 4.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', '无', 1, '2022-06-10 09:18:49', '2022-06-10 09:18:49', 1, 1);
INSERT INTO `flower` VALUES (2, '香槟玫瑰', 15, 4.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', '无', 1, '2022-06-10 09:22:54', '2022-06-10 09:22:54', 1, 1);
INSERT INTO `flower` VALUES (3, '红玫瑰', 15, 5.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/76752350-2121-44d2-b477-10791c23a8ec.png', '无', 1, '2022-06-10 09:30:17', '2022-06-10 09:30:17', 1, 1);
INSERT INTO `flower` VALUES (4, '粉玫瑰', 15, 5.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/475cc599-8661-4899-8f9e-121dd8ef7d02.png', '无', 1, '2022-06-10 09:34:28', '2022-06-10 09:34:28', 1, 1);
INSERT INTO `flower` VALUES (5, '【落入星河】碎冰蓝玫瑰', 15, 56.00, 'https://upyun.dinghuale.com/uploads/20230216/202302161328231451.jpg', '无', 1, '2022-06-10 09:40:51', '2022-06-10 09:40:51', 1, 1);
INSERT INTO `flower` VALUES (6, '【恋爱皇冠】红玫瑰花束', 15, 66.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/5260ff39-986c-4a97-8850-2ec8c7583efc.png', '无', 1, '2022-06-10 09:46:02', '2022-06-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (15, '【粉色物语】粉玫瑰混搭', 15, 30.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', '粉色浪漫~', 1, '2022-06-09 22:40:47', '2022-06-09 22:40:47', 1, 1);
INSERT INTO `flower` VALUES (53, '【莫奈花园】康乃馨混搭雪山玫瑰', 11, 38.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a6953d5a-4c18-4b30-9319-4926ee77261f.png', '无', 1, '2022-06-10 09:48:37', '2022-06-10 09:48:37', 1, 1);
INSERT INTO `flower` VALUES (54, '粉色康乃馨', 11, 3.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/3613d38e-5614-41c2-90ed-ff175bf50716.png', '无', 1, '2022-06-10 09:51:46', '2022-06-10 09:51:46', 1, 1);
INSERT INTO `flower` VALUES (55, '白色康乃馨', 11, 3.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4879ed66-3860-4b28-ba14-306ac025fdec.png', '无', 1, '2022-06-10 09:53:37', '2022-06-10 09:53:37', 1, 1);
INSERT INTO `flower` VALUES (56, '红色康乃馨', 11, 3.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/e9ec4ba4-4b22-4fc8-9be0-4946e6aeb937.png', '无', 1, '2022-06-10 09:55:44', '2022-06-10 09:55:44', 1, 1);
INSERT INTO `flower` VALUES (57, '【无私的爱】母亲节鲜花13支红色康乃馨', 11, 18.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/22f59feb-0d44-430e-a6cd-6a49f27453ca.png', '母亲节必送！', 1, '2022-06-10 09:58:35', '2022-06-10 09:58:35', 1, 1);
INSERT INTO `flower` VALUES (58, '70根棒棒糖满天星', 18, 98.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c18b5c67-3b71-466c-a75a-e63c6449f21c.png', '无', 1, '2022-06-10 10:12:28', '2022-06-10 10:12:28', 1, 1);
INSERT INTO `flower` VALUES (59, '10瓶益力多配粉色满天星', 18, 35.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png', '无', 1, '2022-06-10 10:24:03', '2022-06-10 10:24:03', 1, 1);
INSERT INTO `flower` VALUES (60, '零食6包随机配满天星', 18, 58.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/6080b118-e30a-4577-aab4-45042e3f88be.png', '无', 1, '2022-06-10 10:26:03', '2022-06-10 10:26:03', 1, 1);
INSERT INTO `flower` VALUES (61, '11支红玫瑰搭配9颗巧克力', 18, 66.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/13da832f-ef2c-484d-8370-5934a1045a06.png', '无', 1, '2022-06-10 10:28:54', '2022-06-10 10:28:54', 1, 1);
INSERT INTO `flower` VALUES (62, '【六一儿童节】粉色系零食花束', 18, 88.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7694a5d8-7938-4e9d-8b9e-2075983a2e38.png', '送给孩子的礼物~', 1, '2022-06-10 10:33:05', '2022-06-10 10:33:05', 1, 1);
INSERT INTO `flower` VALUES (63, '【满目星河皆是你】白玫瑰混搭', 16, 88.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/f5ac8455-4793-450c-97ba-173795c34626.png', '浪漫情调~', 1, '2022-06-10 10:35:40', '2022-06-10 10:35:40', 1, 1);
INSERT INTO `flower` VALUES (64, '百合花', 19, 5.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7a55b845-1f2b-41fa-9486-76d187ee9ee1.png', '无', 1, '2022-06-10 10:37:52', '2022-06-10 10:37:52', 1, 1);
INSERT INTO `flower` VALUES (65, '草鱼2斤', 16, 68.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/b544d3ba-a1ae-4d20-a860-81cb5dec9e03.png', '原料：草鱼，黄豆芽，莲藕', 1, '2022-06-10 10:41:08', '2022-06-10 10:41:08', 1, 1);
INSERT INTO `flower` VALUES (66, '江团鱼2斤', 19, 114514.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png', '配料：江团鱼，黄豆芽，莲藕', 1, '2022-06-10 10:42:42', '2025-01-23 22:05:26', 1, 1);
INSERT INTO `flower` VALUES (67, '鮰鱼2斤', 16, 72.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/8cfcc576-4b66-4a09-ac68-ad5b273c2590.png', '原料：鮰鱼，黄豆芽，莲藕', 1, '2022-06-10 10:43:56', '2022-06-10 10:43:56', 1, 1);
INSERT INTO `flower` VALUES (68, '鸡蛋汤', 21, 5.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c09a0ee8-9d19-428d-81b9-746221824113.png', '配料：鸡蛋，紫菜', 1, '2022-06-10 10:54:25', '2025-01-23 22:18:26', 1, 1);
INSERT INTO `flower` VALUES (69, '平菇豆腐汤', 21, 114514.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/16d0a3d6-2253-4cfc-9b49-bf7bd9eb2ad2.png', '配料：豆腐，平菇', 1, '2022-06-10 10:55:02', '2025-01-25 21:56:33', 1, 1);
INSERT INTO `flower` VALUES (70, '香菜', 0, 2.50, '', '', 1, '2025-01-26 17:00:23', '2025-01-26 17:00:23', 1, 1);

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名字',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `flower_id` bigint NULL DEFAULT NULL COMMENT '菜品id',
  `number` int NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单号',
  `status` int NOT NULL DEFAULT 1 COMMENT '订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款',
  `user_id` bigint NOT NULL COMMENT '下单用户',
  `address_book_id` bigint NOT NULL COMMENT '地址id',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `checkout_time` datetime NULL DEFAULT NULL COMMENT '结账时间',
  `pay_method` int NOT NULL DEFAULT 1 COMMENT '支付方式 1微信,2支付宝',
  `pay_status` tinyint NOT NULL DEFAULT 0 COMMENT '支付状态 0未支付 1已支付 2退款',
  `amount` decimal(10, 2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '地址',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户名称',
  `consignee` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '收货人',
  `cancel_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单取消原因',
  `rejection_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单拒绝原因',
  `cancel_time` datetime NULL DEFAULT NULL COMMENT '订单取消时间',
  `estimated_delivery_time` datetime NULL DEFAULT NULL COMMENT '预计送达时间',
  `delivery_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '配送状态  1立即送出  0选择具体时间',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '送达时间',
  `pack_amount` int NULL DEFAULT NULL COMMENT '打包费',
  `tableware_number` int NULL DEFAULT NULL COMMENT '餐具数量',
  `tableware_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '餐具数量状态  1按餐量提供  0选择具体数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for participation
-- ----------------------------
DROP TABLE IF EXISTS `participation`;
CREATE TABLE `participation`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activity_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `order_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `participation_activity_id_uindex`(`activity_id` ASC) USING BTREE,
  UNIQUE INDEX `participation_user_id_uindex`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of participation
-- ----------------------------

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '商品名称',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `user_id` bigint NOT NULL COMMENT '主键',
  `flower_id` bigint NULL DEFAULT NULL COMMENT '菜品id',
  `number` int NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '购物车' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------
INSERT INTO `shopping_cart` VALUES (10, '米饭', 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/76752350-2121-44d2-b477-10791c23a8ec.png', 4, 49, 1, 2.00, '2025-04-09 01:56:15');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '微信用户唯一标识',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime NULL DEFAULT NULL,
  `username` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `password` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (4, 'oe29Y7Dl9KAKB8YReJz6KRvKkCzY', '五河琴里', '13333313133', '女', '111111111111111111', NULL, '2025-02-10 10:33:49', NULL, NULL);
INSERT INTO `user` VALUES (5, NULL, '五河琴里', '13333313133', '女', '111111111111111111', NULL, '2025-04-10 15:46:06', NULL, NULL);
INSERT INTO `user` VALUES (6, NULL, '时崎狂三', '13333313133', '女', '111111111111111111', NULL, '2025-04-10 15:48:23', 'cuiyue', '114514');
INSERT INTO `user` VALUES (7, NULL, '艾莉丝', '13133314564', '女', '44111111111112222', NULL, NULL, 'cuiyuestar', '123456');

-- ----------------------------
-- Table structure for user_comment
-- ----------------------------
DROP TABLE IF EXISTS `user_comment`;
CREATE TABLE `user_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `usercomment_comment_id_uindex`(`comment_id` ASC) USING BTREE,
  UNIQUE INDEX `usercomment_user_id_uindex`(`user_id` ASC) USING BTREE,
  CONSTRAINT `like_comment_id` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `like_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_comment
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
