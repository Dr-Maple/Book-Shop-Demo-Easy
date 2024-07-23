/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : book-shop

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 02/04/2024 15:30:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `receiver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tel` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `flag_default` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `customer_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `customer_id`(`customer_id`) USING BTREE,
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (10, '河北省石家庄市长安区', '张三', '18722268888', '0', '11111111111');
INSERT INTO `address` VALUES (20, '内蒙古自治区呼和浩特市新城区让他吞吞吐吐', '麻子', '15151515151', '0', '11111111111');
INSERT INTO `address` VALUES (25, '河北省石家庄市长安区测试', '海绵宝宝', '11223344556', '1', '999');
INSERT INTO `address` VALUES (26, '辽宁省沈阳市和平区测试2', '派大星', '16511165526', '0', '999');
INSERT INTO `address` VALUES (27, '河北省石家庄市长安区订单的', '小王子', '12345678910', '0', 'aa');
INSERT INTO `address` VALUES (28, '河北省石家庄市长安区ffffff', '小王子', '11111111111', '1', 'www');
INSERT INTO `address` VALUES (29, '山西省太原市小店区11111', '小王子', '11111111111', '0', 'rrr');
INSERT INTO `address` VALUES (30, '内蒙古自治区呼和浩特市新城区111114444', '小王子', '12121212121', '1', 'rrr');
INSERT INTO `address` VALUES (32, '内蒙古自治区呼和浩特市新城区111111', 'kkkk', '11111111111', '1', 'kkk');
INSERT INTO `address` VALUES (33, '河北省石家庄市长安区11111', '小王子', '11111111111', '0', '11111111111');
INSERT INTO `address` VALUES (34, '山西省太原市小店区1111111', '小王子', '12121212121', '1', '11111111111');

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `profile_photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', 'admin', NULL);
INSERT INTO `admin` VALUES ('11111111111', 'anonymous', '111', '1512985092194.jpg');
INSERT INTO `admin` VALUES ('22', '22', '22', NULL);
INSERT INTO `admin` VALUES ('222', '222', '222', NULL);
INSERT INTO `admin` VALUES ('22222', '22222', '22222', NULL);
INSERT INTO `admin` VALUES ('555', '555', '555', NULL);
INSERT INTO `admin` VALUES ('8888', '8888', '8888', NULL);

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `old_price` decimal(10, 2) NULL DEFAULT NULL,
  `new_price` decimal(10, 2) NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  `type` int(0) NULL DEFAULT NULL,
  `inventory` int(0) NOT NULL,
  `sold` int(0) NOT NULL,
  `state` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `type`(`type`) USING BTREE,
  CONSTRAINT `books_ibfk_1` FOREIGN KEY (`type`) REFERENCES `booktype` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO `books` VALUES (1, '图书', 'bb', 12.00, 11.00, NULL, '001.jpg', '2022-05-09 17:41:28', 3, 100, 0, 1);
