/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50627
Source Host           : localhost:3306
Source Database       : nvs3000

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2016-09-01 11:35:01
*/

SET FOREIGN_KEY_CHECKS=0;
USE nvs3000;
-- ----------------------------
-- Table structure for `channel_snapshot`
-- ----------------------------
DROP TABLE IF EXISTS `channel_snapshot`;
CREATE TABLE `channel_snapshot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ot_id` varchar(64) NOT NULL DEFAULT '' COMMENT '外部唯一标识',
  `res_id` char(24) NOT NULL COMMENT '资源id',
  `event_type` char(16) NOT NULL COMMENT '事件类型',
  `create_time` char(24) NOT NULL COMMENT '生成时间',
  `url` char(128) NOT NULL COMMENT '图片存放相对路径',
  `upload_time` datetime NOT NULL COMMENT '上传时间',
  `remark` text NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
	INDEX `r_c` USING BTREE (`res_id`, `create_time`) ,
	INDEX `o` USING BTREE (`ot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of channel_snapshot
-- ----------------------------

-- ----------------------------
-- Table structure for `t_corp`
-- ----------------------------
DROP TABLE IF EXISTS `t_corp`;
CREATE TABLE `t_corp` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(64) NOT NULL DEFAULT '',
  `director` varchar(64) NOT NULL,
  `mobile` varchar(18) NOT NULL,
  `address` varchar(128) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_corp
-- ----------------------------

-- ----------------------------
-- Table structure for `t_dict`
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict` (
  `did` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `pre_id` int(10) unsigned NOT NULL COMMENT '父ID',
  `name` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '名称',
  `value` varchar(32) COLLATE utf8_bin NOT NULL,
  `descr` varchar(1024) COLLATE utf8_bin NOT NULL DEFAULT '',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：1有效，0无效',
  `dmlflag` tinyint(4) NOT NULL DEFAULT '1',
  `dmltime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`did`),
  KEY `pre_key` USING BTREE (`pre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100049 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统字典表';

-- ----------------------------
-- Records of t_dict
-- ----------------------------
INSERT INTO `t_dict` VALUES ('1', '0', '云台级别', '', '', '1', '1', '2015-09-23 14:27:25');
INSERT INTO `t_dict` VALUES ('2', '0', '音视频级别', '', '', '1', '1', '2015-09-09 17:28:53');
INSERT INTO `t_dict` VALUES ('3', '0', '预置位类型', '', '', '1', '1', '2015-09-23 11:11:13');
INSERT INTO `t_dict` VALUES ('4', '0', '资源组类型', '', '', '1', '1', '2015-09-23 11:12:17');
INSERT INTO `t_dict` VALUES ('5', '0', ' 资源组权限类型', '', '', '1', '1', '2015-09-23 19:15:05');
INSERT INTO `t_dict` VALUES ('6', '0', '预案动作', '', '', '1', '1', '2015-10-26 16:57:11');
INSERT INTO `t_dict` VALUES ('7', '0', '警告类型', '', '', '1', '1', '2015-10-26 16:58:00');
INSERT INTO `t_dict` VALUES ('8', '0', '系统参数配制', '', '', '1', '1', '2015-10-26 16:58:00');
INSERT INTO `t_dict` VALUES ('100000', '0', '分隔', '', '', '1', '3', '2015-09-06 14:27:37');
INSERT INTO `t_dict` VALUES ('100001', '1', '一级', '1', '', '1', '1', '2015-09-06 14:30:39');
INSERT INTO `t_dict` VALUES ('100002', '1', '二级', '2', '', '1', '1', '2015-09-06 22:33:37');
INSERT INTO `t_dict` VALUES ('100003', '1', '三级', '3', '', '1', '1', '2015-09-09 17:29:27');
INSERT INTO `t_dict` VALUES ('100004', '1', '四级', '4', '', '1', '1', '2015-09-09 17:29:31');
INSERT INTO `t_dict` VALUES ('100005', '1', '五级', '5', '', '1', '1', '2015-09-09 17:29:36');
INSERT INTO `t_dict` VALUES ('100006', '1', '六级', '6', '', '1', '1', '2015-09-09 17:29:40');
INSERT INTO `t_dict` VALUES ('100007', '1', '七级', '7', '', '1', '1', '2015-09-09 17:29:44');
INSERT INTO `t_dict` VALUES ('100008', '1', '八级', '8', '', '1', '1', '2015-09-09 17:29:49');
INSERT INTO `t_dict` VALUES ('100009', '2', '一级', '1', '', '1', '1', '2015-09-09 17:30:00');
INSERT INTO `t_dict` VALUES ('100010', '2', '二级', '2', '', '1', '1', '2015-09-09 17:30:00');
INSERT INTO `t_dict` VALUES ('100011', '2', '三级', '3', '', '1', '1', '2015-09-09 17:30:01');
INSERT INTO `t_dict` VALUES ('100012', '2', '四级', '4', '', '1', '1', '2015-09-09 17:30:01');
INSERT INTO `t_dict` VALUES ('100013', '2', '五级', '5', '', '1', '1', '2015-09-09 17:30:01');
INSERT INTO `t_dict` VALUES ('100014', '2', '六级', '6', '', '1', '1', '2015-09-09 17:30:08');
INSERT INTO `t_dict` VALUES ('100015', '2', '七级', '7', '', '1', '1', '2015-09-09 17:31:02');
INSERT INTO `t_dict` VALUES ('100016', '2', '八级', '8', '', '1', '1', '2015-09-09 17:31:16');
INSERT INTO `t_dict` VALUES ('100017', '3', '白天守望位', '2', '', '1', '1', '2015-09-23 19:15:38');
INSERT INTO `t_dict` VALUES ('100018', '3', '夜晚守望位', '1', '', '1', '1', '2015-09-23 19:16:30');
INSERT INTO `t_dict` VALUES ('100019', '3', '普通预置位', '0', '', '1', '1', '2015-09-23 19:21:43');
INSERT INTO `t_dict` VALUES ('100020', '4', '视频资源组', '1', '', '1', '1', '2015-09-23 19:44:17');
INSERT INTO `t_dict` VALUES ('100021', '4', '平台资源组', '14', '', '1', '1', '2015-09-23 19:50:29');
INSERT INTO `t_dict` VALUES ('100022', '5', '实时视频', '1', '', '1', '1', '2015-09-23 19:51:13');
INSERT INTO `t_dict` VALUES ('100023', '5', '云镜控制', '2', '', '1', '1', '2015-09-23 19:51:58');
INSERT INTO `t_dict` VALUES ('100024', '5', '历史回放', '4', '', '1', '1', '2015-09-23 19:52:07');
INSERT INTO `t_dict` VALUES ('100025', '5', '录像下载', '16', '', '1', '1', '2015-09-23 19:52:30');
INSERT INTO `t_dict` VALUES ('100026', '6', '邮件通知', 'email', '', '1', '1', '2015-10-26 16:59:15');
INSERT INTO `t_dict` VALUES ('100027', '6', '信息通知', 'message', '', '1', '1', '2015-10-26 16:59:19');
INSERT INTO `t_dict` VALUES ('100028', '6', '弹出视频', 'popwindow', '', '1', '1', '2015-10-26 16:59:20');
INSERT INTO `t_dict` VALUES ('100029', '6', '预置位', 'preset', '', '1', '1', '2015-10-26 16:59:22');
INSERT INTO `t_dict` VALUES ('100030', '6', '文字提示', 'prompt', '', '1', '1', '2015-10-26 16:59:24');
INSERT INTO `t_dict` VALUES ('100031', '6', '录像', 'record', '', '1', '1', '2015-10-26 16:59:25');
INSERT INTO `t_dict` VALUES ('100032', '6', '播放声音', 'sound', '', '1', '1', '2015-10-26 16:59:29');
INSERT INTO `t_dict` VALUES ('100033', '7', '入侵检测', '1', '', '1', '1', '2015-10-26 17:01:34');
INSERT INTO `t_dict` VALUES ('100034', '7', '徘徊检测', '2', '', '1', '1', '2015-10-26 17:01:35');
INSERT INTO `t_dict` VALUES ('100035', '7', '移动侦测', '3', '', '1', '1', '2015-10-26 17:01:36');
INSERT INTO `t_dict` VALUES ('100036', '7', '逗留检测', '4', '', '1', '1', '2015-10-26 17:01:36');
INSERT INTO `t_dict` VALUES ('100037', '7', '遗留物检测', '5', '', '1', '1', '2015-10-26 17:01:38');
INSERT INTO `t_dict` VALUES ('100038', '7', '穿越拌线', '6', '', '1', '1', '2015-10-26 17:01:39');
INSERT INTO `t_dict` VALUES ('100039', '7', '硬盘满', '7', '', '1', '1', '2015-10-26 17:01:41');
INSERT INTO `t_dict` VALUES ('100040', '7', '硬盘故障', '8', '', '1', '1', '2015-10-26 17:01:42');
INSERT INTO `t_dict` VALUES ('100041', '7', '服务器断开', '9', '', '1', '1', '2015-10-26 17:01:43');
INSERT INTO `t_dict` VALUES ('100042', '7', '视频丢失', '10', '', '1', '1', '2015-10-26 17:01:44');
INSERT INTO `t_dict` VALUES ('100043', '7', '编码器断开', '11', '', '1', '1', '2015-10-26 17:01:45');
INSERT INTO `t_dict` VALUES ('100044', '7', '告警输入告警', '12', '', '1', '1', '2015-10-26 17:01:46');
INSERT INTO `t_dict` VALUES ('100045', '7', 'openlive', '13', '', '1', '3', '2015-10-26 17:01:47');
INSERT INTO `t_dict` VALUES ('100046', '7', '可见光告警', '14', '', '1', '1', '2015-10-26 17:01:51');
INSERT INTO `t_dict` VALUES ('100047', '8', '秘钥', '1', '654d9fe1dd2a9ae6ba7315604e4a80f6', '1', '1', '2015-10-26 17:01:51');
INSERT INTO `t_dict` VALUES ('100048', '8', '许可', '2', 'Fxeu4kXlr0J4KRO0NIE%2B82edsXxUiVtwFj46glcIcy3IxP%2FLRddR23zjQpadbjRPj0JbQwO2KEK1Ohsp8WBa3B78%2FKGbY7bmmQMN4srSmfg9uCN%2FXjn5GX8U7YrA5VA%2BZlOEAnFR8Rrs%2BP9mQIZlBD2vdedquO%2FfFLt6VCm4SufsbqnV7g%2FBO1TJhpZVnFMS%2', '1', '1', '2015-10-26 17:01:51');

-- ----------------------------
-- Table structure for `t_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `mid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `preid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '上级节点ID，默认0-TOP',
  `full_mid` varchar(128) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '全ID（父全ID_自ID）',
  `name` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '菜单名称',
  `url` varchar(128) CHARACTER SET utf8 NOT NULL COMMENT '菜单链接',
  `icon_path` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '菜单显示图标',
  `is_func` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否功能页面（0-否；1-是）',
  `lel` tinyint(4) NOT NULL DEFAULT '1' COMMENT '菜单层级',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '启用状态：0-不启用（默认）；1-启用',
  `serial_no` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '序列号',
  `dmlflag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '操作标识：1：新增；2：更新；3：删除。',
  `dmltime` datetime NOT NULL,
  PRIMARY KEY (`mid`),
  KEY `pre_key` USING BTREE (`preid`)
) ENGINE=InnoDB AUTO_INCREMENT=977 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统菜单表';

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', '99999001', '1', '系统管理', '/common/module!index.do?parentId=1', '', '1', '0', '1', '0', '2', '2012-07-05 16:15:24');
INSERT INTO `t_menu` VALUES ('50', '1', '1-50', '权限管理', '', '', '0', '1', '1', '970', '2', '2015-09-07 10:30:27');
INSERT INTO `t_menu` VALUES ('51', '50', '1-50-51', '用户管理', '/security/user!findPage.do?isAdmin=1', '/images/menu/23.png', '0', '2', '1', '0', '1', '2012-03-12 09:21:57');
INSERT INTO `t_menu` VALUES ('52', '50', '1-50-52', '角色管理', '/security/role!findPage.do', '/images/menu/26.png', '0', '2', '1', '0', '1', '2012-03-12 09:22:24');
INSERT INTO `t_menu` VALUES ('53', '50', '1-50-53', '功能管理', '/security/menu!findPage.do', '/images/menu/25.png', '0', '2', '1', '0', '2', '2012-03-12 09:22:40');
INSERT INTO `t_menu` VALUES ('54', '953', '1-940-953-54', '添加用户', '/security/user!save.do', '', '1', '3', '1', '0', '2', '2015-09-06 11:31:49');
INSERT INTO `t_menu` VALUES ('55', '953', '1-940-953-55', '编辑用户', '/security/user!update.do', '', '1', '3', '1', '0', '2', '2012-03-12 10:08:31');
INSERT INTO `t_menu` VALUES ('56', '953', '1-940-953-56', '删除用户', '/security/user!delete.do', '', '1', '3', '1', '0', '2', '2012-03-12 10:09:13');
INSERT INTO `t_menu` VALUES ('57', '51', '1-50-51-57', '授权用户', '/security/user!authForUserUP.do', '', '1', '3', '1', '0', '2', '2012-03-12 10:09:17');
INSERT INTO `t_menu` VALUES ('58', '51', '1-50-51-58', '用户解冻', '/security/user!unfreezeUser.do', '', '1', '3', '1', '0', '2', '2014-06-26 10:10:16');
INSERT INTO `t_menu` VALUES ('59', '52', '1-50-52-59', '添加角色', '/security/role!save.do', '', '1', '3', '1', '0', '2', '2013-12-19 00:24:31');
INSERT INTO `t_menu` VALUES ('60', '52', '1-50-52-60', '编辑角色', '/security/role!update.do', '', '1', '3', '1', '0', '2', '2013-12-19 00:24:43');
INSERT INTO `t_menu` VALUES ('61', '52', '1-50-52-61', '删除角色', '/security/role!delete.do', '', '1', '3', '1', '0', '2', '2013-12-19 00:24:57');
INSERT INTO `t_menu` VALUES ('62', '52', '1-50-52-62', '授权角色', '/security/role!authForRoleUP.do', '', '1', '3', '1', '0', '2', '2013-12-19 00:25:10');
INSERT INTO `t_menu` VALUES ('63', '53', '1-50-53-63', '添加功能', '/security/menu!save.do', '', '1', '3', '1', '0', '2', '2012-03-12 10:15:46');
INSERT INTO `t_menu` VALUES ('64', '53', '1-50-53-64', '编辑功能', '/security/menu!update.do', '', '1', '3', '1', '0', '2', '2012-03-12 10:16:09');
INSERT INTO `t_menu` VALUES ('65', '53', '1-50-53-65', '删除功能', '/security/menu!delete.do', '', '1', '3', '1', '0', '2', '2012-03-12 10:16:36');
INSERT INTO `t_menu` VALUES ('70', '1', '1-70', '数据维护', '', '', '0', '2', '1', '944', '2', '2015-09-06 16:53:52');
INSERT INTO `t_menu` VALUES ('936', '944', '1-944-936', '许可管理', '/common/module!findSn.do', '/images/menu/37.png', '0', '3', '1', '936', '2', '2015-09-06 15:44:05');
INSERT INTO `t_menu` VALUES ('937', '944', '1-944-937', '缓存刷新', '/common/module!refreshCache.do', '/images/menu/27.png', '0', '3', '1', '937', '2', '2015-09-06 15:44:05');
INSERT INTO `t_menu` VALUES ('938', '70', '1-70-938', '字典维护', '/baseinfo/dict!findDict.do', '/images/menu/1.png', '0', '3', '1', '938', '1', '2015-09-06 15:46:16');
INSERT INTO `t_menu` VALUES ('939', '1', '1-939', '设备管理', '', '', '0', '1', '1', '50', '1', '2015-09-06 16:49:53');
INSERT INTO `t_menu` VALUES ('940', '1', '1-940', '业务管理', '', '', '0', '1', '1', '70', '1', '2015-09-06 16:52:41');
INSERT INTO `t_menu` VALUES ('941', '1', '1-941', '故障管理', '', '', '0', '1', '1', '941', '2', '2015-09-07 10:43:38');
INSERT INTO `t_menu` VALUES ('942', '1', '1-942', '性能管理', '', '', '0', '1', '1', '943', '1', '2015-09-06 16:54:24');
INSERT INTO `t_menu` VALUES ('943', '1', '1-943', '日志管理', '', '', '0', '1', '1', '942', '2', '2015-09-06 16:56:06');
INSERT INTO `t_menu` VALUES ('944', '1', '1-944', '系统管理', '', '', '0', '1', '1', '966', '2', '2016-08-04 16:53:30');
INSERT INTO `t_menu` VALUES ('945', '939', '1-939-945', '服务器', '/device/server!findPage.do', '/images/menu/31.png', '0', '2', '1', '945', '1', '2015-09-06 16:58:48');
INSERT INTO `t_menu` VALUES ('946', '939', '1-939-946', '编码器', '/device/encoder!findPage.do', '/images/menu/3.png', '0', '2', '1', '946', '1', '2015-09-06 16:59:09');
INSERT INTO `t_menu` VALUES ('947', '939', '1-939-947', '摄像机', '/device/channel!findPage.do', '/images/menu/4.png', '0', '2', '1', '947', '1', '2015-09-06 16:59:32');
INSERT INTO `t_menu` VALUES ('948', '939', '1-939-948', '终端', '/device/terminal!findPage.do', '/images/menu/5.png', '0', '2', '1', '948', '1', '2015-09-06 16:59:39');
INSERT INTO `t_menu` VALUES ('949', '940', '1-940-949', '资源组', '/bussiness/groups!findPage.do', '/images/menu/6.png', '0', '2', '1', '949', '2', '2015-09-23 19:35:14');
INSERT INTO `t_menu` VALUES ('950', '940', '1-940-950', '存储方案', '/bussiness/recordplan!findPage.do', '/images/menu/7.png', '0', '2', '1', '951', '2', '2015-09-23 19:39:58');
INSERT INTO `t_menu` VALUES ('951', '940', '1-940-951', '预案', '/bussiness/prescheme!findPage.do', '/images/menu/8.png', '0', '2', '1', '952', '2', '2015-09-23 19:39:34');
INSERT INTO `t_menu` VALUES ('952', '940', '1-940-952', '预案关联', '/bussiness/alarmprescheme!findPage.do', '/images/menu/9.png', '0', '2', '1', '953', '2', '2015-09-23 19:39:07');
INSERT INTO `t_menu` VALUES ('953', '940', '1-940-953', '用户管理', '/security/user!findPage.do?isAdmin=0', '/images/menu/23.png', '0', '2', '1', '950', '2', '2015-10-16 16:14:51');
INSERT INTO `t_menu` VALUES ('954', '941', '1-941-954', '告警查询', '/fault/alarmInfo!findPage.do', '/images/menu/11.png', '0', '2', '1', '954', '2', '2015-10-16 16:12:00');
INSERT INTO `t_menu` VALUES ('955', '941', '1-941-955', '告警统计', '/fault/alarmInfo!alarmInfoStatistics.do', '/images/menu/12.png', '0', '2', '1', '955', '2', '2015-10-16 16:13:08');
INSERT INTO `t_menu` VALUES ('956', '941', '1-941-956', '告警级别设置', '', '/images/menu/13.png', '0', '2', '1', '956', '3', '2015-09-06 17:02:54');
INSERT INTO `t_menu` VALUES ('957', '942', '1-942-957', '性能监视', '/bussiness/devicePerformance!findPage.do', '/images/menu/14.png', '0', '2', '1', '957', '2', '2016-01-04 10:55:15');
INSERT INTO `t_menu` VALUES ('958', '942', '1-942-958', '设备运行监测', '', '/images/menu/15.png', '0', '2', '1', '958', '1', '2015-09-06 17:04:06');
INSERT INTO `t_menu` VALUES ('959', '942', '1-942-959', '用户资源占用', '', '/images/menu/16.png', '0', '2', '1', '959', '1', '2015-09-06 17:04:25');
INSERT INTO `t_menu` VALUES ('960', '943', '1-943-960', '告警日志', '', '/images/menu/17.png', '0', '2', '1', '960', '1', '2015-09-06 17:04:47');
INSERT INTO `t_menu` VALUES ('961', '943', '1-943-961', '设备运行日志', '', '/images/menu/18.png', '0', '2', '1', '961', '1', '2015-09-06 17:05:05');
INSERT INTO `t_menu` VALUES ('962', '943', '1-943-962', '管理员操作日志', '/logs/userTrace!findPage.do', '/images/menu/19.png', '0', '2', '1', '962', '2', '2015-10-16 16:16:38');
INSERT INTO `t_menu` VALUES ('963', '943', '1-943-963', '客户端操作日志', '/logs/operationLog!findPage.do', '/images/menu/20.png', '0', '2', '1', '963', '2', '2015-10-16 16:17:15');
INSERT INTO `t_menu` VALUES ('964', '944', '1-944-964', '平台信息', '', '/images/menu/21.png', '0', '2', '1', '964', '1', '2015-09-06 17:06:34');
INSERT INTO `t_menu` VALUES ('965', '70', '1-70-965', '参数设置', '', '/images/menu/33.png', '0', '3', '1', '965', '1', '2015-09-06 17:33:20');
INSERT INTO `t_menu` VALUES ('966', '1', '1-966', '视频浏览', '', '', '0', '1', '1', '939', '1', '2015-11-26 11:54:16');
INSERT INTO `t_menu` VALUES ('967', '966', '1-966-967', '实时视频', '/bussiness/display!beforeLiveDisplay.do', '/images/menu/34.png', '0', '2', '1', '967', '1', '2015-11-26 11:54:39');
INSERT INTO `t_menu` VALUES ('968', '966', '1-966-968', '云端录像', '/bussiness/display!beforeHistoryDisplay.do?recordPath=0', '/images/menu/35.png', '0', '2', '1', '968', '1', '2015-11-26 11:54:58');
INSERT INTO `t_menu` VALUES ('969', '966', '1-966-969', '设备录像', '/bussiness/display!beforeHistoryDisplay.do?recordPath=1', '/images/menu/35.png', '0', '2', '1', '969', '1', '2015-11-26 11:55:14');
INSERT INTO `t_menu` VALUES ('970', '1', '1-970', '地图模式', '', '', '0', '1', '1', '940', '1', '2015-12-08 15:08:00');
INSERT INTO `t_menu` VALUES ('971', '970', '1-970-971', '实时视频', '/bussiness/display!mapForPlay.do?h=0', '/images/menu/34.png', '0', '2', '1', '971', '1', '2015-12-08 15:08:22');
INSERT INTO `t_menu` VALUES ('972', '970', '1-970-972', '云端录像', '/bussiness/display!mapForPlay.do?h=1&p=0', '/images/menu/35.png', '0', '2', '1', '972', '2', '2015-12-08 15:09:04');
INSERT INTO `t_menu` VALUES ('973', '970', '1-970-973', '设备录像', '/bussiness/display!mapForPlay.do?h=1&p=1', '/images/menu/35.png', '0', '2', '1', '973', '2', '2015-12-08 15:09:09');
INSERT INTO `t_menu` VALUES ('974', '970', '1-970-974', '编辑坐标', '/bussiness/display!mapForPlay.do?h=0&a=edit', '/images/menu/36.png', '0', '2', '1', '974', '2', '2015-12-12 14:06:21');
INSERT INTO `t_menu` VALUES ('975', '940', '1-940-975', '通道截屏', '/device/channel!findPageSnapshot.do', '/images/menu/4.png', '0', '2', '1', '975', '1', '2016-03-07 14:00:51');
INSERT INTO `t_menu` VALUES ('976', '936', '1-944-936-976', '生成许可', '/common/module!cteateSn.do', '', '1', '4', '1', '976', '2', '2016-09-01 11:22:33');

-- ----------------------------
-- Table structure for `t_priv_data`
-- ----------------------------
DROP TABLE IF EXISTS `t_priv_data`;
CREATE TABLE `t_priv_data` (
  `user_id` char(24) NOT NULL,
  `priv_code` varchar(24) NOT NULL,
  `priv_type` tinyint(4) NOT NULL,
  `dmlflag` tinyint(4) NOT NULL,
  `dmltime` datetime NOT NULL,
  PRIMARY KEY (`user_id`,`priv_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_priv_data
-- ----------------------------

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `rid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `app_code` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `rname` varchar(45) CHARACTER SET utf8 NOT NULL COMMENT '角色名称',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '启用状态：0-不启用（默认）；1-启用',
  `role_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '角色类型：1-普通角色(默认);2-超级管理员;3-酒店自管理员.',
  `is_public` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否通用角色，0非通用，1通用',
  `dmlflag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '操作标识：1：新增；2：更新；3：删除。',
  `dmltime` datetime NOT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统角色信息表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'lskjoss', '超级管理员', '1', '1', '1', '2', '2014-11-05 16:28:46');
INSERT INTO `t_role` VALUES ('2', 'lskjoss', '系统管理员', '1', '1', '1', '1', '2015-09-23 16:54:19');
INSERT INTO `t_role` VALUES ('3', 'lskjoss', '视频浏览人员', '1', '1', '1', '1', '2015-11-26 14:21:39');

-- ----------------------------
-- Table structure for `t_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `rid` int(10) unsigned NOT NULL COMMENT '角色ID',
  `mid` int(10) unsigned NOT NULL COMMENT '模块ID',
  `dmlflag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '操作标识：1：新增；2：更新；3：删除。',
  `dmltime` datetime NOT NULL,
  PRIMARY KEY (`rid`,`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单权限信息表';

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES ('1', '1', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '50', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '51', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '52', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '53', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '54', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '55', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '56', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '57', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '58', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '59', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '60', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '61', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '62', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '63', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '64', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '65', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '70', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '936', '1', '2016-09-01 11:34:02');
INSERT INTO `t_role_menu` VALUES ('1', '937', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '938', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '939', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '940', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '941', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '942', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '943', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '944', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '945', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '946', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '947', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '948', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '949', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '950', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '951', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '952', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '953', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '954', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '955', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '957', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '962', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '963', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '966', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '967', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '968', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '969', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '970', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '971', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '972', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '973', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '974', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '975', '1', '2016-08-04 17:04:34');
INSERT INTO `t_role_menu` VALUES ('1', '976', '1', '2016-09-01 11:21:41');
INSERT INTO `t_role_menu` VALUES ('2', '1', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '54', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '55', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '56', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '937', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '939', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '940', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '941', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '942', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '943', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '944', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '945', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '946', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '947', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '948', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '949', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '950', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '951', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '952', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '953', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '954', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '955', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '957', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '962', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '963', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '966', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '967', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '968', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '969', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '970', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '971', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '972', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '973', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '974', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('2', '975', '1', '2016-08-04 17:04:06');
INSERT INTO `t_role_menu` VALUES ('3', '1', '1', '2015-12-08 15:09:56');
INSERT INTO `t_role_menu` VALUES ('3', '966', '1', '2015-12-08 15:09:56');
INSERT INTO `t_role_menu` VALUES ('3', '967', '1', '2015-12-08 15:09:56');
INSERT INTO `t_role_menu` VALUES ('3', '968', '1', '2015-12-08 15:09:56');
INSERT INTO `t_role_menu` VALUES ('3', '969', '1', '2015-12-08 15:09:56');
INSERT INTO `t_role_menu` VALUES ('3', '970', '1', '2015-12-08 15:09:56');
INSERT INTO `t_role_menu` VALUES ('3', '971', '1', '2015-12-08 15:09:56');
INSERT INTO `t_role_menu` VALUES ('3', '972', '1', '2015-12-08 15:09:56');
INSERT INTO `t_role_menu` VALUES ('3', '973', '1', '2015-12-08 15:09:56');

-- ----------------------------
-- Table structure for `t_user_link`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_link`;
CREATE TABLE `t_user_link` (
  `amid` char(24) COLLATE utf8_bin NOT NULL COMMENT '管理员ID',
  `preamid` char(24) COLLATE utf8_bin NOT NULL COMMENT '上级管理员ID，默认0-TOP',
  `full_amid` varchar(124) CHARACTER SET utf8 NOT NULL COMMENT '全ID（父全ID_自ID）',
  PRIMARY KEY (`full_amid`),
  KEY `amid_key` USING BTREE (`amid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理员关系表';

-- ----------------------------
-- Records of t_user_link
-- ----------------------------
INSERT INTO `t_user_link` VALUES ('2100003020000001', '0', '2100003020000001');
INSERT INTO `t_user_link` VALUES ('2100003020000002', '0', '2100003020000002');
INSERT INTO `t_user_link` VALUES ('2100003020000003', '0', '2100003020000003');

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `rid` int(10) unsigned NOT NULL COMMENT '角色ID',
  `amid` char(24) COLLATE utf8_bin NOT NULL COMMENT '管理员ID',
  `dmlflag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '操作标识：1：新增；2：更新；3：删除。',
  `dmltime` datetime NOT NULL,
  PRIMARY KEY (`rid`,`amid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统角色配置表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '2100003020000002', '1', '2015-11-26 11:53:19');
INSERT INTO `t_user_role` VALUES ('2', '2100003020000001', '1', '2015-09-23 17:07:07');
INSERT INTO `t_user_role` VALUES ('3', '2100003020000003', '1', '2015-09-23 17:07:07');

-- ----------------------------
-- Table structure for `t_user_trace`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_trace`;
CREATE TABLE `t_user_trace` (
  `auid` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `amid` char(24) NOT NULL,
  `user_type` smallint(6) NOT NULL DEFAULT '1' COMMENT '管理员类型:1，管理员；2，客户端',
  `mid` int(11) NOT NULL,
  `menu_name` varchar(255) NOT NULL DEFAULT '',
  `user_trace` varchar(255) NOT NULL DEFAULT '',
  `operate_date` datetime NOT NULL,
  `operate_status` smallint(6) NOT NULL DEFAULT '1' COMMENT '操作状态：1、成功，2、失败',
  PRIMARY KEY (`auid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_trace
-- ----------------------------
