
DROP DATABASE  IF EXISTS nvs3000;
CREATE DATABASE IF NOT EXISTS nvs3000 character set utf8;

grant all privileges on nvs3000.*  to 'root'@'%' identified by '6000' with grant option;
grant all privileges on nvs3000.*  to 'root'@'localhost' identified by '6000' with grant option;
flush privileges;

USE nvs3000;


CREATE TABLE IF NOT EXISTS db_version
(
    version char(10) not null primary key,
    description varchar(256) not null
) comment = 'db version info';


CREATE TABLE IF NOT EXISTS user
(
    id char(24) not null primary key comment 'user ID, must be union code',
    name char(128) binary not null UNIQUE,
    password char(128) binary not null
);

CREATE TABLE IF NOT EXISTS user_info
(
    id char(24) not null primary key comment '@see User.ID',
    alias char(128) comment 'user aliase',
    ptz_level   int unsigned null comment 'ptz control level,[1-8], 8 is the highest',
    av_level    int unsigned null comment 'video level, [1-8], 8 is the highest',
    user_type   int unsigned not null comment '1 default user, 2 gis user',    
    active_begin_time char(128) comment 'format: hh:mm:ss', 
    active_end_time   char(128) comment 'format: hh:mm:ss', 
    phone char(128) not null comment 'phone number',
    mail char(128) not null comment 'mail number',
    last_login_time   char(128) comment 'last login time',
    description varchar(256) comment 'user description',
    status BOOL not null comment 'online status, 0 indicate offline',
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
) comment = 'user info';

CREATE TABLE IF NOT EXISTS server
(
    id char(24) not null primary key comment 'server ID, must be union code',
    name char(128) not null UNIQUE comment 'name, must be unique',
    type int comment '@see ServerType',
    cmu_id char(24) comment 'CMU ID, empty if it is master',
    ip char(128) not null comment 'ip',
    port SMALLINT UNSIGNED comment 'port to CMU',
    username char(128) not null comment 'register name for CMU',    
    password char(128) not null comment 'register password for CMU',
    max_connection int default 65535 comment 'max connection count',
    address char(128) comment 'physical address',
    description varchar(256) comment 'description',
    status BOOL not null comment 'online status, 0 indicate offline'
) comment = 'server';

CREATE TABLE IF NOT EXISTS encoder
(
    id char(24) not null primary key comment 'encoder ID,must be union code',
    name char(128) not null UNIQUE comment 'name, unique',
    enabled BOOL not null comment 'enabled flag,0 indicate disabled',
    has_audio BOOL not null comment 'enabled flag,0 indicate disabled',
    model char(128) not null comment 'device model',
    ip char(128) not null comment 'device ip',
    port SMALLINT UNSIGNED comment 'device port',
    username char(128) not null comment 'login user name',
    password char(128) not null comment 'login user password',
    channel_count  int not null comment 'video channel count',
    input_count  int not null comment 'input count',
    output_count int not null comment 'output count',
    address char(128) comment 'physical address',
    description varchar(256) comment 'description',
    status BOOL not null comment 'online status, 0 indicate offline'
) comment = 'encoder device';



CREATE TABLE IF NOT EXISTS channel
(
    id char(24) not null primary key comment 'ID,must be union code',
    name char(128) not null UNIQUE comment 'name, unique',
    enabled BOOL not null comment 'enabled flag,0 indicate disabled',
    encoder_id char(24) not null comment 'encoder ID',
    num int not null comment 'sequence num, start from 0',
    has_ptz int comment '0 indicate no PTZ',
    has_audio int comment '0 indicate no audio',
    stream_count int default 1 comment 'stream count, can be 2 or more',
    description varchar(256) comment 'description',
    status BOOL not null comment 'online status, 0 indicate offline'
) comment = 'video channel';


