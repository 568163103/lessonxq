USE nvs3000;

DROP TABLE IF EXISTS `case_info`;
CREATE TABLE `case_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` varchar(64) NOT NULL,
  `uid` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `case_type` int(2) NOT NULL,
  `detect_state` int(2) NOT NULL,
  `creator` varchar(32) NOT NULL,
  `code_num` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `jurisdiction_area` varchar(64) NOT NULL,
  `happening_place` varchar(64) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `case_informant`;
CREATE TABLE `case_informant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `caseid` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `gender` int(2) NOT NULL,
  `birthday` datetime NOT NULL,
  `tel` varchar(64) NOT NULL,
  `nativeplace` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `case_video_file`;
CREATE TABLE `case_video_file` (
  `caseid` varchar(64) NOT NULL,
  `casefileseq` varchar(32) NOT NULL,
  `downloadtime` datetime NOT NULL,
  `filebegintime` datetime NOT NULL,
  `fileendtime` datetime NOT NULL,
  `filepath` varchar(256) NOT NULL,
  `deletetorecycle` int(11) NOT NULL ,
  `fileMD5` varchar(64) NOT NULL ,
  `longitude` float NOT NULL ,
	`latitude` float NOT NULL ,
  PRIMARY KEY (`caseid`,`casefileseq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

DROP TABLE IF EXISTS `encoder_groups`;
CREATE TABLE `encoder_groups` (
  `encoder_id` char(24) NOT NULL COMMENT 'encoder ID',
  `group_id` char(24) NOT NULL COMMENT 'group ID',
  PRIMARY KEY (`encoder_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `encoder_groups_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='encoder groups';

DROP TABLE IF EXISTS `server_stauts`;
CREATE TABLE `server_stauts` (
  `server_id` char(24) NOT NULL,
  `cpu` varchar(8) NOT NULL DEFAULT '',
  `cpu_total` varchar(8) NOT NULL DEFAULT '',
  `memory` varchar(16) NOT NULL DEFAULT '',
  `memory_free` varchar(16) NOT NULL DEFAULT '',
  `memory_total` varchar(16) NOT NULL DEFAULT '',
  `network_in` varchar(16) NOT NULL DEFAULT '',
  `network_out` varchar(16) NOT NULL DEFAULT '',
  `online_client` varchar(16) NOT NULL DEFAULT '',
  `online_server` varchar(16) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `platform_res`
ADD COLUMN `description`  varchar(256) NOT NULL DEFAULT '' AFTER `status`;

ALTER TABLE `t_dict`
ADD COLUMN `descr`  varchar(1024) NOT NULL DEFAULT '' AFTER `value`,
ADD COLUMN `status`  tinyint(1) NOT NULL DEFAULT 1 AFTER `descr`;

ALTER TABLE `user_info`
MODIFY COLUMN `ptz_level` int(10) unsigned DEFAULT NULL COMMENT 'ptz control level,[1-8], 8 is the highest',
MODIFY COLUMN `av_level` int(10) unsigned DEFAULT NULL COMMENT 'video level, [1-8], 8 is the highest';

ALTER TABLE `user_tree`
ADD INDEX `u_p_p` (`user_id`,`parent_id`,`previous_id`),
ADD INDEX `u_r` (`user_id`,`res_id`);

DROP TABLE IF EXISTS `t_corp`;
CREATE TABLE `t_corp` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(64) NOT NULL DEFAULT '',
  `director` varchar(64) NOT NULL,
  `mobile` varchar(18) NOT NULL,
  `address` varchar(128) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_priv_data`;
CREATE TABLE `t_priv_data` (
  `user_id` char(24) NOT NULL,
  `priv_code` varchar(24) NOT NULL,
  `priv_type` tinyint(4) NOT NULL,
  `dmlflag` tinyint(4) NOT NULL,
  `dmltime` datetime NOT NULL,
  PRIMARY KEY (`user_id`,`priv_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
