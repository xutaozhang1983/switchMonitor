package com.ruoyi.monitor.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备监控指标对象 tb_device_item
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
public class TbDeviceItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 主机IP */
    @Excel(name = "主机IP")
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

    /** 状态（0正常 1异常） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDeviceId(Long deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() 
    {
        return deviceId;
    }
    public void setItemName(String itemName) 
    {
        this.itemName = itemName;
    }

    public String getItemName() 
    {
        return itemName;
    }
    public void setValue(String value) 
    {
        this.value = value;
    }

    public String getValue() 
    {
        return value;
    }
    public void setLastValue(String lastValue) 
    {
        this.lastValue = lastValue;
    }

    public String getLastValue() 
    {
        return lastValue;
    }
    public void setClock(Long clock) 
    {
        this.clock = clock;
    }

    public Long getClock() 
    {
        return clock;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceId", getDeviceId())
            .append("itemName", getItemName())
            .append("value", getValue())
            .append("lastValue", getLastValue())
            .append("clock", getClock())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .toString();
    }
}