CREATE TABLE IF NOT EXISTS channel_stream
(
    channel_id char(24) not null comment 'channel ID,@see Channel.ID',
    num int not null comment 'sequence num, start from 0',
    codec int not null comment 'video codec, @see MediaCodec',
    width int default 0 comment 'image width',
    height int default 0 comment 'image ',
    fps double comment 'frame rate, ab. fps',
    bps int comment 'bitrate, ab. bps',
    iframe int comment 'key frame interval',
    audio_codec int comment 'audio codec, @see MediaCodec',
    audio_channel int comment 'audio channel, may be 1 or 2',
    audio_sample int comment 'sampe rate',
    primary key(channel_id, num),
    FOREIGN KEY (channel_id) REFERENCES channel(id) ON DELETE CASCADE
) comment = 'channel stream format';



CREATE TABLE IF NOT EXISTS decoder
(
    id char(24) not null primary key comment 'decoder ID,must be union code',
    name char(128) not null UNIQUE comment 'name, unique',
    enabled BOOL not null comment 'enabled flag,0 indicate disabled',    
    model char(128) not null comment 'device model',
    ip char(128) not null comment 'device ip',
    port SMALLINT UNSIGNED comment 'device port',
    username char(128) not null comment 'login user name',
    password char(128) not null comment 'login user password',
    channel_count  int not null comment 'video channel count',  
    address char(128) comment 'physical address',
    description varchar(256) comment 'description',
    status BOOL not null comment 'online status, 0 indicate offline'
) comment = 'decoder device';


CREATE TABLE IF NOT EXISTS decoder_channel
(
    id char(24) not null primary key comment 'ID,must be union code',
    name char(128) not null UNIQUE comment 'name, unique',
    enabled BOOL not null comment 'enabled flag,0 indicate disabled',
    decoder_id char(24) not null comment 'decoder ID',
    num int not null comment 'sequence num, start from 0',   
    description varchar(256) comment 'description',
    status BOOL not null comment 'online status, 0 indicate offline',
    FOREIGN KEY (decoder_id) REFERENCES decoder(id) ON DELETE CASCADE
) comment = 'decoder channel';


CREATE TABLE IF NOT EXISTS res_decoder_encoder
(
    decoder_channel_id char(24) not null comment 'ID,must be union code',
    encoder_channel_id char(24) not null comment 'ID,must be union code',
    primary key(decoder_channel_id, encoder_channel_id)
) comment = 'res_decoder_encoder';


CREATE TABLE IF NOT EXISTS preset
(   
    channel_id char(24) not null comment 'channel ID, @see Channel.ID',
    num int not null comment 'sequence num, start from 0',
    name char(128) not null comment 'name',  
    flag int unsigned not null comment 'default is 0, 1 indicat day,2 indicate night',  
    primary key(channel_id, num)
) comment = 'preset';

CREATE TABLE IF NOT EXISTS preset_jpg
(   
    channel_id char(24) not null comment 'channel ID, @see Channel.ID',
    num int not null comment 'sequence num, start from 0',
    data_len int unsigned not null comment 'image length', 
    data_buffer MEDIUMBLOB not null comment 'image data'
) comment = 'preset image, JPG only';

CREATE TABLE IF NOT EXISTS encoder_input
(
    id char(24) not null primary key comment 'ID, must be union code',
    name char(128) not null comment 'name',
    enabled BOOL not null comment 'enabled flag,0 indicate disabled',
    encoder_id char(24) not null comment 'encoder ID',
    num int not null comment 'sequence num, start from 0',
    status int not null comment 'electric status',
    description varchar(256) comment 'description',
    FOREIGN KEY (encoder_id) REFERENCES encoder(id) ON DELETE CASCADE
) comment = 'device input';


