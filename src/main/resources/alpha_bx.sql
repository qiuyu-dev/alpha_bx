/*
 Navicat MySQL Data Transfer

 Source Server         : 172.19.136.40_bx
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 172.19.136.40:3306
 Source Schema         : bx

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 03/12/2020 09:11:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '菜单主键',
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `name_zh` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称-中文',
  `path` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单显示路径，唯一',
  `icon_cls` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `component` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父ID',
  `operator` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_menu
-- ----------------------------

-- ----------------------------
-- Table structure for admin_permission
-- ----------------------------
DROP TABLE IF EXISTS `admin_permission`;
CREATE TABLE `admin_permission`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '许可主键',
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '许可名称',
  `desc_` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '许可名称-中文',
  `url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '许可url前缀',
  `operator` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '许可' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_permission
-- ----------------------------

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色主键',
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `name_zh` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称-中文，不可修改',
  `enabled` int(11) NULL DEFAULT 1 COMMENT '是否可用，0不可用，1可用',
  `operator` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role
-- ----------------------------

-- ----------------------------
-- Table structure for admin_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_menu`;
CREATE TABLE `admin_role_menu`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色-菜单主键',
  `rid` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  `mid` int(11) NULL DEFAULT NULL COMMENT '一级菜单ID，二级菜单显示全部',
  `operator` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色-菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for admin_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_permission`;
CREATE TABLE `admin_role_permission`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色-许可主键',
  `rid` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  `pid` int(11) NULL DEFAULT NULL COMMENT '许可ID，许可url前缀',
  `operator` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色-许可' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户-角色主键',
  `uid` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `rid` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  `operator` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户-角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for alpha_subject
-- ----------------------------
DROP TABLE IF EXISTS `alpha_subject`;
CREATE TABLE `alpha_subject`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主体主键',
  `subject_type` int(11) NULL DEFAULT NULL COMMENT '1、客户，2、保险企业，3、服务企业',
  `record_type` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型，汉字,身份证，2、军官证,驾驶证，3、新生儿出生证明，4、社会保险卡号，,10组织机构代码，',
  `record_number` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织机构代码，验证，唯一，或者身份证或其他证件好',
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '企业名称，或客户姓名',
  `phone` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `location` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在地,可以录入或来源于身份证号',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄,可以录入或来源于身份证号',
  `sex` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别,1男，2女，可以录入或来源于身份证号',
  `source_type` int(11) NULL DEFAULT NULL COMMENT '来源：1、excel申请；2、系统维护；3、api上传',
  `source_id` int(11) NULL DEFAULT NULL COMMENT '触发id，1、cp_excel_mst_id，2null',
  `source_detail_id` int(11) NULL DEFAULT NULL COMMENT '触发id，1、cp_excel_detail_id，2null',
  `enabled` int(1) NULL DEFAULT 1 COMMENT '是否可用，0不可用，1可用',
  `operator` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '主体' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of alpha_subject
-- ----------------------------

-- ----------------------------
-- Table structure for bx_achievement
-- ----------------------------
DROP TABLE IF EXISTS `bx_achievement`;
CREATE TABLE `bx_achievement`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '保险完成业绩ID',
  `promotion_id` int(11) NULL DEFAULT NULL COMMENT '推广ID',
  `url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `amount` int(11) NULL DEFAULT NULL,
  `goods_code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'G1600249457',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注，用于存来源文件名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '保险完成业绩' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bx_achievement
-- ----------------------------

-- ----------------------------
-- Table structure for bx_promotion
-- ----------------------------
DROP TABLE IF EXISTS `bx_promotion`;
CREATE TABLE `bx_promotion`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '推广URL主键',
  `channel` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '276' COMMENT '长链接channel',
  `ch` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '城惠保推广' COMMENT '渠道',
  `product_code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'P1020200098' COMMENT '产品代码',
  `product_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '城惠保' COMMENT '产品名称',
  `goods_code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'G1600249457' COMMENT '长链接参数goods_code',
  `fr_code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '长链接fr的编码',
  `fr` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推广来源序号',
  `url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问url',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态,0-不可以，1-可用',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注用于存来源文件名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '保险推广URL' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bx_promotion
-- ----------------------------
INSERT INTO `bx_promotion` VALUES (1, '276', '城惠保推广', 'P1020200098', '城惠保', 'G1600249457', '2683', '城惠保-树山1', 'https://h5.360huiminbao.cn/datamark?bxtfcode=l2TwglEB6xE&__pl__=', '2020-12-02 01:05:53', 1, NULL);
INSERT INTO `bx_promotion` VALUES (2, '276', '城惠保推广', 'P1020200098', '城惠保', 'G1600249457', '2684', '城惠保-树山2', 'https://h5.360huiminbao.cn/datamark?bxtfcode=sT7GnJSKEwI&__pl__=', '2020-12-02 01:05:53', 1, NULL);
INSERT INTO `bx_promotion` VALUES (3, '276', '城惠保推广', 'P1020200098', '城惠保', 'G1600249457', '2685', '城惠保-树山3', 'https://h5.360huiminbao.cn/datamark?bxtfcode=iQ6en5JcJY0&__pl__=', '2020-12-02 01:05:53', 1, NULL);
INSERT INTO `bx_promotion` VALUES (4, '276', '城惠保推广', 'P1020200098', '城惠保', 'G1600249457', '2686', '城惠保-树山4', 'https://h5.360huiminbao.cn/datamark?bxtfcode=L7oRnEVZTho&__pl__=', '2020-12-02 01:05:53', 1, NULL);
INSERT INTO `bx_promotion` VALUES (5, '276', '城惠保推广', 'P1020200098', '城惠保', 'G1600249457', '2687', '城惠保-树山5', 'https://h5.360huiminbao.cn/datamark?bxtfcode=cyaMN4pMT5I&__pl__=', '2020-12-02 01:05:53', 1, NULL);
INSERT INTO `bx_promotion` VALUES (6, '276', '城惠保推广', 'P1020200098', '城惠保', 'G1600249457', '2688', '城惠保-树山6', 'https://h5.360huiminbao.cn/datamark?bxtfcode=sMgc-9L5SNU&__pl__=', '2020-12-02 01:05:53', 1, NULL);
INSERT INTO `bx_promotion` VALUES (7, '276', '城惠保推广', 'P1020200098', '城惠保', 'G1600249457', '2689', '城惠保-树山7', 'https://h5.360huiminbao.cn/datamark?bxtfcode=xZtt3RJ5ui0&__pl__=', '2020-12-02 01:05:53', 1, NULL);
INSERT INTO `bx_promotion` VALUES (8, '276', '城惠保推广', 'P1020200098', '城惠保', 'G1600249457', '2690', '城惠保-树山8', 'https://h5.360huiminbao.cn/datamark?bxtfcode=qK-BVCpD9vc&__pl__=', '2020-12-02 01:05:53', 1, NULL);
INSERT INTO `bx_promotion` VALUES (9, '276', '城惠保推广', 'P1020200098', '城惠保', 'G1600249457', '2691', '城惠保-树山9', 'https://h5.360huiminbao.cn/datamark?bxtfcode=EILOqFY8WUs&__pl__=', '2020-12-02 01:05:53', 0, NULL);
INSERT INTO `bx_promotion` VALUES (10, '276', '城惠保推广', 'P1020200098', '城惠保', 'G1600249457', '2692', '城惠保-树山10', 'https://h5.360huiminbao.cn/datamark?bxtfcode=eSIXwAVFvOI&__pl__=', '2020-12-02 01:05:53', 1, NULL);

-- ----------------------------
-- Table structure for bx_task
-- ----------------------------
DROP TABLE IF EXISTS `bx_task`;
CREATE TABLE `bx_task`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '保险销售任务ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `amount` int(11) NULL DEFAULT NULL COMMENT '总销售任务需要完成的数量',
  `goods_code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'G1600249457',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注，用于存来源文件名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '保险销售任务' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bx_task
-- ----------------------------

-- ----------------------------
-- Table structure for bx_user_promotion
-- ----------------------------
DROP TABLE IF EXISTS `bx_user_promotion`;
CREATE TABLE `bx_user_promotion`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '我方用户ID，对多个推广',
  `promotion_id` int(11) NULL DEFAULT NULL COMMENT '推广URL对应ID，对一个用户',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '我方用户-推广URL' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bx_user_promotion
-- ----------------------------
INSERT INTO `bx_user_promotion` VALUES (1, 13, 10, '2020-12-02 01:39:48');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '我方用户ID',
  `username` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号，账户名，验证，唯一',
  `password` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码，md5加密',
  `salt` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐值',
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `alpha_subject_id` int(11) NULL DEFAULT NULL COMMENT '企业主体ID',
  `enabled` int(1) NULL DEFAULT 1 COMMENT '是否可用，0不可用，1可用',
  `operator` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注用于写来源文件名',
  `sup_userid` int(11) NULL DEFAULT NULL COMMENT '上级用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '我方用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (12, '18612755812', NULL, NULL, '刘书良', '18612755812', NULL, NULL, 1, NULL, '2020-12-02 00:56:12', NULL, NULL);
INSERT INTO `user` VALUES (13, '18610594008', NULL, NULL, '邱哥', '18610594008', NULL, NULL, 1, NULL, '2020-12-02 00:56:12', NULL, 12);
INSERT INTO `user` VALUES (14, '13611250005', NULL, NULL, '谷玲玲', '13611250005', NULL, NULL, 1, NULL, '2020-12-02 00:56:12', NULL, 12);
INSERT INTO `user` VALUES (15, '13911112222', NULL, NULL, '测试1', '13911112222', NULL, NULL, 1, NULL, '2020-12-02 00:56:12', NULL, 13);
INSERT INTO `user` VALUES (16, '13922223333', NULL, NULL, '测试2', '13922223333', NULL, NULL, 1, NULL, '2020-12-02 00:56:12', NULL, 13);
INSERT INTO `user` VALUES (17, '13933334444', NULL, NULL, '测试3', '13933334444', NULL, NULL, 1, NULL, '2020-12-02 00:56:12', NULL, 13);
INSERT INTO `user` VALUES (18, '13944445555', NULL, NULL, '测试4', '13944445555', NULL, NULL, 1, NULL, '2020-12-02 00:56:12', NULL, 14);
INSERT INTO `user` VALUES (19, '13955556666', NULL, NULL, '测试5', '13955556666', NULL, NULL, 0, NULL, '2020-12-02 00:56:12', NULL, 14);
INSERT INTO `user` VALUES (20, '13966667777', NULL, NULL, '测试6', '13966667777', NULL, NULL, 1, NULL, '2020-12-02 00:56:12', NULL, 14);

-- ----------------------------
-- Table structure for wx_user
-- ----------------------------
DROP TABLE IF EXISTS `wx_user`;
CREATE TABLE `wx_user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '微信用户ID',
  `openid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `unionid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信unionid',
  `nick_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号',
  `gender` tinyint(2) NULL DEFAULT 0 COMMENT '用户性别 0未知,1男,2女',
  `country` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户所在国家',
  `province` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户所在省份',
  `city` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户所在城市',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `userid` int(11) NULL DEFAULT NULL COMMENT '用户表id',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `language` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示 country，province，city 所用的语言',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '微信用户' ROW_FORMAT = DYNAMIC;


SET FOREIGN_KEY_CHECKS = 1;
