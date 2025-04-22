/*
 Navicat Premium Dump SQL

 Source Server         : Blossom-FlowerShop
 Source Server Type    : MySQL
 Source Server Version : 80020 (8.0.20)
 Source Host           : 101.201.65.241:3978
 Source Schema         : blossom

 Target Server Type    : MySQL
 Target Server Version : 80020 (8.0.20)
 File Encoding         : 65001

 Date: 22/04/2025 20:11:07
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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (1, '2025-06-09 22:40:47', '2025-06-10 22:00:00', 1, '促销活动', 3, '2025-01-09 22:00:00', '2025-01-09 22:00:00', 0, 0);

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_sale
-- ----------------------------
INSERT INTO `activity_sale` VALUES (1, 1, 5, 56.00, 46.00, 114, 0, '2025-04-12 09:40:51', '2025-04-12 09:40:51', 1);
INSERT INTO `activity_sale` VALUES (2, 1, 15, 30.00, 20.00, 100, 0, '2025-04-19 09:40:51', '2025-04-19 09:40:51', 1);
INSERT INTO `activity_sale` VALUES (3, 1, 63, 88.00, 66.00, 150, 0, '2025-04-19 09:40:51', '2025-04-19 09:40:51', 1);
INSERT INTO `activity_sale` VALUES (4, 1, 62, 88.00, 72.00, 200, 0, '2025-04-19 09:40:51', '2025-04-19 09:40:51', 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '地址簿' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address_book
-- ----------------------------

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `sort` int NOT NULL DEFAULT 0 COMMENT '顺序',
  `status` int NULL DEFAULT NULL COMMENT '分类状态 0:禁用，1:启用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_category_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品及套餐分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (11, '康乃馨', 10, 1, '2022-06-09 22:09:18', '2022-06-09 22:09:18', 1, 1, 'https://pic.616pic.com/photoone/00/00/78/618cea23cb63f1056.jpg!/fw/1120');
INSERT INTO `category` VALUES (12, '玩偶花束', 9, 1, '2022-06-09 22:09:32', '2022-06-09 22:18:53', 1, 1, 'https://cbu01.alicdn.com/img/ibank/O1CN01z0FgsE22GBjFkUMwH_!!3935607092-0-cib.jpg');
INSERT INTO `category` VALUES (15, '玫瑰', 13, 1, '2022-06-09 22:14:10', '2025-04-19 11:10:42', 1, 1, 'https://www.168hua.com/photo/2019181258270.jpg');
INSERT INTO `category` VALUES (16, '推荐', 4, 1, '2022-06-09 22:15:37', '2025-01-21 23:48:06', 1, 1, 'https://img95.699pic.com/photo/50116/0866.jpg_wh300.jpg!/fh/300/quality/90');
INSERT INTO `category` VALUES (17, '郁金香', 5, 1, '2022-06-09 22:16:14', '2025-01-18 11:52:05', 1, 1, 'https://img.pconline.com.cn/images/upload/upc/tx/itbbs/1904/30/c23/145350822_1556612948968_mthumb.jpg');
INSERT INTO `category` VALUES (18, '零食花束', 6, 1, '2022-06-09 22:17:42', '2022-06-09 22:17:42', 1, 1, 'https://img.alicdn.com/bao/uploaded/i3/309768391/O1CN01xC4eFZ2Br84jAc5tn_!!309768391.jpg');
INSERT INTO `category` VALUES (19, '百合花', 7, 1, '2022-06-09 22:18:12', '2025-01-23 18:29:18', 1, 1, 'https://img.huabaike.com/tukuimgs/375/20200922141336_124555.jpg');
INSERT INTO `category` VALUES (20, '白兰花', 8, 1, '2022-06-09 22:22:29', '2022-06-09 22:23:45', 1, 1, 'https://www.dajiazhao.com/uploads/allimg/221130/4-221130201219220.jpg');
INSERT INTO `category` VALUES (21, '牡丹花', 11, 1, '2022-06-10 10:51:47', '2022-06-10 10:51:47', 1, 1, 'https://img.huabaike.com/uploads/allimg/20120405/120405113340820390-L.png');

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
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 4, 3, 5, '送给妹妹琴里了，她开心得像个孩子一样！', '2022-06-09 22:40:47', 521, 0);
INSERT INTO `comment` VALUES (2, 4, 3, 5, '收到玫瑰了，实物和图片一样，好评！', '2025-04-08 17:12:49', 102, 0);
INSERT INTO `comment` VALUES (4, 4, 15, 5, '送给女神洛琪希了，她很喜欢', '2025-04-08 21:56:47', 30, 0);
INSERT INTO `comment` VALUES (5, 7, 62, 5, '六一送给孩子了，他很开心', '2025-04-14 19:33:38', 25, 0);
INSERT INTO `comment` VALUES (6, 7, 57, 5, '收到了，母亲很开心', '2025-04-14 19:33:38', 10, 0);
INSERT INTO `comment` VALUES (7, 7, 3, 5, '很棒！', '2025-04-14 19:33:38', 30, 0);
INSERT INTO `comment` VALUES (8, 6, 3, 5, '很漂亮', '2025-04-14 19:33:38', 4, 0);
INSERT INTO `comment` VALUES (9, 6, 3, 4, '好香啊！', '2025-04-14 19:33:38', 15, 0);
INSERT INTO `comment` VALUES (10, 6, 5, 5, '很漂亮', '2025-04-14 19:33:38', 4, 0);
INSERT INTO `comment` VALUES (11, 6, 15, 5, '超级喜欢', '2025-04-14 19:33:38', 4, 0);
INSERT INTO `comment` VALUES (12, 6, 53, 5, '收到了，很漂亮', '2025-04-14 19:33:38', 4, 0);
INSERT INTO `comment` VALUES (13, 6, 54, 3, '感觉有点不够新鲜', '2025-04-14 19:33:38', 4, 0);
INSERT INTO `comment` VALUES (14, 6, 55, 4, '非常香', '2025-04-14 19:33:38', 4, 0);
INSERT INTO `comment` VALUES (15, 6, 64, 4, '还行', '2025-04-14 19:33:38', 48, 0);
INSERT INTO `comment` VALUES (16, 6, 65, 4, '还可以', '2025-04-14 19:33:38', 12, 0);
INSERT INTO `comment` VALUES (17, 6, 66, 4, '还不错', '2025-04-14 19:33:38', 55, 0);
INSERT INTO `comment` VALUES (18, 6, 67, 5, '挺好的', '2025-04-14 19:33:38', 17, 0);
INSERT INTO `comment` VALUES (19, 6, 68, 5, '666', '2025-04-12 19:33:38', 59, 0);
INSERT INTO `comment` VALUES (20, 7, 70, 5, '小熊好可爱！！', '2025-04-18 19:33:38', 102, 0);
INSERT INTO `comment` VALUES (21, 7, 15, 5, '送给五河琴里妹妹', '2025-04-21 21:24:14', 115, 0);
INSERT INTO `comment` VALUES (22, 7, 1, 5, '送给朋友了，他很开心~', '2025-04-21 21:24:14', 12, 0);
INSERT INTO `comment` VALUES (23, 7, 2, 2, '质量一般……', '2025-04-21 21:24:14', 0, 0);
INSERT INTO `comment` VALUES (24, 6, 15, 2, '一般般，避雷了……', '2025-04-21 21:24:14', 2, 0);
INSERT INTO `comment` VALUES (25, 7, 4, 5, '收到现货了，真的很不错！！', '2025-04-21 21:24:14', 15, 0);
INSERT INTO `comment` VALUES (26, 7, 5, 5, '很漂亮，很适合送礼', '2025-04-21 21:24:14', 29, 0);
INSERT INTO `comment` VALUES (27, 7, 6, 5, '送给女朋友了！！', '2025-04-21 21:24:14', 37, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '管理员', 'admin', '123456', '13812312312', '1', '110101199001010047', 1, '2022-02-15 15:51:20', '2022-02-17 09:16:20', 10, 1);
INSERT INTO `employee` VALUES (2, '', '', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', 1, '2025-01-15 20:34:47', '2025-04-08 02:36:54', 10, 1);
INSERT INTO `employee` VALUES (3, '', 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '', '', '', 1, '2025-01-15 21:10:38', '2025-01-15 21:10:38', 10, 10);
INSERT INTO `employee` VALUES (8, 'Sayori', 'sayori', 'e10adc3949ba59abbe56e057f20f883e', '13956624569', '1', '445632145698745631', 1, '2025-01-16 10:39:05', '2025-01-17 20:22:09', 1, 1);
INSERT INTO `employee` VALUES (9, 'Monika', 'monika', 'e10adc3949ba59abbe56e057f20f883e', '13921165493', '女', '123', 1, '2025-01-16 14:02:31', '2025-01-21 23:47:26', 1, 1);
INSERT INTO `employee` VALUES (10, '管理', 'cuiyue', '114514', '10000000001', '1', '000000000000000000', 1, '2025-01-16 14:02:32', NULL, NULL, NULL);
INSERT INTO `employee` VALUES (11, '艾丽丝', 'kkk', 'e10adc3949ba59abbe56e057f20f883e', '13946485466', '女', '123456', 1, '2025-04-19 11:19:40', '2025-04-19 11:19:40', NULL, NULL);
INSERT INTO `employee` VALUES (12, '艾丽丝', 'AILISI', 'e10adc3949ba59abbe56e057f20f883e', '13687795466', '女', '123456789', 1, '2025-04-19 19:42:08', '2025-04-19 19:42:08', NULL, NULL);
INSERT INTO `employee` VALUES (14, '琴里', 'qinli', 'e10adc3949ba59abbe56e057f20f883e', '13954464766', '女', '11111', 0, '2025-04-19 19:45:01', '2025-04-19 20:12:13', NULL, NULL);

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flower
-- ----------------------------
INSERT INTO `flower` VALUES (1, '【满目星河】香槟玫瑰混搭', 15, 4.00, 'https://www.facaishur.com/uploads/allimg/190816/14-1ZQ61S045533.jpg', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (2, '香槟玫瑰', 15, 4.00, 'https://ts1.tc.mm.bing.net/th/id/R-C.d4d527bb8ed8c678de2137c33dd676c3?rik=xoJlBNC3zitEVA&riu=http%3a%2f%2fwww.hua.com%2fuploadpic%2fimages%2f201510121553398502673.jpg&ehk=cFEY5%2b66XnZj%2fI1ipYb6ZK55aL3z73mZj5Tn8CZTnIk%3d&risl=&pid=ImgRaw&r=0', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (3, '红玫瑰', 15, 5.00, 'https://pic.52112.com/180524/JPG-180524_33/3twFwQQQep_small.jpg', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (4, '粉玫瑰', 15, 5.00, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.GrGl9CW8UzwrJYQkcPCRTwHaFV?rs=1&pid=ImgDetMain', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (5, '【落入星河】碎冰蓝玫瑰', 15, 56.00, 'https://upyun.dinghuale.com/uploads/20230216/202302161328231451.jpg', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (6, '【恋爱皇冠】红玫瑰花束', 15, 66.00, 'https://img.alicdn.com/bao/uploaded/i3/2445977191/O1CN01pEDgNN22zWo0CCAzt_!!2445977191.jpg', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (15, '【粉色物语】粉玫瑰混搭', 15, 30.00, 'https://bpic.588ku.com/back_origin_min_pic/20/11/06/50ff8a691ba74a8e9f79db8b41b3724b.jpg', '粉色浪漫~', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (53, '【莫奈花园】康乃馨混搭雪山玫瑰', 11, 38.00, 'https://tse4-mm.cn.bing.net/th/id/OIP-C.V6PKKIzGEfCm0iqqIG5rtAHaHa?rs=1&pid=ImgDetMain', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (54, '粉色康乃馨', 11, 3.00, 'https://ts1.tc.mm.bing.net/th/id/R-C.db4463dbf763f31986ac265734c7b40f?rik=jLlafiUARa1kDA&riu=http%3a%2f%2ftgi12.jia.com%2f119%2f391%2f19391296.png&ehk=fHumTtFfBzJ7vKdk%2fjGu7Qu4loUEfL2eww7X5JCmwx4%3d&risl=&pid=ImgRaw&r=0', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (55, '白色康乃馨', 11, 3.00, 'https://www.pycu.cn/zb_users/upload/2023/12/20231230111433_30627.jpeg', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (56, '红色康乃馨', 11, 3.00, 'https://tse3-mm.cn.bing.net/th/id/OIP-C.BmT5CU9Xqs0Uw58k9iP7ewHaFj?rs=1&pid=ImgDetMain', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (57, '【无私的爱】母亲节鲜花13支红色康乃馨', 11, 18.00, 'https://www.lvbad.com/uploads/allimg/202102/1614158140677613.jpg', '母亲节必送！', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (58, '70根棒棒糖满天星', 18, 98.00, 'https://img.alicdn.com/i1/845678775/O1CN01NGtQWw2Eh0D3rZcvM_!!845678775.jpg', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (59, '10瓶益力多配粉色满天星', 18, 35.00, 'https://imgs.rednet.cn/data/24/IMAGE_TENANT_LIB/IMAGE/508/2022/5/19/80d57d99d7c04517985813697c691839.jpg', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (60, '零食6包随机配满天星', 18, 58.00, 'https://img1.sixflower.com/202342014185356217.jpg', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (61, '11支红玫瑰搭配9颗巧克力', 18, 66.00, 'https://img1.sixflower.com/2019122611452523878.jpg', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (62, '【六一儿童节】粉色系零食花束', 18, 88.00, 'https://img.alicdn.com/i1/1041848210/O1CN01LYdT3R2AWEXhNqyIK_!!1041848210.jpg', '送给孩子的礼物~', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (63, '【满目星河皆是你】白玫瑰混搭', 16, 88.00, 'https://oss.huawa.com/shop/placeorder/07110386289366621.jpg', '浪漫情调~', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (64, '百合花', 19, 5.00, 'https://p1.ssl.qhimg.com/t01ddcd3bf07fa80dbf.jpg', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (65, '紫色郁金香', 17, 12.00, 'https://img.shetu66.com/2022/12/06/1670280980010906.jpg', '高雅', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (66, '粉色郁金香', 17, 15.00, 'https://cbu01.alicdn.com/img/ibank/2018/150/493/9275394051_1114213732.jpg', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (67, '【花开富贵】粉色牡丹花', 21, 50.00, 'https://img.huabaike.com/uploads/allimg/20120405/120405113H3540200-L.png', '花开富贵！', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (68, '红色牡丹', 21, 42.00, 'https://img.pconline.com.cn/images/upload/upc/tx/itbbs/1504/13/c54/5339316_1428913987696.jpg', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (69, '五支装白兰花', 20, 56.00, 'https://ts1.tc.mm.bing.net/th/id/R-C.ce45a0115e5c8aa9ce137096a59958e5?rik=CyuI7QiDkuv3uw&riu=http%3a%2f%2fpic.baike.soso.com%2fp%2f20131221%2f20131221044405-163897828.jpg&ehk=vErCZG0B%2bHke8CsaG7tMP%2fjW04jChmSIXHvh8hUjRV0%3d&risl=&pid=ImgRaw&r=0', '清香型', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (70, '【圣诞小熊】玩偶花束', 12, 108.00, 'https://cbu01.alicdn.com/img/ibank/O1CN01rByPAX1bYyIrFH03X_!!2739203478-0-cib.jpg', '圣诞节送礼必备！', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (71, '【生日送朋友送闺蜜】可爱玩偶花束', 12, 102.00, 'https://cbu01.alicdn.com/img/ibank/O1CN01kEyTIJ1EzmNjhlggK_!!938770423-0-cib.jpg', '生日送朋友送闺蜜送孩子', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);
INSERT INTO `flower` VALUES (72, '【粉色物语】粉玫瑰混搭~', 16, 30.00, 'https://bpic.588ku.com/back_origin_min_pic/20/11/06/50ff8a691ba74a8e9f79db8b41b3724b.jpg', '无', 1, '2025-04-10 09:46:02', '2025-04-10 09:46:02', 1, 1);

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名字',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `flower_id` bigint NULL DEFAULT NULL COMMENT '菜品id',
  `number` int NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1, '艾丽丝', 1, 62, 1, 88.00);
INSERT INTO `order_detail` VALUES (2, '艾丽丝', 2, 5, 1, 56.00);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单号',
  `status` int NULL DEFAULT 1 COMMENT '订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款',
  `user_id` bigint NULL DEFAULT NULL COMMENT '下单用户',
  `address_book_id` bigint NULL DEFAULT NULL COMMENT '地址id',
  `order_time` datetime NULL DEFAULT NULL COMMENT '下单时间',
  `checkout_time` datetime NULL DEFAULT NULL COMMENT '结账时间',
  `pay_method` int NULL DEFAULT 1 COMMENT '支付方式 1微信,2支付宝',
  `pay_status` tinyint NULL DEFAULT 0 COMMENT '支付状态 0未支付 1已支付 2退款',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '实收金额',
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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '123456789', 1, 7, NULL, '2025-04-08 21:56:47', NULL, 1, 1, 10.00, NULL, '134500', NULL, 'cuiyuestar', NULL, NULL, NULL, NULL, NULL, 1, '2025-04-08 21:56:47', NULL, NULL, 1);
INSERT INTO `orders` VALUES (2, '123456789', 1, 7, NULL, '2025-04-12 21:56:47', NULL, 1, 1, 18.00, NULL, NULL, NULL, 'cuiyuestar', NULL, NULL, NULL, NULL, NULL, 1, '2025-04-08 21:56:47', NULL, NULL, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of participation
-- ----------------------------

-- ----------------------------
-- Table structure for seckill_voucher
-- ----------------------------
DROP TABLE IF EXISTS `seckill_voucher`;
CREATE TABLE `seckill_voucher`  (
  `voucher_id` bigint UNSIGNED NOT NULL COMMENT '关联的优惠券的id',
  `stock` int NOT NULL COMMENT '库存',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `begin_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生效时间',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '失效时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`voucher_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '秒杀优惠券表，与优惠券是一对一关系' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of seckill_voucher
-- ----------------------------
INSERT INTO `seckill_voucher` VALUES (10, 99, '2024-11-08 17:14:21', '2022-01-25 10:09:17', '2024-11-12 12:09:04', '2024-11-09 19:57:15');
INSERT INTO `seckill_voucher` VALUES (11, 199, '2024-11-10 12:55:09', '2022-01-25 10:09:17', '2024-11-20 12:09:04', '2024-11-10 16:53:39');

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
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '微信用户唯一标识',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号',
  `sex` varchar(5) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime NULL DEFAULT NULL,
  `username` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `password` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (4, 'oe29Y7Dl9KAKB8YReJz6KRvKkCzY', '五河琴里', '13333313133', '女', '111111111111111111', NULL, '2025-02-10 10:33:49', NULL, NULL);
INSERT INTO `user` VALUES (5, NULL, '五河琴里', '13333313133', '女', '111111111111111111', NULL, '2025-04-10 15:46:06', NULL, NULL);
INSERT INTO `user` VALUES (6, NULL, '时崎狂三', '13333313133', '女', '111111111111111111', NULL, '2025-04-10 15:48:23', 'cuiyue', '114514');
INSERT INTO `user` VALUES (7, NULL, '艾丽丝', '13954478466', '女', '12345678', NULL, NULL, 'cuiyuestar', '123456');
INSERT INTO `user` VALUES (8, NULL, '艾丽丝', '13131313131', '女', '123131213132123', NULL, NULL, '111', '114514');

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
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_comment
-- ----------------------------
INSERT INTO `user_comment` VALUES (8, 1, 7);

-- ----------------------------
-- Table structure for voucher
-- ----------------------------
DROP TABLE IF EXISTS `voucher`;
CREATE TABLE `voucher`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `shop_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '商铺id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '代金券标题',
  `sub_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '副标题',
  `rules` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '使用规则',
  `pay_value` bigint UNSIGNED NOT NULL COMMENT '支付金额，单位是分。例如200代表2元',
  `actual_value` bigint NOT NULL COMMENT '抵扣金额，单位是分。例如200代表2元',
  `type` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '0,普通券；1,秒杀券',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '1,上架; 2,下架; 3,过期',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of voucher
-- ----------------------------
INSERT INTO `voucher` VALUES (1, 1, '50元代金券', '周一至周日均可使用', '全场通用\\n无需预约\\n可无限叠加\\不兑现、不找零\\n仅限堂食', 4750, 5000, 0, 1, '2022-01-04 09:42:39', '2022-01-04 09:43:31');
INSERT INTO `voucher` VALUES (10, 1, '100元代金卷', '周一至周五均可使用', '全场通用\\n无需预约\\n可无限叠加\\不兑现，不找零\\n仅限堂食', 8000, 10000, 1, 1, '2024-11-08 17:14:21', '2024-11-08 17:14:21');
INSERT INTO `voucher` VALUES (11, 1, '100元代金卷', '周一至周五均可使用', '全场通用\\n无需预约\\n可无限叠加\\不兑现，不找零\\n仅限堂食', 8000, 10000, 1, 1, '2024-11-10 12:55:09', '2024-11-10 12:55:09');

-- ----------------------------
-- Table structure for voucher_order
-- ----------------------------
DROP TABLE IF EXISTS `voucher_order`;
CREATE TABLE `voucher_order`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '下单的用户id',
  `voucher_id` bigint UNSIGNED NOT NULL COMMENT '购买的代金券id',
  `pay_type` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '支付方式 1：余额支付；2：支付宝；3：微信',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '订单状态，1：未支付；2：已支付；3：已核销；4：已取消；5：退款中；6：已退款',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `use_time` timestamp NULL DEFAULT NULL COMMENT '核销时间',
  `refund_time` timestamp NULL DEFAULT NULL COMMENT '退款时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of voucher_order
-- ----------------------------
INSERT INTO `voucher_order` VALUES (387674133367881732, 1014, 11, 1, 1, '2024-11-10 16:53:39', NULL, NULL, NULL, '2024-11-10 16:53:39');

SET FOREIGN_KEY_CHECKS = 1;