INSERT INTO `books` VALUES (2, 'd', 's', 14.00, 10.00, NULL, '58f5c73e6c95e.jpg', '2022-05-03 17:41:31', 7, 94, 6, 0);
INSERT INTO `books` VALUES (3, 'a', 'a', 20.00, 1.00, NULL, 't01afa7f754feca54ac.jpg', '2022-05-01 17:41:35', 2, 160, 0, 1);
INSERT INTO `books` VALUES (4, 'w', 'w', 30.00, 25.00, NULL, '120723105.jpg', '2022-05-06 17:41:41', 7, 600, 0, 1);
INSERT INTO `books` VALUES (5, 'dddd', 'zzzz', 99.00, 77.00, NULL, '151298506038.jpg', '2022-04-05 17:41:45', 9, 800, 0, 0);
INSERT INTO `books` VALUES (6, '12', '12', 15.00, 12.00, '12', 't0167d8a3175e7e27b1.jpg', '2022-05-17 00:00:00', 6, 100, 0, 0);
INSERT INTO `books` VALUES (7, '床头灯英语·1000词读物（英汉对照）：小飞侠彼得·潘', '[英] J·M·马利 著，李斐媛，贺紫燕 译', 20.00, 10.00, '', '27938338-1_w_1.jpg', '2019-11-01 00:00:00', 2, 183, 5, 0);
INSERT INTO `books` VALUES (8, '小王子', '(法) 圣埃克苏佩里、郑克鲁译', 26.00, 25.00, NULL, '25289386-1_u_24.jpg', '2018-07-23 15:35:16', 1, 466, 34, 0);
INSERT INTO `books` VALUES (9, 'sss', 'sss', 0.00, 12.00, NULL, 't01bb6eb1460b672b1e.jpg', '2022-05-02 00:00:00', 3, 123, 0, 0);
INSERT INTO `books` VALUES (10, 'kkk', 'kkk', 0.00, 12.00, NULL, 't0197830de01b844ba7.jpg', '2022-05-10 00:00:00', 4, 452, 0, 0);
INSERT INTO `books` VALUES (11, 'lll', 'ssss', 0.00, 12.00, NULL, 't0167d8a3175e7e27b1.jpg', '2022-05-03 00:00:00', 3, 500, 0, 0);
INSERT INTO `books` VALUES (12, 'kkk', 'kkkk', 0.00, 21.00, NULL, '151298506038.jpg', '2022-05-05 00:00:00', 5, 2000, 0, 0);

