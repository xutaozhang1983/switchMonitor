package com.ruoyi.monitor.domain;

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
    private String hostname;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String deviceType;

    /** 设备IP */
    @Excel(name = "设备IP")
    private String deviceIp;

    /** 协议 */
    @Excel(name = "协议")
    private String protocol;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String snmpCommunity;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer snmpVersion;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
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
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String snmpEngineId;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }
    public void setHostname(String hostname) 
    {
        this.hostname = hostname;
    }

    public String getHostname() 
    {
        return hostname;
    }
    public void setDeviceType(String deviceType) 
    {
        this.deviceType = deviceType;
    }

    public String getDeviceType() 
    {
        return deviceType;
    }
    public void setDeviceIp(String deviceIp) 
    {
        this.deviceIp = deviceIp;
    }

    public String getDeviceIp() 
    {
        return deviceIp;
    }
    public void setProtocol(String protocol) 
    {
        this.protocol = protocol;
    }

    public String getProtocol() 
    {
        return protocol;
    }
    public void setSnmpCommunity(String snmpCommunity) 
    {
        this.snmpCommunity = snmpCommunity;
    }

    public String getSnmpCommunity() 
    {
        return snmpCommunity;
    }
    public void setSnmpVersion(Integer snmpVersion) 
    {
        this.snmpVersion = snmpVersion;
    }

    public Integer getSnmpVersion() 
    {
        return snmpVersion;
    }
    public void setSnmpPort(Integer snmpPort) 
    {
        this.snmpPort = snmpPort;
    }

    public Integer getSnmpPort() 
    {
        return snmpPort;
    }
    public void setSnmpUsername(String snmpUsername) 
    {
        this.snmpUsername = snmpUsername;
    }

    public String getSnmpUsername() 
    {
        return snmpUsername;
    }
    public void setSnmpPassword(String snmpPassword) 
    {
        this.snmpPassword = snmpPassword;
    }

    public String getSnmpPassword() 
    {
        return snmpPassword;
    }
    public void setSnmpAuthProtocol(String snmpAuthProtocol) 
    {
        this.snmpAuthProtocol = snmpAuthProtocol;
    }

    public String getSnmpAuthProtocol() 
    {
        return snmpAuthProtocol;
    }
    public void setSnmpPrivPassphrase(String snmpPrivPassphrase) 
    {
        this.snmpPrivPassphrase = snmpPrivPassphrase;
    }

    public String getSnmpPrivPassphrase() 
    {
        return snmpPrivPassphrase;
    }
    public void setSnmpPrivProtocol(String snmpPrivProtocol) 
    {
        this.snmpPrivProtocol = snmpPrivProtocol;
    }

    public String getSnmpPrivProtocol() 
    {
        return snmpPrivProtocol;
    }
    public void setSnmpContext(String snmpContext) 
    {
        this.snmpContext = snmpContext;
    }

    public String getSnmpContext() 
    {
        return snmpContext;
    }
    public void setSnmpEngineId(String snmpEngineId) 
    {
        this.snmpEngineId = snmpEngineId;
    }

    public String getSnmpEngineId() 
    {
        return snmpEngineId;
    }
    public void setManufacturer(String manufacturer) 
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setEnable(Integer enable) 
    {
        this.enable = enable;
    }

    public Integer getEnable() 
    {
        return enable;
    }
    public void setUptime(Long uptime) 
    {
        this.uptime = uptime;
    }

    public Long getUptime() 
    {
        return uptime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("groupId", getGroupId())
            .append("hostname", getHostname())
            .append("deviceType", getDeviceType())
            .append("deviceIp", getDeviceIp())
            .append("protocol", getProtocol())
            .append("snmpCommunity", getSnmpCommunity())
            .append("snmpVersion", getSnmpVersion())
            .append("snmpPort", getSnmpPort())
            .append("snmpUsername", getSnmpUsername())
            .append("snmpPassword", getSnmpPassword())
            .append("snmpAuthProtocol", getSnmpAuthProtocol())
            .append("snmpPrivPassphrase", getSnmpPrivPassphrase())
            .append("snmpPrivProtocol", getSnmpPrivProtocol())
            .append("snmpContext", getSnmpContext())
            .append("snmpEngineId", getSnmpEngineId())
            .append("manufacturer", getManufacturer())
            .append("status", getStatus())
            .append("enable", getEnable())
            .append("uptime", getUptime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
