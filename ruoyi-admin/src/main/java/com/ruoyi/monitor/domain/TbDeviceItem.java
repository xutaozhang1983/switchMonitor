package com.ruoyi.monitor.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 设备监控指标对象 tb_device_item
 * 
 * @author ruoyi
 * @date 2023-05-07
 */

public class TbDeviceItem
{
    private static final long serialVersionUID = 1L;


    private Long id;

    /** 主机IP */
    @Excel(name = "主机id")
    private Long deviceId;

    /** 监控指标名称 */
    @Excel(name = "监控指标名称")
    private String itemName;

    /** 监控值 */
    @Excel(name = "监控值")
    private String value;

    /** 上次监控值 */
    @Excel(name = "上次监控值")
    private String lastValue;

    /** 收集时间 */
    @Excel(name = "收集时间")
    private Long clock;

    /** 是否是端口 */
    private Long isPort;
    /** 表达式 */
    private String express;

    private String counter;
    private String alarmValue;
    /** 状态（0正常 1异常） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    private String status;
    /** 收集时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLastValue() {
        return lastValue;
    }

    public void setLastValue(String lastValue) {
        this.lastValue = lastValue;
    }

    public Long getClock() {
        return clock;
    }

    public void setClock(Long clock) {
        this.clock = clock;
    }

    public Long getIsPort() {
        return isPort;
    }

    public void setIsPort(Long isPort) {
        this.isPort = isPort;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getAlarmValue() {
        return alarmValue;
    }

    public void setAlarmValue(String alarmValue) {
        this.alarmValue = alarmValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
