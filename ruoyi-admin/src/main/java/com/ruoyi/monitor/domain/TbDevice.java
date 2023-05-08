package com.ruoyi.monitor.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备列对象 tb_device
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@Data
public class TbDevice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long id;

    /** 组ID */
    @Excel(name = "组ID")
    private Long groupId;

    /** 主机名 */
    @Excel(name = "主机名")
    private String deviceName;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String deviceType;



    /** 设备IP */
    @Excel(name = "设备IP")
    private String deviceIp;
    @Excel(name = "设备型号")
    private String device_model;

    /** 协议 */
    @Excel(name = "协议")
    private String protocol;

    /** $column.columnComment */
    @Excel(name = "snmpCommunity")
    private String snmpCommunity;

    /** $column.columnComment */
    @Excel(name = "snmp版本")
    private Integer snmpVersion;

    /** $column.columnComment */
    @Excel(name = "snmp端口")
    private Integer snmpPort;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String snmpUsername;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String snmpPassword;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String snmpAuthProtocol;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String snmpPrivPassphrase;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String snmpPrivProtocol;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String snmpContext;

    /** $column.columnComment */
    @Excel(name = "端口数量")
    private Integer portNum;

    /** 制造商 */
    @Excel(name = "制造商")
    private String manufacturer;

    /** 状态（0正常 1异常） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    private String status;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Integer enable;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long uptime;

    private String username;
    private String passwd;
    private String appKey;
    private String appSecret;

}