-- ----------------------------
-- Table structure for bookstate
-- ----------------------------
DROP TABLE IF EXISTS `bookstate`;
CREATE TABLE `bookstate`  (
  `id` int(0) NOT NULL,
  `book_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bookstate
-- ----------------------------
INSERT INTO `bookstate` VALUES (0, '在售');
INSERT INTO `bookstate` VALUES (1, '已下架');

-- ----------------------------
-- Table structure for booktype
-- ----------------------------
DROP TABLE IF EXISTS `booktype`;
CREATE TABLE `booktype`  (
  `id` int(0) NOT NULL,
  `booktype` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of booktype
-- ----------------------------
INSERT INTO `booktype` VALUES (0, '文学类');
INSERT INTO `booktype` VALUES (1, '童书');
INSERT INTO `booktype` VALUES (2, '教育类');
INSERT INTO `booktype` VALUES (3, '考试类');
INSERT INTO `booktype` VALUES (4, '人文社科类');
INSERT INTO `booktype` VALUES (5, '经管综合类');
INSERT INTO `booktype` VALUES (6, '生活类');
INSERT INTO `booktype` VALUES (7, '艺术类');
INSERT INTO `booktype` VALUES (8, '科技类');
INSERT INTO `booktype` VALUES (9, '其他');

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `book_id` int(0) NOT NULL,
  `customer_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `num` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `book_id`(`book_id`) USING BTREE,
  INDEX `customer_id`(`customer_id`) USING BTREE,
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (14, 3, '11111111111', 3);
INSERT INTO `cart` VALUES (16, 8, '22', 3);
INSERT INTO `cart` VALUES (17, 8, '333', 3);
INSERT INTO `cart` VALUES (18, 7, '333', 1);
INSERT INTO `cart` VALUES (19, 8, '666', 1);
INSERT INTO `cart` VALUES (20, 8, '777', 1);
INSERT INTO `cart` VALUES (22, 8, 'aa', 3);
INSERT INTO `cart` VALUES (25, 8, 'www', 4);
INSERT INTO `cart` VALUES (26, 7, 'yyy', 1);
INSERT INTO `cart` VALUES (27, 8, 'yyy', 7);
INSERT INTO `cart` VALUES (28, 7, 'rrr', 1);
INSERT INTO `cart` VALUES (30, 7, 'ppp', 1);
INSERT INTO `cart` VALUES (31, 8, 'ppp', 5);
INSERT INTO `cart` VALUES (32, 7, 'ooo', 1);
INSERT INTO `cart` VALUES (33, 8, 'ooo', 4);
INSERT INTO `cart` VALUES (36, 8, 'kkk', 6);
INSERT INTO `cart` VALUES (40, 8, '11111111111', 3);

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tel` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `profile_photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `member_date` datetime(0) NULL DEFAULT NULL,
  `member` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `birthday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('11111111111', 'anonymous', '2459862046@qq.com', '222', '18722267680', '1512985092194.jpg', NULL, '2022-05-28 22:15:27', 'true', 'woman', '1999-03-02');
INSERT INTO `customer` VALUES ('11111111112', '默认n', NULL, '111', NULL, 'defaultPhoto.jpg', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `customer` VALUES ('22', 'test', NULL, '2222', NULL, 'defaultPhoto.jpg', NULL, NULL, NULL, 'woman', '2022-05-04');
INSERT INTO `customer` VALUES ('333', 'test', NULL, '333', '11111111111', 'defaultPhoto.jpg', NULL, NULL, NULL, 'woman', '2021-07-06');
INSERT INTO `customer` VALUES ('666', '666', NULL, '666', NULL, 'defaultPhoto.jpg', NULL, NULL, NULL, 'woman', '2022-05-02');
INSERT INTO `customer` VALUES ('777', '777', NULL, '777', '11111111111', 't01088ba01925655a4a.jpg', NULL, NULL, NULL, 'woman', '2020-06-16');
INSERT INTO `customer` VALUES ('888', 'test', NULL, '888', NULL, 'defaultPhoto.jpg', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `customer` VALUES ('999', '派大星', NULL, '999', '12312312111', 't01088ba01925655a4a.jpg', NULL, '2022-05-29 13:04:33', 'true', 'man', '2022-05-09T16:00:00.000Z');
INSERT INTO `customer` VALUES ('aa', 'aa', NULL, 'sss', '11111111111', 't01088ba01925655a4a.jpg', NULL, '2022-05-29 22:27:03', 'true', 'woman', '2022-05-02');
INSERT INTO `customer` VALUES ('kkk', 'kkk', NULL, 'kkkk', '11111111111', 't01088ba01925655a4a.jpg', NULL, '2022-05-31 15:23:21', 'true', 'woman', '2022-05-02');
INSERT INTO `customer` VALUES ('ooo', 'ooo', NULL, 'ooo', NULL, 'defaultPhoto.jpg', NULL, NULL, NULL, 'woman', '2020-10-20');
INSERT INTO `customer` VALUES ('ppp', 'ppp', NULL, 'ppp', NULL, 'defaultPhoto.jpg', NULL, NULL, NULL, 'woman', '2022-05-02');
INSERT INTO `customer` VALUES ('rrr', 'rrr', NULL, 'rrr', '11111111111', 'QQ图片20180822082808.png', NULL, '2022-05-31 10:56:07', 'true', 'woman', '2022-05-02');
INSERT INTO `customer` VALUES ('www', 'www', NULL, '111', '12121121211', 't01088ba01925655a4a.jpg', NULL, '2022-05-30 22:49:17', 'true', 'woman', '2022-05-03');
INSERT INTO `customer` VALUES ('yyy', 'yyy', NULL, 'yyy', '11111111111', 'defaultPhoto.jpg', NULL, NULL, NULL, 'woman', '2020-07-09');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `book_id` int(0) NOT NULL,
  `customer_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `receiver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tel` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `num` int(0) NOT NULL,
  `single_price` decimal(10, 2) NOT NULL,
  `state` int(0) NOT NULL,
  `date` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `end_date` datetime(0) NULL DEFAULT NULL,
  `total_price` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `book_id`(`book_id`) USING BTREE,
  INDEX `customer_id`(`customer_id`) USING BTREE,
  INDEX `state`(`state`) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`state`) REFERENCES `ordersstate` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (57, 4, '11111111111', '天津天津市西青区宾水西道391号天津理工大学', '倪总', '12121212121', 2, 25.00, 0, '2022-05-29 17:57:25', '2022-05-31 03:23:58', 50.00);
