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
    private String deviceModel;

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
    @Excel(name = "Snmp用户")
    private String snmpUsername;

    @Excel(name = "Snmp密码")
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
    @Excel(name = "启动时间")
    private String uptime;

    private String username;
    private String passwd;
    private String appKey;
    private String appSecret;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSnmpCommunity() {
        return snmpCommunity;
    }

    public void setSnmpCommunity(String snmpCommunity) {
        this.snmpCommunity = snmpCommunity;
    }

    public Integer getSnmpVersion() {
        return snmpVersion;
    }

    public void setSnmpVersion(Integer snmpVersion) {
        this.snmpVersion = snmpVersion;
    }

    public Integer getSnmpPort() {
        return snmpPort;
    }

    public void setSnmpPort(Integer snmpPort) {
        this.snmpPort = snmpPort;
    }

    public String getSnmpUsername() {
        return snmpUsername;
    }

    public void setSnmpUsername(String snmpUsername) {
        this.snmpUsername = snmpUsername;
    }

    public String getSnmpPassword() {
        return snmpPassword;
    }

    public void setSnmpPassword(String snmpPassword) {
        this.snmpPassword = snmpPassword;
    }

    public String getSnmpAuthProtocol() {
        return snmpAuthProtocol;
    }

    public void setSnmpAuthProtocol(String snmpAuthProtocol) {
        this.snmpAuthProtocol = snmpAuthProtocol;
    }

    public String getSnmpPrivPassphrase() {
        return snmpPrivPassphrase;
    }

    public void setSnmpPrivPassphrase(String snmpPrivPassphrase) {
        this.snmpPrivPassphrase = snmpPrivPassphrase;
    }

    public String getSnmpPrivProtocol() {
        return snmpPrivProtocol;
    }

    public void setSnmpPrivProtocol(String snmpPrivProtocol) {
        this.snmpPrivProtocol = snmpPrivProtocol;
    }

    public String getSnmpContext() {
        return snmpContext;
    }

    public void setSnmpContext(String snmpContext) {
        this.snmpContext = snmpContext;
    }

    public Integer getPortNum() {
        return portNum;
    }

    public void setPortNum(Integer portNum) {
        this.portNum = portNum;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
