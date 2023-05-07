package com.ruoyi.monitor.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备监控指标对象 tb_device_item_his
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
public class TbDeviceItemHis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 主机IP */
    @Excel(name = "主机IP")
    private Long deviceId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long itemId;

    /** 监控指标名称 */
    @Excel(name = "监控指标名称")
    private String itemName;

    /** 监控值 */
    @Excel(name = "监控值")
    private String value;

    /** 收集时间 */
    @Excel(name = "收集时间")
    private Long clock;

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
    public void setItemId(Long itemId) 
    {
        this.itemId = itemId;
    }

    public Long getItemId() 
    {
        return itemId;
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
    public void setClock(Long clock) 
    {
        this.clock = clock;
    }

    public Long getClock() 
    {
        return clock;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceId", getDeviceId())
            .append("itemId", getItemId())
            .append("itemName", getItemName())
            .append("value", getValue())
            .append("clock", getClock())
            .append("createTime", getCreateTime())
            .toString();
    }
}