CREATE TABLE IF NOT EXISTS encoder_output
(
    id char(24) not null primary key comment 'ID,must be union code',
    name char(128) not null comment 'name',
    enabled BOOL not null comment 'enabled flag,0 indicate disabled',
    encoder_id char(24) not null comment 'encoder ID',
    num int not null comment 'sequence num, start from 0',
    status int not null comment 'electric status',
    description varchar(256) comment 'description',
    FOREIGN KEY (encoder_id) REFERENCES encoder(id) ON DELETE CASCADE
) comment = 'device output';


CREATE TABLE IF NOT EXISTS groups
(
    id char(24) not null primary key comment 'ID,must be union code',
    name char(128) not null UNIQUE comment 'name, unique',
    type int comment 'group type',
    subnum int default 0 comment 'element count',
    description varchar(256) comment 'description'
) comment = 'resource group';

CREATE TABLE IF NOT EXISTS group_resource
(
    group_id char(24) not null comment 'group ID',
    resource_id char(24) not null comment 'resource ID',
    primary key(group_id, resource_id),
    FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE
) comment = 'group resource';


CREATE TABLE IF NOT EXISTS encoder_groups 
(
	encoder_id  char(24) NOT NULL PRIMARY KEY COMMENT 'encoder ID' ,
	group_id  char(24) NOT NULL COMMENT 'group ID' ,
	FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE
) COMMENT='encoder groups';

CREATE TABLE IF NOT EXISTS server_encoder
(
    server_id char(24) not null comment 'server ID',
    encoder_id char(24) not null comment 'encoder ID',
    primary key(server_id, encoder_id),
    FOREIGN KEY (server_id) REFERENCES server(id) ON DELETE CASCADE
) comment = 'server encoder associative';

CREATE TABLE IF NOT EXISTS server_stauts (
		server_id  char(24) NOT NULL ,
		cpu  varchar(8) NOT NULL DEFAULT '' ,
		cpu_total  varchar(8) NOT NULL DEFAULT '' ,
		memory  varchar(16) NOT NULL DEFAULT '' ,
		memory_free  varchar(16) NOT NULL DEFAULT '' ,
		memory_total  varchar(16) NOT NULL DEFAULT '' ,
		network_in  varchar(16) NOT NULL DEFAULT '' ,
		network_out  varchar(16) NOT NULL DEFAULT '' ,
		online_client  varchar(16) NOT NULL DEFAULT '' ,
		online_server  varchar(16) NOT NULL DEFAULT '' ,
		create_time  datetime NOT NULL ,
		PRIMARY KEY (server_id)
);

