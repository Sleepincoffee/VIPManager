/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : mmsystem

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-05-16 13:19:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '1' COMMENT '状态，0:不可用, 1:可用',
  `premission` int(11) DEFAULT '1' COMMENT '权限 999:系统管理员,默认1普通',
  `admin_account` varchar(68) NOT NULL COMMENT '管理账号',
  `admin_password` varchar(258) DEFAULT NULL,
  `admin_phone` varchar(68) NOT NULL COMMENT '管理手机号',
  `admin_name` varchar(68) NOT NULL COMMENT '管理员姓名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `admin_account` (`admin_account`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('70', '2016-09-12 10:12:17', '2018-05-16 09:38:17', '1', '999', 'admin', '812B63837C93AB288C627DF3D19387F75B9876C43D1638B01757A534D5647047', '15589517517', '超级管理员');
INSERT INTO `admin` VALUES ('71', '2018-03-14 10:44:55', '2018-05-16 09:38:17', '1', '1', 'liu', '812B63837C93AB288C627DF3D19387F75B9876C43D1638B01757A534D5647047', '13200997788', '刘俊');
INSERT INTO `admin` VALUES ('72', '2018-05-16 09:40:18', '2018-05-16 09:40:18', '1', '1', 'admin_cql', '812B63837C93AB288C627DF3D19387F75B9876C43D1638B01757A534D5647047', '13355778866', '陈齐良');
INSERT INTO `admin` VALUES ('73', '2018-05-16 09:40:57', '2018-05-16 09:40:57', '1', '1', 'zhangsan', '812B63837C93AB288C627DF3D19387F75B9876C43D1638B01757A534D5647047', '17734654567', '张山');

