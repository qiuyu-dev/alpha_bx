/*
 Navicat Premium Data Transfer

 Source Server         : alpha_bx
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : rm-2zev8arz9h15ml618.mysql.rds.aliyuncs.com:3306
 Source Schema         : bx

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 18/03/2021 09:26:00
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
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单' ROW_FORMAT = DYNAMIC;

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
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '许可' ROW_FORMAT = DYNAMIC;

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
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for admin_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_menu`;
CREATE TABLE `admin_role_menu`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色-菜单主键',
  `rid` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  `mid` int(11) NULL DEFAULT NULL COMMENT '一级菜单ID，二级菜单显示全部',
  `operator` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色-菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for admin_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_permission`;
CREATE TABLE `admin_role_permission`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色-许可主键',
  `rid` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  `pid` int(11) NULL DEFAULT NULL COMMENT '许可ID，许可url前缀',
  `operator` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色-许可' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户-角色主键',
  `uid` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `rid` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  `operator` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户-角色' ROW_FORMAT = DYNAMIC;

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
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '主体' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for bx_achievement
-- ----------------------------
DROP TABLE IF EXISTS `bx_achievement`;
CREATE TABLE `bx_achievement`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '保险完成业绩ID',
  `promotion_id` int(11) NULL DEFAULT NULL COMMENT '推广ID',
  `url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保存plan_id或fr',
  `amount` int(11) NULL DEFAULT NULL COMMENT '成单量',
  `goods_code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注，用于存来源文件名',
  `exposure_num` int(11) NULL DEFAULT NULL COMMENT '曝光人数',
  `follow_num` int(11) NULL DEFAULT NULL COMMENT '关注数量',
  `premium` int(11) NULL DEFAULT NULL COMMENT '保险费',
  `flag` int(2) NULL DEFAULT NULL COMMENT '统计标识，1为直接投保，2为公众号',
  `customers` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '成单客户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4217 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '保险完成业绩' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for bx_promotion
-- ----------------------------
DROP TABLE IF EXISTS `bx_promotion`;
CREATE TABLE `bx_promotion`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '推广URL主键',
  `channel` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '276' COMMENT '长链接channel',
  `ch` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '渠道',
  `product_code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'P1020200122' COMMENT '产品代码',
  `product_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '城惠保' COMMENT '产品名称',
  `goods_code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'G1606372896' COMMENT '长链接参数goods_code',
  `fr_code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '2853' COMMENT '长链接fr的编码，运营位编码',
  `fr` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运营位，赣州',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问url',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` int(2) NULL DEFAULT 0 COMMENT '状态,0-不可以，1-可用',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '江西城惠保销售链接.xlsx' COMMENT '备注用于存来源文件名',
  `plan_id` int(11) NULL DEFAULT NULL COMMENT '计划ID',
  `url_old` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旧的二维码生成链接',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `url_UNIQUE`(`url`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1721 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '保险推广URL' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for bx_task
-- ----------------------------
DROP TABLE IF EXISTS `bx_task`;
CREATE TABLE `bx_task`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '保险销售任务ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `amount` int(11) NULL DEFAULT NULL COMMENT '总销售任务需要完成的数量',
  `goods_code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'G1600249457',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注，用于存来源文件名',
  `end_time` datetime NULL DEFAULT NULL COMMENT '任务截止时间',
  `phone` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 496 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '保险销售任务' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for bx_user_promotion
-- ----------------------------
DROP TABLE IF EXISTS `bx_user_promotion`;
CREATE TABLE `bx_user_promotion`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '我方用户ID，对多个推广',
  `promotion_id` int(11) NULL DEFAULT NULL COMMENT '推广URL对应ID，对一个用户',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 314 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '我方用户-推广URL' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '我方用户ID',
  `username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号，账户名，验证，唯一',
  `password` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码，md5加密',
  `salt` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐值',
  `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `alpha_subject_id` int(11) NULL DEFAULT NULL COMMENT '企业主体ID',
  `enabled` int(1) NULL DEFAULT 1 COMMENT '是否可用，0不可用，1可用',
  `operator` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'admin' COMMENT '操作员',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '江西 保险销售 人员小程序数据.xlsx' COMMENT '备注用于写来源文件名',
  `sup_userid` int(11) NULL DEFAULT NULL COMMENT '上级用户ID',
  `team` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '团队',
  `post` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位',
  `display` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '海报展示内容',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_UNIQUE`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 386 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '我方用户' ROW_FORMAT = DYNAMIC;

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
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `userid` int(11) NULL DEFAULT NULL COMMENT '用户表id',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `language` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示 country，province，city 所用的语言',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 872 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '微信用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- View structure for achieve_data
-- ----------------------------
DROP VIEW IF EXISTS `achieve_data`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `achieve_data` AS select `udata`.`create_time` AS `data_time`,`team_order`.`team_order` AS `team_order`,`udata`.`team` AS `team_name`,`udata`.`id` AS `user_id`,`udata`.`name` AS `user_name`,`udata`.`username` AS `user_phone`,`udata`.`promotion_id` AS `promotion_id`,ifnull(`udata`.`insure_amount`,0) AS `insure_amount`,ifnull(`udata`.`exposure_num`,0) AS `insure_exposure_num`,ifnull(`udata`.`insure_premium`,0) AS `insure_premium`,`udata`.`insure_customers` AS `insure_customers`,ifnull(`udata`.`official_amount`,0) AS `official_amount`,ifnull(`udata`.`follow_num`,0) AS `official_follow_num`,ifnull(`udata`.`official_premium`,0) AS `official_premium`,`udata`.`official_customers` AS `official_customers` from (`bx`.`team_order` left join (select `bx`.`user`.`id` AS `id`,`bx`.`user`.`name` AS `name`,`bx`.`user`.`username` AS `username`,`bx`.`user`.`team` AS `team`,`achieve`.`user_id` AS `user_id`,`achieve`.`promotion_id` AS `promotion_id`,`achieve`.`insure_amount` AS `insure_amount`,`achieve`.`exposure_num` AS `exposure_num`,`achieve`.`insure_premium` AS `insure_premium`,`achieve`.`insure_customers` AS `insure_customers`,`achieve`.`official_amount` AS `official_amount`,`achieve`.`follow_num` AS `follow_num`,`achieve`.`official_premium` AS `official_premium`,`achieve`.`official_customers` AS `official_customers`,`achieve`.`create_time` AS `create_time` from (`bx`.`user` left join (select `bx`.`bx_user_promotion`.`user_id` AS `user_id`,`bx`.`bx_user_promotion`.`promotion_id` AS `promotion_id`,(case `bx`.`bx_achievement`.`flag` when 1 then `bx`.`bx_achievement`.`amount` else 0 end) AS `insure_amount`,`bx`.`bx_achievement`.`exposure_num` AS `exposure_num`,(case `bx`.`bx_achievement`.`flag` when 1 then `bx`.`bx_achievement`.`premium` else 0 end) AS `insure_premium`,(case `bx`.`bx_achievement`.`flag` when 1 then `bx`.`bx_achievement`.`customers` end) AS `insure_customers`,(case `bx`.`bx_achievement`.`flag` when 2 then `bx`.`bx_achievement`.`amount` else 0 end) AS `official_amount`,`bx`.`bx_achievement`.`follow_num` AS `follow_num`,(case `bx`.`bx_achievement`.`flag` when 2 then `bx`.`bx_achievement`.`premium` else 0 end) AS `official_premium`,(case `bx`.`bx_achievement`.`flag` when 2 then `bx`.`bx_achievement`.`customers` end) AS `official_customers`,`bx`.`bx_achievement`.`create_time` AS `create_time` from (`bx`.`bx_user_promotion` left join `bx`.`bx_achievement` on((`bx`.`bx_user_promotion`.`promotion_id` = `bx`.`bx_achievement`.`promotion_id`)))) `achieve` on((`bx`.`user`.`id` = `achieve`.`user_id`))) where (`bx`.`user`.`enabled` = 1)) `udata` on((`team_order`.`team` = `udata`.`team`))) where ((`team_order`.`team_order` <> 9) and (`team_order`.`team_order` <> 10) and (`team_order`.`team_order` <> 12)) order by `udata`.`create_time`,`team_order`.`team_order`,`udata`.`team`,`udata`.`id`;

-- ----------------------------
-- View structure for team_order
-- ----------------------------
DROP VIEW IF EXISTS `team_order`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `team_order` AS select min(`user`.`id`) AS `team_order`,`user`.`team` AS `team` from `user` group by `user`.`team` order by `team_order`;

SET FOREIGN_KEY_CHECKS = 1;