CREATE TABLE IF NOT EXISTS user_group_right
(
    user_id char(24) not null comment 'user ID, @see User.ID',
    group_id char(24) not null comment 'group ID,@see Group.ID',
    group_right int comment 'right',
    primary key(user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE
) comment = 'user right';


CREATE TABLE IF NOT EXISTS terminal
(
    id char(24) not null primary key comment 'ID,must be union code',
    name char(128) not null UNIQUE comment 'name, unique',
    enabled BOOL not null comment 'enabled flag,0 indicate disabled',
    ip char(128) not null comment 'ip',
    address char(128) comment 'physical address',
    unlock_key char(128) comment 'unlock key',
    description varchar(256) comment 'description'
) comment = 'terminal';

CREATE TABLE IF NOT EXISTS terminal_user
(
    terminal_id char(24) not null comment 'terminal ID',
    user_id char(24) not null comment 'user ID',
    primary key(terminal_id, user_id),
    FOREIGN KEY (terminal_id) REFERENCES terminal(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) comment = 'terminal user associative';


CREATE TABLE IF NOT EXISTS record_plan
(    
    name char(128) not null primary key comment 'plan name,unique',
    resolution int default 0 comment 'image resolution',
    frametype  int unsigned not null comment 'frame rate',
    timespan char(168) comment 'time span,7*24 bytes, character 0 indicate no record',   
    cycle_date int unsigned not null comment 'cycle day',
    description varchar(256) comment 'description'
) comment = 'record plan';

CREATE TABLE IF NOT EXISTS channel_record_plan
(
    channel_id char(24) not null comment 'channel ID,@see Channel.ID',
    plan_name char(128) not null comment 'plan name',
    primary key(channel_id, plan_name),
    FOREIGN KEY (channel_id) REFERENCES channel(id) ON DELETE CASCADE,
    FOREIGN KEY (plan_name) REFERENCES record_plan(name) ON DELETE CASCADE
) comment = 'channel record plan associative';

CREATE TABLE IF NOT EXISTS operation_log
(
    id int unsigned auto_increment not null primary key comment 'log ID',
    user_id char(24) not null comment 'user ID',
    user_name char(128) not null comment 'user name',
    terminal_ip char(24) not null comment 'terminal IP',
    time varchar(32) comment 'date time',
    operation char(128) not null comment 'operation name',
    object_id char(24) not null comment 'object ID',
    description varchar(256) comment 'description'
) comment = 'log';


CREATE TABLE IF NOT EXISTS  alarm_info
(
    id int unsigned auto_increment not null primary key comment 'alarm ID',
    source_id char(24) not null comment 'source ID',
    alarm_type int comment 'alarm type',
    name varchar(256) comment 'name',
    begin_time varchar(32) comment 'begin time',
    end_time varchar(32) comment 'end time',   
    state  int unsigned not null comment 'state,1 indicate close',
    storage_server_id char(24) not null comment 'CRU ID indicate which CRU saved the stream',
    deal_state int unsigned not null comment '1 indicate correct,-1 indicate error, 0 indicate unknown',
    deal_user_id char(24) not null comment 'user ID',
    deal_time  varchar(32) comment 'deal time',
    description varchar(256) comment 'description'
) comment = 'alarm';


CREATE TABLE IF NOT EXISTS action
(
   id int unsigned auto_increment not null primary key comment 'action ID',
   name char(128) comment 'action name',
   type char(128) not null comment 'action type,@see  ActionType',
   target char(24) comment 'target ID',
   duration int comment 'in second',
   ahead_of_time int comment 'in second',
   data varchar(512) comment 'param'
) comment = 'action';

CREATE TABLE IF NOT EXISTS pre_scheme
(
   id int unsigned auto_increment not null primary key comment 'scheme ID',
   name char(128) unique comment 'name',
   description varchar(256) comment 'description'
) comment = 'prescheme';


CREATE TABLE IF NOT EXISTS pre_scheme_action
(
   scheme_id int unsigned comment 'scheme ID',
   action_id int unsigned comment 'action ID',
   primary key(scheme_id, action_id),
   FOREIGN KEY (scheme_id) REFERENCES pre_scheme(id) ON DELETE CASCADE,
   FOREIGN KEY (action_id) REFERENCES action(id) ON DELETE CASCADE
) comment = 'scheme action associative';


CREATE TABLE IF NOT EXISTS alarm_pre_scheme
(
    source_id char(24) not null comment 'alarm source ID',
    alarm_type int comment 'alarm type',
    schema_id int unsigned comment 'scheme ID',
    FOREIGN KEY (schema_id) REFERENCES pre_scheme(id) ON DELETE CASCADE
) comment = 'alarm scheme associative';



CREATE TABLE IF NOT EXISTS media_codec
(
    tag int not null primary key comment 'codec value',	
    name char(128) unique comment 'name'
) comment = 'media codec';




-- platform

CREATE TABLE IF NOT EXISTS platform
(
    id char(24) not null primary key comment 'platform ID,must be union code',
    name char(128) comment 'name',
    ip char(128) not null comment 'ip',
    port SMALLINT UNSIGNED comment 'port',
    username char(128) comment 'user name',
    password char(128) comment 'user password',
    description varchar(256) comment 'description'
) comment = 'platform';


CREATE TABLE IF NOT EXISTS platform_server
(
    platform_id char(24) not null comment 'platform ID',
    server_id char(24) not null comment 'server ID,@see Server.ID',
    primary key(platform_id, server_id),
    FOREIGN KEY (platform_id) REFERENCES platform(id) ON DELETE CASCADE
) comment = 'platform server associative';


CREATE TABLE IF NOT EXISTS platform_res
(
    id char(24) not null primary key comment 'node ID,must be union code',
    name char(128) not null comment 'name',
    parent_id char(24) not null comment 'parent ID, empty if it is root',
    server_id char(24) not null comment 'SA ID',
    subnum int comment 'children count',
    has_ptz int comment '0 indicate no PTZ',
    codec int not null comment 'video codec,@see MediaCodec',
    longitude double default 0.0 comment 'longitude',
    latitude double default 0.0 comment 'latitude',
    status BOOL not null comment 'online status, 0 indicate offline',
    description varchar(256) comment 'description',
    FOREIGN KEY (server_id) REFERENCES encoder(id) ON DELETE CASCADE
) comment = 'platform resource';


CREATE TABLE IF NOT EXISTS  alarm_device
(
    id char(24) not null primary key,
    name char(128) not null unique comment 'name, unique',
    channel_count int  not null comment 'device channel count',
    ip char(24) not null comment 'IP',
    port int  not null comment 'port',
    username char(128) not null comment 'user name',    
    password char(128) not null comment 'user password',
    address char(128) comment 'physical address',
    description varchar(256) comment 'description',
    status BOOL not null comment 'online status, 0 indicate offline'
) comment = 'alarm device';

CREATE TABLE IF NOT EXISTS  alarm_device_channel
(
    channel_id char(24) not null comment 'ID,must be union code',
    name char(128) not null UNIQUE comment 'name, unique',
    device_id char(24) not null comment 'device ID, @see AlarmDevice',
    num int not null comment 'sequence num, start from 0',
    description varchar(256) comment 'description',
    primary key(num, device_id)
) comment = 'alarm device channel associative';

CREATE TABLE IF NOT EXISTS  res_alarm_channel
(
    alarm_channel_id char(24) not null comment 'ID,must be union code',
    channel_id char(24) not null comment 'ID,must be union code',
    primary key(alarm_channel_id ,channel_id)
) comment = 'resAlarmChannelChannel';

CREATE TABLE IF NOT EXISTS  res_alarm_type
(
    id char(24) not null primary key ,
    source_id char(24) not null,
    alarm_type int unsigned not null,
    description varchar(256) comment 'description'      
)comment = '';


CREATE TABLE IF NOT EXISTS msu_channel
(
    channel_id char(24) not null comment 'channel ID,@see Channel.ID',	
    server_id char(24) not null comment 'MSU ID',	
    primary key(channel_id, server_id)
) comment = 'MSU channel associative';



CREATE TABLE IF NOT EXISTS route
(   
    route_id int unsigned auto_increment not null primary key, 
    terminal_id char(24) not null comment 'terminal or SA ID', 
    server_id char(24) not null comment 'server ID',
    hop int unsigned not null comment 'hop count'   
) comment = 'route';
       
CREATE TABLE IF NOT EXISTS route_hop
(   
    route_id int unsigned not null, 
    hop_num int unsigned not null comment 'sequence num, start from 0',
    server_id char(24) not null comment 'server ID'
) comment = 'route hop';


CREATE TABLE IF NOT EXISTS user_tree
(
    user_id char(24) not null comment 'user ID',
    res_id char(24) not null comment 'resource ID',
    name char(128) not null comment 'name',
    parent_id char(24) not null comment 'parent ID,empty if it is root',
    previous_id char(24) not null comment 'Previous ID,empty if it is first',
    primary key(user_id, res_id,parent_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    INDEX `u_p_p` USING BTREE (`user_id`, `parent_id`, `previous_id`),
    INDEX `u_r` USING BTREE (`user_id`, `res_id`)
) comment = 'user node tree';




CREATE TABLE IF NOT EXISTS next_code_num
(
    name char(128) not null primary key comment 'entity type name',
    type_code char(6) comment 'type code,4 character',
    num int default 2 comment 'next num'
) comment = 'next union code num';


CREATE TABLE IF NOT EXISTS position_code
(
    code char(8) not null primary key comment 'position code,6 character'
) comment = 'position code';


CREATE TABLE IF NOT EXISTS osd_type
(
    id char(24) not null primary key comment 'ID,must be union code',
    type int default 0 comment '0 front osd,1 end osd'
) comment = 'osd  type';


CREATE TABLE IF NOT EXISTS cruise_track
(   
    channel_id char(24) not null comment 'channel ID, @see Channel.ID',
    num int not null comment 'sequence num, start from 1,end to 255',
    name char(128) not null comment 'name',  
    dwell_time int not null comment 'sec', 
    speed int not null comment 'ptz Speed', 
    flag int not null comment 'default 0', 
    preset_num char(128) not null comment '#1#4#2', 
    status int not null comment 'stop 0,start 1,pause 2', 
    primary key(channel_id, num)
) comment = 'cruisetrack';


CREATE TABLE IF NOT EXISTS server_channel
(
    channel_id char(24) not null comment 'channel ID',	
    server_id char(24) not null comment 'Server ID',	
    primary key(channel_id, server_id)
) comment = 'Server channel associative';


CREATE TABLE IF NOT EXISTS inter_device
(
   id int auto_increment not null primary key,
   name char(255) not null UNIQUE comment 'name, unique',
   description varchar(256) comment 'description'
)comment = 'InterDevice';


CREATE TABLE IF NOT EXISTS channel_param
(
   channel_id char(24) not null comment 'channel ID,@see Channel.ID',	
   channel_type int not null comment '0 default , 1 SmokeDetect , 2 Infrared',
   scene_type   int not null comment '0 default , 1 FUJINON , 2 KOWA',
   decoder_address int not null comment '0 default',
   ptz_model int not null comment '0 default, 1 yaan',
   ptz_protocol int not null comment '0 pelco-d, 1 pelco-p',	
   trans_com_type int not null comment '0 default,1 485,2 422,3 232',
   baudrate int not null comment '2400 default',
   checkbit int not null comment '0 default',
   databit int not null comment '8 default',
   flowcontrol int not null comment '0 default',
   stopbit int not null comment '1 default',
   vaparam char(128) comment 'SmokeDetect param',
   inter_device_id int not null default 0 comment 'InterDevice ID, @see InterDevice.DeviceID',
   primary key(channel_id)
) comment = 'ChannelParam';


CREATE TABLE IF NOT EXISTS ims_gis_info
(
   channel_id char(24) not null comment 'channel ID,@see Channel.ID',
   recvtime char(32) not null comment 'yyyy-mm-dd hh:mm:ss',
   status int not null comment '0 online status',  
   longitude double default 0.0 comment 'longitude',
   latitude double default 0.0 comment 'latitude',
   direction double default 0.0 comment 'direction',
   dpitch double default 0.0 comment 'dpitch',
   angle double default 0.0 comment 'angle',
   cur_range double default 0.0 comment 'curr range',  
   speed double default 0.0 comment 'speed',
   height double default 0.0 comment 'height',
   primary key(channel_id)
) comment = 'gis info';


-- server type
CREATE TABLE IF NOT EXISTS server_type
(
    id int not null primary key comment 'type value, start from 1',
    name char(128) not null comment 'type name'
) comment = 'server type';


-- resolution
CREATE TABLE IF NOT EXISTS resolution
(
    id int not null primary key comment 'resolution ID, start from 1',
    width int comment 'image width',
    height int comment 'image height',
    name char(128) not null comment 'name'
) comment = 'resolution';


-- encoder model
CREATE TABLE IF NOT EXISTS encoder_model
(
    model char(128) not null primary key comment 'model, letter and number are permitted',
    provider char(128) comment 'provider name'
) comment = 'encoder model';


-- alarm type
CREATE TABLE IF NOT EXISTS alarm_type
(
    type int not null primary key comment 'alarm type value',
    name char(128) not null comment 'alarm type name'
) comment = 'alarm type enumaration';



-- right
CREATE TABLE IF NOT EXISTS right_type
(
    type int not null primary key comment 'right type value',
    name char(128) not null comment 'right name'
) comment = 'right enumaration';



CREATE TABLE IF NOT EXISTS action_type
(
    type char(128) not null primary key comment 'action type value',
    name char(128) not null comment 'action name'
) comment = 'action type enumaration';



DELIMITER $$

-- 格式化编码序号为6个十六进制字符,填充字符为0
DROP FUNCTION IF EXISTS `nvs3000`.`format_code_num` $$
CREATE FUNCTION `nvs3000`.`format_code_num` (num int) RETURNS char(8)
BEGIN
  declare result char(24);
  declare len int;
  set result = CONCAT('000000', CONV(num, 10, 10));
  set len = LENGTH(result);
  return substring(result, len - 6 + 1);
END $$

DROP FUNCTION IF EXISTS `nvs3000`.`make_union_code` $$
CREATE FUNCTION `nvs3000`.`make_union_code` (codename char(128)) RETURNS char(24)
BEGIN
  declare result char(24);
  declare pos_code char(8);
  declare t_code char(6);
  declare next_num char(8);

  select code into pos_code from position_code;
  select type_code into t_code from next_code_num where name=codename;
  select format_code_num(num) into next_num from next_code_num where name=codename;
  update  next_code_num set num=num+1 where name=codename;
  select CONCAT(pos_code, t_code, next_num) into result;
  return result;
END $$

DELIMITER ;

-- constant or default value

-- version
INSERT INTO db_version VALUES('1.0.0.0', 'init version');

INSERT INTO position_code VALUES('210000');

INSERT INTO next_code_num VALUES('group', '7101', 2);
INSERT INTO next_code_num VALUES('usertree', '7201', 2);
INSERT INTO next_code_num VALUES('user', '3020', '4');
INSERT INTO next_code_num VALUES('cmu', '2007', 2);
INSERT INTO next_code_num VALUES('mdu', '2004', 2);
INSERT INTO next_code_num VALUES('sa', '2008', 2);
INSERT INTO next_code_num VALUES('msu', '2002', 2);
INSERT INTO next_code_num VALUES('cru', '2030', 2);
INSERT INTO next_code_num VALUES('au', '2032', 2);
INSERT INTO next_code_num VALUES('encoder', '1205', 2);
INSERT INTO next_code_num VALUES('channel', '1201', 2);
INSERT INTO next_code_num VALUES('input', '1207', 2);
INSERT INTO next_code_num VALUES('output', '1208', 2);
INSERT INTO next_code_num VALUES('alarmdevice', '1230', 2);
INSERT INTO next_code_num VALUES('terminal', '1101', 2);

-- default user
INSERT INTO user values('2100003020000001', 'admin', 'admin@lskj123');
INSERT INTO user values ('2100003020000002', 'superadmin', 'superadmin123321');
INSERT INTO user values ('2100003020000003', 'everybody', 'everybody');
INSERT INTO user_info values('2100003020000001', 'admin', 5, 5, 1, '00:00:00', '23:59:59', '','', NULL, '...', 0);
INSERT INTO user_info values ('2100003020000002', 'superadmin', '1', '1', '1', '', '', '18600000000', 'liwf@beyeon.com', null, '', '0');
INSERT INTO user_info values ('2100003020000003', 'everybody', '1', '1', '1', '', '', '18600000000', 'liwf@beyeon.com', null, '', '0');

INSERT INTO server_type VALUES(1, 'cmu');
INSERT INTO server_type VALUES(2, 'mdu');
INSERT INTO server_type VALUES(3, 'msu');
INSERT INTO server_type VALUES(5, 'sa');
INSERT INTO server_type VALUES(6, 'au');


INSERT INTO resolution VALUES(1, 176, 144, 'qcif');
INSERT INTO resolution VALUES(2, 352, 288, 'cif');
INSERT INTO resolution VALUES(3, 352, 144, 'dcif');
INSERT INTO resolution VALUES(4, 704, 576, '4cif');
INSERT INTO resolution VALUES(5, 720, 576, 'd1');
INSERT INTO resolution VALUES(6, 1280, 720, 'hd720p');
INSERT INTO resolution VALUES(7, 1920, 1080, 'hd1080p');
INSERT INTO resolution VALUES(8, 2560, 1920, 'hd1920p');


INSERT INTO encoder_model VALUES('hik', 'hik');
INSERT INTO encoder_model VALUES('dahua', 'dahua');
INSERT INTO encoder_model VALUES('axis', 'axis');
INSERT INTO encoder_model VALUES('onvif', 'onvif');
INSERT INTO encoder_model VALUES('lc', 'lc');
INSERT INTO encoder_model VALUES('tvt', 'tvt');
INSERT INTO encoder_model VALUES('rtsp', 'rtsp');
INSERT INTO encoder_model VALUES('gb28181', 'gb28181');
INSERT INTO encoder_model VALUES('nvs', 'nvs');
INSERT INTO encoder_model VALUES('360', '360');
INSERT INTO encoder_model VALUES('ehome', 'ehome');
INSERT INTO encoder_model VALUES('tm2', 'tm2');
INSERT INTO encoder_model VALUES('platform', 'platform');
INSERT INTO encoder_model VALUES('hbgk', 'hbgk');
INSERT INTO encoder_model VALUES('tdwy', 'tdwy');
INSERT INTO encoder_model VALUES('gb28181decoder', 'gb28181decoder');



INSERT INTO alarm_type VALUES(1, 'intrusion'); 
INSERT INTO alarm_type VALUES(2, 'loiter'); 
INSERT INTO alarm_type VALUES(3, 'moving'); 
INSERT INTO alarm_type VALUES(4, 'stay'); 
INSERT INTO alarm_type VALUES(5, 'left take'); 
INSERT INTO alarm_type VALUES(6, 'line'); 
INSERT INTO alarm_type VALUES(7, 'disk full'); 
INSERT INTO alarm_type VALUES(8, 'disk fault');
INSERT INTO alarm_type VALUES(9, 'server off');
INSERT INTO alarm_type VALUES(10, 'video lost');
INSERT INTO alarm_type VALUES(11, 'encoder off');
INSERT INTO alarm_type VALUES(12, 'input');
INSERT INTO alarm_type VALUES(13, 'open live');
INSERT INTO alarm_type VALUES(14, 'smokedetect');


INSERT INTO right_type VALUES(1, 'play');
INSERT INTO right_type VALUES(2, 'ptz');
INSERT INTO right_type VALUES(4, 'playback');
INSERT INTO right_type VALUES(8, 'deal alarm');
INSERT INTO right_type VALUES(16, 'download record');


INSERT INTO action_type VALUES('record', 'record');
INSERT INTO action_type VALUES('preset', 'goto preset');
INSERT INTO action_type VALUES('popwindow', 'pop window');
INSERT INTO action_type VALUES('prompt', 'prompt text');
INSERT INTO action_type VALUES('sound', 'open sound');
INSERT INTO action_type VALUES('email', 'send mail');
INSERT INTO action_type VALUES('message', 'send sms');

/**遗留编码器与资源组关系建立
INSERT INTO `encoder_groups` SELECT e.id ,g.id from encoder e INNER JOIN groups g on e.`name` = g.`name`
left JOIN encoder_groups eg on eg.encoder_id = e.id where eg.encoder_id is null;
**/