-- ----------------------------
-- Table structure for consume_record
-- ----------------------------
DROP TABLE IF EXISTS `consume_record`;
CREATE TABLE `consume_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `order_number` varchar(68) NOT NULL COMMENT '订单编号',
  `card_number` varchar(68) NOT NULL COMMENT '会员账号',
  `member_name` varchar(68) NOT NULL COMMENT '会员姓名',
  `member_rank` varchar(68) NOT NULL COMMENT '会员等级',
  `goods_number` varchar(68) NOT NULL COMMENT '商品编号',
  `goods_name` varchar(68) NOT NULL COMMENT '商品名称',
  `goods_price` double NOT NULL COMMENT '商品价格',
  `goods_count` int(11) NOT NULL COMMENT '商品数量',
  `consume_money` double NOT NULL COMMENT '消费金额',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态：0,未购买，1已购买',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_number` (`order_number`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consume_record
-- ----------------------------
INSERT INTO `consume_record` VALUES ('1', '2016-09-18 20:23:52', '2016-09-18 20:23:52', '741852', 'a4271934', '李四', '金牌会员', '98651', '牛奶', '25.3', '2', '35.42', '0');
INSERT INTO `consume_record` VALUES ('2', '2016-09-19 19:49:06', '2016-09-19 19:56:21', '123456', 'a4271934', '李四', '金牌会员', '98651', '牛奶', '25.3', '2', '35.42', '1');
INSERT INTO `consume_record` VALUES ('4', '2016-09-24 20:01:37', '2016-09-24 20:01:37', '123456ww', 'a4271934', '战士', '铜牌会员', '98651', '牛奶', '25.3', '2', '45.54', '0');
INSERT INTO `consume_record` VALUES ('5', '2018-03-15 18:47:11', '2018-03-15 18:47:11', '20180315', '2017', '刘俊', '铜牌会员', '2018', '2018', '50', '1', '45', '1');
INSERT INTO `consume_record` VALUES ('6', '2018-03-15 18:48:01', '2018-03-15 18:48:01', '20182017', '2017', '刘俊', '铜牌会员', '10', '鱼香肉丝', '20', '1', '18', '0');
INSERT INTO `consume_record` VALUES ('7', '2018-05-11 17:22:21', '2018-05-11 17:22:21', '201805111', '20180504001', '宋江', '铜牌会员', '98651', '牛奶', '25.3', '2', '45.54', '1');
INSERT INTO `consume_record` VALUES ('8', '2018-05-11 17:40:24', '2018-05-11 17:40:24', '201805112', '2017', '刘俊', '铜牌会员', '98651', '牛奶', '25.3', '3', '68.31', '1');
INSERT INTO `consume_record` VALUES ('9', '2018-05-11 19:09:56', '2018-05-11 19:09:56', '2018051103', '2017', '刘俊', '铜牌会员', '10', '鱼香肉丝', '20', '6', '108', '1');
INSERT INTO `consume_record` VALUES ('10', '2018-05-11 19:12:03', '2018-05-11 19:12:03', '2018051104', '2017', '刘俊', '铜牌会员', '10', '鱼香肉丝', '20', '6', '108', '1');
INSERT INTO `consume_record` VALUES ('11', '2018-05-11 19:13:52', '2018-05-11 19:13:52', '2018051105', '2017', '刘俊', '铜牌会员', '2018', '2018', '50', '12', '540', '1');
INSERT INTO `consume_record` VALUES ('12', '2018-05-11 19:31:54', '2018-05-11 19:31:54', '208051106', '2017', '刘俊', '铜牌会员', '10', '鱼香肉丝', '20', '28', '504', '1');
INSERT INTO `consume_record` VALUES ('13', '2018-05-11 19:32:50', '2018-05-11 19:32:50', '208051107', '2017', '刘俊', '铜牌会员', '11596355', '一次性餐具', '2.5', '6', '13.5', '1');
INSERT INTO `consume_record` VALUES ('14', '2018-05-11 19:34:52', '2018-05-11 19:34:52', '2018051108', '2017', '刘俊', '银牌会员', '10', '鱼香肉丝', '20', '5', '80', '1');
INSERT INTO `consume_record` VALUES ('15', '2018-05-11 19:35:53', '2018-05-11 19:35:53', '2018050101', '2017', '刘俊', '金牌会员', '11596355', '一次性餐具', '2.5', '1', '1.75', '1');
INSERT INTO `consume_record` VALUES ('16', '2018-05-11 19:37:45', '2018-05-11 19:38:47', '2018050102', '2017', '刘俊', '银牌会员', '11596355', '一次性餐具', '2.5', '1', '2', '1');
INSERT INTO `consume_record` VALUES ('17', '2018-05-11 19:40:21', '2018-05-11 19:41:25', '2018050111', '2017', '刘俊', '金牌会员', '11596355', '一次性餐具', '2.5', '1', '1.75', '1');
INSERT INTO `consume_record` VALUES ('18', '2018-05-11 19:43:42', '2018-05-11 19:43:57', '201805020111', '2017', '刘俊', '银牌会员', '10', '鱼香肉丝', '20', '4', '64', '1');
INSERT INTO `consume_record` VALUES ('19', '2018-05-11 19:45:53', '2018-05-11 19:45:53', '20180502333', '2017', '刘俊', '金牌会员', '2054865', 'iphone7', '5388', '1', '3771.6', '1');
INSERT INTO `consume_record` VALUES ('20', '2018-05-11 19:48:22', '2018-05-11 19:48:22', '2018050312', '2017', '刘俊', '金牌会员', '20154865', 'iphone7s', '7288', '1', '5101.599999999999', '1');
INSERT INTO `consume_record` VALUES ('21', '2018-05-11 19:48:58', '2018-05-11 19:49:44', '2018050199', '2017', '刘俊', '金牌会员', '11596355', '一次性餐具', '2.5', '1', '1.75', '1');
INSERT INTO `consume_record` VALUES ('22', '2018-05-11 19:51:46', '2018-05-11 19:51:46', '2018050288', '2017', '刘俊', '钻石会员', '20154865', 'iphone7s', '7288', '2', '7288', '0');
INSERT INTO `consume_record` VALUES ('24', '2018-05-11 19:55:57', '2018-05-11 19:56:45', '2018050198', '2017', '刘俊', '铜牌会员', '20154865', 'iphone7s', '7288', '2', '13118.4', '1');
INSERT INTO `consume_record` VALUES ('25', '2018-05-11 20:01:38', '2018-05-11 20:01:38', '2018051198', '2017', '刘俊', '铜牌会员', '10', '鱼香肉丝', '20', '1', '18', '1');
INSERT INTO `consume_record` VALUES ('26', '2018-05-11 21:05:50', '2018-05-11 21:05:50', '20180316', '2017', '陈齐良', '铜牌会员', '10', '鱼香肉丝', '20', '3', '54', '1');
INSERT INTO `consume_record` VALUES ('27', '2018-05-16 10:28:46', '2018-05-16 10:28:46', '2018051601', '2017', '陈齐良', '金牌会员', '98651', '牛奶', '25.3', '2', '35.42', '1');
INSERT INTO `consume_record` VALUES ('28', '2018-05-16 11:50:47', '2018-05-16 11:50:47', '2018051603', '2018', '李四', '铜牌会员', '10', '鱼香肉丝', '20', '10', '180', '1');
INSERT INTO `consume_record` VALUES ('29', '2018-05-16 11:51:19', '2018-05-16 11:51:19', '2018051604', '2018', '李四', '铜牌会员', '10', '鱼香肉丝', '20', '3', '54', '0');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `goods_number` varchar(68) NOT NULL COMMENT '商品编号',
  `goods_name` varchar(68) NOT NULL COMMENT '商品名称',
  `goods_price` double NOT NULL COMMENT '商品价格',
  `goods_count` int(11) NOT NULL COMMENT '商品数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `goods_number` (`goods_number`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('11', '2016-09-08 11:26:15', '2018-05-11 19:49:44', '11596355', '一次性餐具', '2.5', '10');
INSERT INTO `goods` VALUES ('12', '2016-09-08 13:32:25', '2018-05-11 19:45:53', '2054865', 'iphone7', '5388', '99');
INSERT INTO `goods` VALUES ('13', '2016-09-08 13:40:59', '2018-05-11 19:56:45', '20154865', 'iphone7s', '7288', '2');
INSERT INTO `goods` VALUES ('14', '2016-09-09 20:01:41', '2018-05-16 10:28:46', '98651', '牛奶', '25.3', '9');
INSERT INTO `goods` VALUES ('15', '2018-03-15 18:28:05', '2018-05-16 11:50:47', '10', '鱼香肉丝', '20', '437');
INSERT INTO `goods` VALUES ('16', '2018-03-15 18:43:18', '2018-05-11 19:13:52', '2018', '2018', '50', '487');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '1' COMMENT '状态,0:不可用，1:可用',
  `card_number` varchar(68) NOT NULL COMMENT '会员卡号(账号)',
  `member_rank` varchar(68) NOT NULL DEFAULT '铜牌会员' COMMENT '等级：铜银金钻，默认铜',
  `member_name` varchar(68) NOT NULL COMMENT '会员姓名',
  `member_phone` varchar(68) DEFAULT NULL COMMENT '会员手机',
  `member_password` varchar(256) DEFAULT NULL,
  `money` double DEFAULT '0' COMMENT '金额',
  `email` varchar(68) DEFAULT NULL COMMENT '邮箱',
  `integral` double unsigned zerofill DEFAULT '0000000000000000000000' COMMENT '会员积分',
  PRIMARY KEY (`id`),
  UNIQUE KEY `card_number` (`card_number`),
  KEY `member_rank` (`member_rank`),
  CONSTRAINT `member_ibfk_1` FOREIGN KEY (`member_rank`) REFERENCES `members_rank` (`member_rank`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('1', '2016-09-14 19:12:30', '2018-05-16 10:05:40', '1', 'a4271934', '铜牌会员', '战士', '1558952368', '812B63837C93AB288C627DF3D19387F75B9876C43D1638B01757A534D5647047', '0', null, '0000000000000000000000');
INSERT INTO `member` VALUES ('2', '2016-09-15 16:04:45', '2018-05-11 20:28:07', '1', 'a4271935', '铜牌会员', '王五', '17862822595', '812B63837C93AB288C627DF3D19387F75B9876C43D1638B01757A534D5647047', '20', null, '0000000000000000000100');
INSERT INTO `member` VALUES ('3', '2018-03-15 18:24:58', '2018-05-16 11:22:53', '0', '2017', '金牌会员', '陈齐良', '13323455455', '812B63837C93AB288C627DF3D19387F75B9876C43D1638B01757A534D5647047', '58462.92', null, '0000000000000004089.42');
INSERT INTO `member` VALUES ('4', '2018-05-04 21:05:36', '2018-05-11 17:51:39', '1', '20180504001', '铜牌会员', '宋江', '13344556677', '812B63837C93AB288C627DF3D19387F75B9876C43D1638B01757A534D5647047', '123410.46', null, '0000000000000000000000');
INSERT INTO `member` VALUES ('5', '2018-05-16 11:45:20', '2018-05-16 11:51:19', '1', '2018', '铜牌会员', '李四', '17766556766', '812B63837C93AB288C627DF3D19387F75B9876C43D1638B01757A534D5647047', '9820', '', '0000000000000000000234');
INSERT INTO `member` VALUES ('6', '2018-05-16 13:11:41', '2018-05-16 13:11:41', '1', '2019', '银牌会员', '王五', '18772345632', '812B63837C93AB288C627DF3D19387F75B9876C43D1638B01757A534D5647047', '1000', 'cs781503542@qq.com', '0000000000000000000000');

-- ----------------------------
-- Table structure for members_rank
-- ----------------------------
DROP TABLE IF EXISTS `members_rank`;
CREATE TABLE `members_rank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `member_rank` varchar(68) NOT NULL DEFAULT '铜牌会员' COMMENT '会员等级',
  `discount` double DEFAULT '1' COMMENT '折扣',
  `integral` double DEFAULT NULL COMMENT '积分要求',
  PRIMARY KEY (`member_rank`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of members_rank
-- ----------------------------
INSERT INTO `members_rank` VALUES ('3', '2016-09-14 07:32:50', '2018-05-11 17:48:38', '金牌会员', '0.7', '1000');
INSERT INTO `members_rank` VALUES ('4', '2016-09-14 17:56:49', '2018-05-11 17:50:11', '钻石会员', '0.5', '5000');
INSERT INTO `members_rank` VALUES ('1', '2016-09-14 17:58:14', '2018-05-11 17:51:12', '铜牌会员', '0.9', '100');
INSERT INTO `members_rank` VALUES ('2', '2016-09-14 17:59:40', '2018-05-11 17:51:15', '银牌会员', '0.8', '500');
