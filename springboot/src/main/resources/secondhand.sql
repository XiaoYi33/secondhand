/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : secondhand

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 19/05/2024 17:27:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `category_name only one`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '目录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (4, '书籍');
INSERT INTO `category` VALUES (3, '家具');
INSERT INTO `category` VALUES (1, '数码');
INSERT INTO `category` VALUES (2, '电器');
INSERT INTO `category` VALUES (5, '零食');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `description` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品描述',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片',
  `category_id` int(0) NOT NULL COMMENT '1数码，2电器，3家具，4书籍，5零食',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `quantity` int(0) NOT NULL DEFAULT 1 COMMENT '数量',
  `user_id` int(0) NOT NULL COMMENT 'user表的id',
  `state` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '上架' COMMENT '0：发布\n1：下架\n2：成交\n',
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'now()' COMMENT '创建时间',
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下架原因',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 238 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (138, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 44, '上架', '2024-03-03 02:13:12', NULL, NULL);
INSERT INTO `product` VALUES (139, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 44, '上架', '2024-03-03 02:13:12', NULL, NULL);
INSERT INTO `product` VALUES (140, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 44, '上架', '2024-03-03 02:13:12', NULL, NULL);
INSERT INTO `product` VALUES (141, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 2, 123.00, 1, 44, '上架', '2024-03-03 02:13:12', NULL, NULL);
INSERT INTO `product` VALUES (142, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 2, 123.00, 1, 44, '上架', '2024-03-03 02:13:12', NULL, NULL);
INSERT INTO `product` VALUES (143, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 2, 123.00, 1, 44, '上架', '2024-03-03 02:13:12', NULL, NULL);
INSERT INTO `product` VALUES (144, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 2, 123.00, 1, 44, '上架', '2024-03-03 02:13:12', NULL, NULL);
INSERT INTO `product` VALUES (145, '托尔斯泰', '托尔斯泰', 'http://localhost:9090/file/download/1709477181383_123.jpg', 2, 123.00, 1, 44, '下架', '2024-03-03 02:13:12', '2024-03-03 22:16:46', '卖出下架');
INSERT INTO `product` VALUES (146, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 2, 123.00, 1, 44, '上架', '2024-03-03 02:13:12', NULL, NULL);
INSERT INTO `product` VALUES (148, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 44, '下架', '2024-03-03 22:46:23', '2024-03-04 18:10:39', '卖出下架');
INSERT INTO `product` VALUES (149, '123', '托尔斯泰', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (150, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:15', NULL);
INSERT INTO `product` VALUES (151, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:15', NULL);
INSERT INTO `product` VALUES (152, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:15', NULL);
INSERT INTO `product` VALUES (153, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:15', NULL);
INSERT INTO `product` VALUES (154, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:15', NULL);
INSERT INTO `product` VALUES (155, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 1.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (156, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (157, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (158, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (159, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (160, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (161, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (162, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (163, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (164, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (165, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (166, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (167, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (168, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (169, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (170, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (171, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (172, '第二页', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:16', NULL);
INSERT INTO `product` VALUES (173, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (174, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (175, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (176, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (177, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (178, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 4, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (179, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (180, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 4, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (181, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (182, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 4, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (183, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (184, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (185, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 4, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (186, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (187, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (188, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 5, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (189, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 5, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (190, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (191, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (192, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 5, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (193, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (194, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (195, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:19', NULL);
INSERT INTO `product` VALUES (196, '第三页', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 5, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:38', NULL);
INSERT INTO `product` VALUES (197, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:38', NULL);
INSERT INTO `product` VALUES (198, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:38', NULL);
INSERT INTO `product` VALUES (199, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:38', NULL);
INSERT INTO `product` VALUES (200, '123', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '上架', '2024-03-03 02:13:13', '2024-05-17 17:39:38', NULL);
INSERT INTO `product` VALUES (202, '测试商品', '测试商品', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '下架', '2024-03-03 02:13:13', '2024-03-25 22:57:03', '卖出下架');
INSERT INTO `product` VALUES (203, 'test', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '下架', '2024-03-03 02:13:13', '2024-03-24 15:32:02', '卖出下架');
INSERT INTO `product` VALUES (204, 'test', '123', 'http://localhost:9090/file/download/1709477181383_123.jpg', 1, 123.00, 1, 1, '下架', '2024-03-03 02:13:13', '2024-03-06 00:50:59', '不合法');
INSERT INTO `product` VALUES (206, 'VGN N75Pro键盘', '三模连接机械键盘，蒸汽波pro轴体，MDA高度键帽。一年前京东原价199购入，现闲置出售。', 'http://localhost:9090/file/download/img_20240320_091210007.jpg', 1, 99.99, 1, 55, '下架', '2024-03-25 21:52:16', '2024-03-25 22:55:27', '卖出下架');
INSERT INTO `product` VALUES (208, 'JJK84三模键盘', '三模热插拔机械键盘，支持五脚轴体，白色灯光。两年前购于淘宝，现99甩卖！！！', 'http://localhost:9090/file/download/img_20240223_183022394.jpg', 1, 999.99, 1, 55, '下架', '2024-03-25 23:39:07', '2024-03-28 14:37:24', '卖出下架');
INSERT INTO `product` VALUES (211, '狼蛛灰木轴v4', '有70颗', 'http://localhost:9090/file/download/P20240223-211618.jpg', 1, 30.00, 1, 55, '下架', '2024-03-28 14:39:04', '2024-03-28 14:44:29', '卖出下架');
INSERT INTO `product` VALUES (212, '高达主题原厂键帽', '热升华pbt材质，不缺键，基本没怎么用过', 'http://localhost:9090/file/download/img_20240223_191918500.jpg', 1, 30.00, 1, 55, '下架', '2024-03-28 14:42:12', '2024-03-28 14:44:23', '卖出下架');
INSERT INTO `product` VALUES (213, '机革命CODE01笔记', '购于咸鱼，屏幕老化，键盘损坏，32G内存，2T固态。', 'http://localhost:9090/file/download/PixPin_2024-03-28_22-15-42.png', 1, 1499.00, 1, 55, '下架', '2024-03-28 22:16:48', '2024-04-05 23:13:15', '卖出下架');
INSERT INTO `product` VALUES (215, '兵马俑', '位于西安秦始皇兵马俑博物馆，需要自提', 'http://localhost:9090/file/download/f7b1eba5-4ed1-43a4-a540-3b14dccd148b.jpg', 1, 9999999.99, 1, 55, '上架', '2024-04-08 23:13:19', '2024-04-12 14:54:18', '');
INSERT INTO `product` VALUES (216, '新盟X75透明键盘', '首发239购入，单模RGB，gasket结构，凯华水母轴，轴座热插拔，全透键帽和外壳。', 'http://localhost:9090/file/download/89923d2e-5cfa-4f82-a16c-979913921dcf.jpg', 1, 139.00, 1, 55, '上架', '2024-04-08 23:18:54', NULL, NULL);
INSERT INTO `product` VALUES (217, 'RK R98三模机械键盘', '23年双11购入，烟雨轴。三模gasket热插拔RGB音乐律动MDA键帽。烟雨轴没有想象中优秀，故打折出售。', 'http://localhost:9090/file/download/57960a9d-d5ab-4417-bdf3-bdeefc51a98a.jpg', 1, 159.00, 1, 55, '上架', '2024-04-08 23:20:15', NULL, NULL);
INSERT INTO `product` VALUES (218, '班长的RK R75键盘', '路过他宿舍时看到，感觉很不错，就顺走了，现因没钱吃饭低价出售。\n', 'http://localhost:9090/file/download/75a71b70-c6a4-4224-a37e-3134c33e7b30.jpg', 1, 199.99, 1, 55, '上架', '2024-04-08 23:21:26', NULL, NULL);
INSERT INTO `product` VALUES (219, '比亚迪车钥匙', '应该是比亚迪的吗，路上捡的，不认识，送给有缘人', 'http://localhost:9090/file/download/09ed2858-225f-4156-8b31-105139cf0a7b.png', 2, 10.00, 1, 55, '下架', '2024-04-08 23:18:54', '2024-04-08 23:37:28', '用户下架');
INSERT INTO `product` VALUES (220, '比亚迪仰望U8', '车钥匙掉了开不了，车低价甩了，不想折腾', 'http://localhost:9090/file/download/662048b2-a95d-4195-beb9-5d3da1cc4a5d.jpg', 2, 8888888.00, 1, 55, '下架', '2024-04-08 23:18:54', '2024-04-08 23:37:31', '用户下架');
INSERT INTO `product` VALUES (221, '黑爵AKL680键盘', '买来玩，无线，矮轴，60多入手的', 'http://localhost:9090/file/download/59bc90aa-97fa-4f40-8e3e-afaee7ea85fd.jpg', 1, 50.00, 1, 55, '上架', '2024-04-08 23:29:19', NULL, NULL);
INSERT INTO `product` VALUES (222, 'VGN V75x', '半铝键盘，该有的都有，这个价格还划得来', 'http://localhost:9090/file/download/4dadab41-7941-484a-8f2d-caff0557475f.jpg', 1, 199.00, 1, 55, '上架', '2024-04-08 23:30:04', NULL, NULL);
INSERT INTO `product` VALUES (223, '黑爵AK992', '最便宜98配列gasket结构三模键盘，便宜是便宜但是手感和声音都太辣了，不想要了', 'http://localhost:9090/file/download/8b80e8ae-d2b2-469b-ba9d-6095f3146a94.jpg', 1, 99.00, 1, 55, '上架', '2024-04-08 23:30:51', NULL, NULL);
INSERT INTO `product` VALUES (224, 'LEOBOG HI8', '500元以内最强铝坨坨之一，换了fr4定位板，紫川银轴，qxa高度键帽，海丰最新模具卫星轴，主力键盘。现忍痛割爱', 'http://localhost:9090/file/download/f65e11fe-b149-447f-8dc4-6815d2e8353b.jpg', 1, 599.00, 1, 55, '上架', '2024-04-08 23:32:09', NULL, NULL);
INSERT INTO `product` VALUES (225, '瓦尔基里VK87', '二次元浓度够高，基本配置都有，还带个电容屏', 'http://localhost:9090/file/download/54636fa0-53cd-426c-8bbc-370f87dc4eec.jpg', 1, 199.00, 1, 55, '上架', '2024-04-08 23:33:19', NULL, NULL);
INSERT INTO `product` VALUES (226, 'Alice98', '稀有货色，闲鱼都没几个人卖。应该是最便宜的alice配列，只可惜是单模，套件素质非常不错。', 'http://localhost:9090/file/download/fee48f5c-e88a-49ae-887d-a3cd3a641696.jpg', 1, 299.00, 1, 55, '上架', '2024-04-08 23:34:14', NULL, NULL);
INSERT INTO `product` VALUES (227, 'iphone xs', 'iphone xs 256g  ', 'http://localhost:9090/file/download/a0e88c94-546e-4dce-a00a-b08e573b7ab5.png', 1, 1999.00, 1, 1, '上架', '2024-04-08 23:34:14', '2024-05-17 17:44:52', NULL);
INSERT INTO `product` VALUES (230, '床上桌', '床上桌1', 'http://localhost:9090/file/download/504e1a42-4c4c-40ef-981e-b278a57e0548.png', 3, 20.00, 1, 1, '上架', '2024-05-11 00:42:41', '2024-05-17 17:44:52', NULL);
INSERT INTO `product` VALUES (231, 'iphonexs', 'iphonexs', 'http://localhost:9090/file/download/bc9cdcec-0194-4b73-b10d-67520ac2f771.png', 1, 1500.00, 1, 1, '上架', '2024-05-11 00:44:45', '2024-05-17 17:44:52', NULL);
INSERT INTO `product` VALUES (232, '小狗一只', '小狗一只，可以搂着睡', 'http://localhost:9090/file/download/c01d1eb5-52fd-4f39-8121-4efffd399386.jpg', 3, 39.00, 1, 1, '上架', '2024-05-11 00:50:19', '2024-05-17 17:44:52', NULL);
INSERT INTO `product` VALUES (233, '不知道谁给我的芒果', '香甜可口芒果', 'http://localhost:9090/file/download/bbe67efa-3aed-4e45-8e4f-4545f7dd1f32.jpg', 5, 10.00, 1, 1, '上架', '2024-05-11 00:51:54', '2024-05-17 17:44:52', NULL);
INSERT INTO `product` VALUES (234, '海底捞脆脆角', '好吃的一', 'http://localhost:9090/file/download/04cccbc4-93d5-435f-b31e-d3865104259c.jpg', 5, 10.00, 1, 1, '上架', '2024-05-11 00:52:31', '2024-05-17 17:44:52', NULL);
INSERT INTO `product` VALUES (235, '隔壁宿舍的枕头', '去隔壁宿舍抢的枕头', 'http://localhost:9090/file/download/0282aea5-608d-4f82-a59a-609470870d7a.jpg', 3, 200.00, 1, 1, '上架', '2024-05-11 00:53:27', '2024-05-17 18:30:41', '');
INSERT INTO `product` VALUES (237, '鼠标', '旧鼠标', 'http://localhost:9090/file/download/4e2f8df7-be93-407c-8076-eed56039d9be.jpg', 1, 10.00, 1, 1, '下架', '2024-05-18 16:55:23', '2024-05-18 16:56:30', '卖出下架');

-- ----------------------------
-- Table structure for product_state
-- ----------------------------
DROP TABLE IF EXISTS `product_state`;
CREATE TABLE `product_state`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_state
-- ----------------------------
INSERT INTO `product_state` VALUES (1, '上架');
INSERT INTO `product_state` VALUES (2, '下架');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '管理员');
INSERT INTO `role` VALUES (2, '学生');
INSERT INTO `role` VALUES (3, '老师');

-- ----------------------------
-- Table structure for transaction
-- ----------------------------
DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `buyer_id` int(0) NOT NULL COMMENT '买家ID',
  `seller_id` int(0) NOT NULL COMMENT '卖家ID',
  `product_id` int(0) NOT NULL COMMENT '商品ID',
  `price` int(0) NOT NULL COMMENT '订单成交时金额',
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'now()' COMMENT '创建时间',
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单完成或取消时间',
  `state` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '待交易' COMMENT '待交易、待确认、已完成、已取消',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '等待卖家确认交易' COMMENT '备注',
  `is_showed_to_buyer` int(0) NOT NULL DEFAULT 1 COMMENT '逻辑删除字段，1为展示，0为不展示',
  `is_showed_to_seller` int(0) NOT NULL DEFAULT 1 COMMENT '逻辑删除字段，1为展示，0为不展示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of transaction
-- ----------------------------
INSERT INTO `transaction` VALUES ('2024030302194397541', 1, 1, 146, 123, '2024-03-03 02:19:44', NULL, '已取消', '买家取消了订单', 1, 1);
INSERT INTO `transaction` VALUES ('2024030322164595070', 44, 1, 145, 123, '2024-03-03 22:16:46', NULL, '已完成', '交易已完成', 0, 1);
INSERT INTO `transaction` VALUES ('2024030322164595071', 44, 1, 145, 123, '2024-03-03 22:16:46', '2024-03-06 23:24:58', '已取消', '卖家取消了订单', 1, 0);
INSERT INTO `transaction` VALUES ('2024030322164595072', 45, 1, 145, 123, '2024-03-03 22:16:46', NULL, '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024030322164595073', 45, 1, 145, 123, '2024-03-03 22:16:46', NULL, '已取消', '买家取消了订单', 0, 1);
INSERT INTO `transaction` VALUES ('2024030322164595074', 46, 1, 145, 123, '2024-03-03 22:16:46', NULL, '已取消', '买家取消了订单', 1, 1);
INSERT INTO `transaction` VALUES ('2024030322164595075', 46, 1, 145, 123, '2024-03-03 22:16:46', NULL, '已取消', '买家取消了订单', 1, 0);
INSERT INTO `transaction` VALUES ('2024030322164595076', 47, 1, 145, 123, '2024-03-03 22:16:46', NULL, '已取消', '买家取消了订单', 1, 0);
INSERT INTO `transaction` VALUES ('2024030322164595077', 47, 1, 145, 123, '2024-03-03 22:16:46', '2024-03-06 23:19:16', '待确认', '等待买家完成交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955078', 1, 44, 148, 123, '2024-03-04 18:10:39', '2024-03-06 23:06:12', '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955081', 1, 44, 148, 123, '2024-03-04 18:10:39', NULL, '待交易', '等待与卖家交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955082', 1, 44, 148, 123, '2024-03-04 18:10:39', NULL, '待交易', '等待与卖家交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955083', 1, 44, 148, 123, '2024-03-04 18:10:39', NULL, '待交易', '等待与卖家交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955084', 1, 44, 148, 123, '2024-03-04 18:10:39', NULL, '待交易', '等待与卖家交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955085', 1, 44, 148, 123, '2024-03-04 18:10:39', NULL, '待交易', '等待与卖家交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955086', 1, 44, 148, 123, '2024-03-04 18:10:39', NULL, '待交易', '等待与卖家交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955087', 1, 44, 148, 123, '2024-03-04 18:10:39', NULL, '待交易', '等待与卖家交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955088', 1, 44, 148, 123, '2024-03-04 18:10:39', NULL, '待交易', '等待与卖家交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955089', 1, 44, 148, 123, '2024-03-04 18:10:39', NULL, '待交易', '等待与卖家交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955090', 1, 44, 148, 123, '2024-03-04 18:10:39', NULL, '待交易', '等待与卖家交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955091', 1, 44, 148, 123, '2024-03-04 18:10:39', '2024-03-06 00:01:51', '已取消', '买家取消了订单', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955092', 1, 44, 148, 123, '2024-03-04 18:10:39', '2024-03-06 23:08:57', '已取消', '买家取消了订单', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955093', 1, 44, 148, 123, '2024-03-04 18:10:39', '2024-03-06 23:09:05', '已取消', '买家取消了订单', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955094', 1, 44, 145, 123, '2024-03-04 18:10:39', '2024-03-06 00:01:22', '已取消', '买家取消了订单', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955095', 1, 44, 145, 123, '2024-03-04 18:10:39', '2024-03-05 23:59:52', '已取消', '买家取消了订单', 0, 1);
INSERT INTO `transaction` VALUES ('2024030418103955096', 1, 44, 145, 123, '2024-03-04 18:10:39', '2024-03-05 23:52:56', '已取消', '买家取消了订单', 0, 1);
INSERT INTO `transaction` VALUES ('2024030418103955097', 1, 44, 145, 123, '2024-03-04 18:10:39', '2024-03-05 23:46:59', '已取消', '买家取消了订单', 0, 1);
INSERT INTO `transaction` VALUES ('2024030418103955098', 1, 44, 145, 123, '2024-03-04 18:10:39', '2024-03-05 02:30:49', '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955111', 44, 1, 148, 123, '2024-03-04 18:10:39', '2024-03-05 02:30:49', '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955112', 44, 1, 148, 123, '2024-03-04 18:10:39', '2024-03-05 02:30:49', '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955113', 45, 1, 148, 123, '2024-03-04 18:10:39', '2024-03-05 02:30:49', '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955114', 45, 1, 148, 123, '2024-03-04 18:10:39', '2024-03-05 02:30:49', '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955115', 44, 1, 148, 123, '2024-03-04 18:10:39', '2024-03-05 02:30:49', '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955116', 46, 1, 148, 123, '2024-03-04 18:10:39', '2024-03-05 02:30:49', '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955117', 46, 1, 148, 123, '2024-03-04 18:10:39', '2024-03-05 02:30:49', '已完成', '交易已完成', 1, 0);
INSERT INTO `transaction` VALUES ('2024030418103955118', 46, 1, 148, 123, '2024-03-04 18:10:39', '2024-03-05 02:30:49', '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955119', 47, 1, 148, 123, '2024-03-04 18:10:39', '2024-03-05 02:30:49', '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955120', 47, 1, 148, 123, '2024-03-04 18:10:39', '2024-03-05 02:30:49', '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024030418103955121', 47, 1, 148, 123, '2024-03-04 18:10:39', '2024-03-05 02:30:49', '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024032415320277268', 32, 1, 203, 123, '2024-03-24 15:32:00', '2024-03-24 15:36:26', '已完成', '交易已完成', 0, 1);
INSERT INTO `transaction` VALUES ('2024032522552629900', 32, 55, 206, 99, '2024-03-25 22:55:29', '2024-03-28 14:45:50', '已取消', '买家取消了订单', 1, 1);
INSERT INTO `transaction` VALUES ('2024032522570337742', 32, 1, 202, 123, '2024-03-25 22:57:05', '2024-03-26 13:22:55', '已取消', '买家取消了订单', 0, 1);
INSERT INTO `transaction` VALUES ('2024032814372440329', 32, 55, 208, 999, '2024-03-28 14:37:24', NULL, '待交易', '等待卖家确认交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024032814442348395', 32, 55, 212, 30, '2024-03-28 14:44:23', '', '待确认', '等待买家完成交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024032814442833417', 32, 55, 211, 30, '2024-03-28 14:44:29', '2024-03-28 14:45:56', '已完成', '交易已完成', 1, 1);
INSERT INTO `transaction` VALUES ('2024040523131520980', 1, 55, 213, 1499, '2024-04-05 23:13:15', NULL, '待交易', '等待卖家确认交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024040812121464485', 32, 1, 201, 123, '2024-04-08 12:12:09', NULL, '待交易', '等待卖家确认交易', 1, 1);
INSERT INTO `transaction` VALUES ('2024041214541857084', 1, 55, 215, 9999999, '2024-04-08 12:12:09', '2024-05-17 16:48:18', '已取消', '买家取消了订单', 1, 1);
INSERT INTO `transaction` VALUES ('2024051816563069141', 55, 1, 237, 10, 'now()', '2024-05-18 16:57:38', '已完成', '交易已完成', 1, 1);

-- ----------------------------
-- Table structure for transaction_state
-- ----------------------------
DROP TABLE IF EXISTS `transaction_state`;
CREATE TABLE `transaction_state`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of transaction_state
-- ----------------------------
INSERT INTO `transaction_state` VALUES (1, '待交易');
INSERT INTO `transaction_state` VALUES (2, '待确认');
INSERT INTO `transaction_state` VALUES (3, '已完成');
INSERT INTO `transaction_state` VALUES (4, '已取消');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '`username`' COMMENT '显示名',
  `role` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '学生',
  `wechat` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '微信',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username onlyone`(`username`) USING BTREE COMMENT '用户名唯一',
  UNIQUE INDEX `nickname only`(`nickname`) USING BTREE COMMENT '昵称唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'root', '202cb962ac59075b964b07152d234b70', 'root', '管理员', '123', '123', '31007@qq.com', 'http://localhost:9090/file/download/711219d2-3513-404f-af21-78ee9f06809e.png', '2024-02-12 18:14:46');
INSERT INTO `user` VALUES (32, 'ywy1', 'e10adc3949ba59abbe56e057f20f883e', 'ywy1', '学生', '4', '123', '2678089155@qq.com', 'http://localhost:9090/file/download/1711374832713_123.jpg', '2024-03-04 00:59:20');
INSERT INTO `user` VALUES (44, 'user2', '202cb962ac59075b964b07152d234b70', 'nickname2', '学生', 'wechat2', '1234567891', 'email2@example.com', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-03-03 02:12:46');
INSERT INTO `user` VALUES (45, 'user3', '202cb962ac59075b964b07152d234b70', 'nickname3', '学生', 'wechat3', '1234567892', 'email3@example.com', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-03-03 02:12:46');
INSERT INTO `user` VALUES (46, 'user4', '202cb962ac59075b964b07152d234b70', 'nickname4', '学生', 'wechat4', '1234567893', 'email4@example.com', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-03-03 02:12:46');
INSERT INTO `user` VALUES (47, 'user5', '202cb962ac59075b964b07152d234b70', 'nickname5', '学生', 'wechat5', '1234567894', 'email5@example.com', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-03-03 02:12:46');
INSERT INTO `user` VALUES (48, 'user6', '202cb962ac59075b964b07152d234b70', 'nickname6', '学生', 'wechat6', '1234567895', 'email6@example.com', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-03-03 02:12:46');
INSERT INTO `user` VALUES (49, 'user7', '202cb962ac59075b964b07152d234b70', 'nickname7', '学生', 'wechat7', '1234567896', 'email7@example.com', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-03-03 02:12:46');
INSERT INTO `user` VALUES (50, 'user8', '202cb962ac59075b964b07152d234b70', 'nickname8', '学生', 'wechat8', '1234567897', 'email8@example.com', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-03-03 02:12:46');
INSERT INTO `user` VALUES (51, 'user9', '202cb962ac59075b964b07152d234b70', 'nickname9', '学生', 'wechat9', '1234567898', 'email9@example.com', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-03-03 02:12:46');
INSERT INTO `user` VALUES (52, 'user10', 'e10adc3949ba59abbe56e057f20f883e', 'nickname10', '学生', 'wechat10', '1234567899', 'email10@example.com', 'http://localhost:9090/file/download/1709659434321_123.jpg', '2024-03-03 02:12:46');
INSERT INTO `user` VALUES (55, 'ywy', 'e10adc3949ba59abbe56e057f20f883e', 'ywy', '学生', 'ywy123', '123456789', '1303381555@qq.com', 'http://localhost:9090/file/download/41141f57-5d6b-450a-8794-456633cb4637.jpg', '2024-03-25 21:37:36');
INSERT INTO `user` VALUES (57, 'test1', '202cb962ac59075b964b07152d234b70', 'test', '学生', 'test', '123', '123@123.com', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-03-29 22:21:44');
INSERT INTO `user` VALUES (60, 'test123', 'e10adc3949ba59abbe56e057f20f883e', 'test123', '学生', '123', '123', '310091267@qq.com', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-03-31 13:36:45');
INSERT INTO `user` VALUES (61, 'test1234', 'e8909a29d2cb10989cb934ab8e7393b3', 'test1234', '学生', '123', '123', '1029807850@qq.com', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-03-31 14:02:14');
INSERT INTO `user` VALUES (62, 'ywyw11', '46fc8e8f56d67731ec4206a6ffe01f0b', 'ywyw11', '学生', '123', NULL, '1029807850@qq.com', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-05-18 16:53:21');

SET FOREIGN_KEY_CHECKS = 1;