INSERT INTO `orders` VALUES (60, 2, '11111111111', '天津天津市西青区宾水西道391号', '倪总', '12121212121', 4, 10.00, 0, '2022-05-29 17:58:44', '2022-05-29 17:58:36', 40.00);
INSERT INTO `orders` VALUES (61, 6, '11111111111', '黑龙江省哈尔滨市道里区看看', 'uu', '222', 1, 12.00, 2, '2022-05-29 17:57:33', '2022-05-29 05:57:50', 12.00);
INSERT INTO `orders` VALUES (63, 8, '999', '河北省石家庄市长安区测试', '海绵宝宝', '11223344556', 1, 25.00, 3, '2022-05-29 14:58:46', '2022-05-29 14:58:41', 23.75);
INSERT INTO `orders` VALUES (64, 2, '11111111111', '黑龙江省哈尔滨市道里区', '所示', '搜索', 3, 10.00, 2, '2022-05-29 15:52:57', '2022-05-31 09:52:14', 28.50);
INSERT INTO `orders` VALUES (66, 7, '11111111111', '黑龙江省哈尔滨市道里区', '所示', '搜索', 1, 10.00, 2, '2022-05-29 17:51:58', '2022-05-29 05:45:22', 9.00);
INSERT INTO `orders` VALUES (67, 8, '333', '天津', '小王子', '12121212121', 3, 25.00, 3, '2022-05-29 21:43:23', NULL, 75.00);
INSERT INTO `orders` VALUES (68, 8, '777', '天津', 'ha', '12121212121', 4, 25.00, 2, '2022-05-29 22:13:41', NULL, 100.00);
INSERT INTO `orders` VALUES (69, 8, 'aa', '天津', '小派大星', '12121212121', 1, 25.00, 2, '2022-05-29 22:26:19', NULL, 25.00);
INSERT INTO `orders` VALUES (70, 7, 'aa', '爱爱爱', 'tesy', '11111111111', 1, 10.20, 2, '2022-05-29 22:27:35', NULL, 9.69);
INSERT INTO `orders` VALUES (71, 7, 'www', '河北省石家庄市长安区ffffff', '小王子', '11111111111', 1, 10.20, 2, '2022-05-30 22:49:34', NULL, 9.69);
INSERT INTO `orders` VALUES (72, 8, 'yyy', 'hhhhhh', 'ttt', '12121212121', 7, 25.00, 2, '2022-05-30 23:07:08', NULL, 175.00);
INSERT INTO `orders` VALUES (73, 7, '11111111111', '黑龙江省哈尔滨市道里区的点点滴滴', '王五', '11144477788', 1, 10.20, 2, '2022-05-30 23:14:30', NULL, 9.69);
INSERT INTO `orders` VALUES (74, 8, 'rrr', '山西省太原市小店区11111', '小王子', '11111111111', 5, 25.00, 2, '2022-05-31 10:56:23', NULL, 118.75);
INSERT INTO `orders` VALUES (75, 8, 'kkk', '111', 'kkk', '11111111111', 6, 25.00, 3, '2022-05-31 15:22:07', NULL, 150.00);
INSERT INTO `orders` VALUES (76, 7, 'kkk', '内蒙古自治区呼和浩特市新城区111111', 'kkkk', '11111111111', 1, 10.00, 2, '2022-05-31 15:23:33', NULL, 9.50);
INSERT INTO `orders` VALUES (77, 8, '11111111111', '山西省太原市小店区测试', '李四', '11122233345', 4, 25.00, 2, '2022-05-31 21:46:14', NULL, 95.00);
INSERT INTO `orders` VALUES (78, 8, '11111111111', '河北省石家庄市长安区', '张三', '18722268888', 3, 25.00, 2, '2022-05-31 21:51:49', NULL, 71.25);

-- ----------------------------
-- Table structure for ordersstate
-- ----------------------------
DROP TABLE IF EXISTS `ordersstate`;
CREATE TABLE `ordersstate`  (
  `id` int(0) NOT NULL,
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ordersstate
-- ----------------------------
INSERT INTO `ordersstate` VALUES (0, '已完成');
INSERT INTO `ordersstate` VALUES (1, '待付款');
INSERT INTO `ordersstate` VALUES (2, '待发货');
INSERT INTO `ordersstate` VALUES (3, '待收货');

SET FOREIGN_KEY_CHECKS = 1;
