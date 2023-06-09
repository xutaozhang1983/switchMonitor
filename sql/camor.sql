CREATE TABLE `tb_device_item`
(
    `id`          int       NOT NULL AUTO_INCREMENT,
    capability varchar(128)    NOT NULL                                    DEFAULT '' COMMENT '设备能力集',
    `device_type`   varchar(32)    NOT NULL                                    DEFAULT '' COMMENT '设备类型',
    `name`      char(64)   DEFAULT NULL COMMENT '资源名称',
    `dev_serial_num`   varchar(128)                                                  DEFAULT '' COMMENT '设备序列号',
    `index_code`       varchar(64)  DEFAULT '' COMMENT '资源唯一编码',
    `ip`  varchar(32) DEFAULT '' COMMENT '上次监控值',
    `manufacturer`       varchar(32)                                         DEFAULT '' COMMENT '厂商',
    net_zone_id bigint default 0 comment '网域',
    region_index_code varchar(64) default '' comment '所属区域编码',
    resource_type varchar(64) default '' comment '资源类型，encodeDevice：编码设备',
    treaty_type varchar(64) default '' comment '接入协议',
    chan_num varchar(64) default '' comment '通道号，为级联监控点时，该字段为空；本级监控点时，该字段非空',
    cascade_code varchar(64) default '' comment '级联编号',
    camera_index_code varchar(64) default '' comment '监控点唯一标识',
    camera_name varchar(64) default '' comment '监控点名称',
    channel_no varchar(64) default '' comment '通道编号',
    channel_type varchar(64) default '' comment '通道类型',
    parent_index_code varchar(64) default '' comment '父级资源编号',
    longitude varchar(24) default '' comment '经度，精确到小数点后8位',
    latitude varchar(24) default '' comment '纬度，精确到小数点后8位',
    camera_type int default '' comment '监控点类型枪机0半球1快球2带云台枪机3',
    record_location varchar(64) default '' comment '录像存储位置',
    install_location varchar(64) default '' comment '录像存储位置',
    region_name  varchar(128) default '' comment '所属区域路径，由唯一标示组成，最大10级，格式： @根节点@子区域1@子区域2@',
     status   char(1) default 0 comment '状态0 正常 1 异常',
    `create_by`   varchar(64)                                                  DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                     DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64)                                                  DEFAULT '' COMMENT '更新者',
    `update_time` timestamp NOT NULL                                           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `remark`      varchar(255)                                                 DEFAULT NULL COMMENT '备注',
    primary key (id),
    key(index_code)
) engine = innodb