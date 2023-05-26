package com.ruoyi.monitor.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 编码设备对象 tb_device_encode
 * 
 * @author ruoyi
 * @date 2023-05-25
 */
public class TbDeviceEncode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 能力集 */
    @Excel(name = "能力集")
    private String capability;

    /** 设备系列 */
    @Excel(name = "设备系列")
    private String deviceType;

    /** 设备序列号 */
    @Excel(name = "设备序列号")
    private String devSerialNum;

    /** 主动设备编号 */
    @Excel(name = "主动设备编号")
    private String deviceCode;

    /** 资源唯一编码 */
    @Excel(name = "资源唯一编码")
    private String indexCode;

    /** 厂商 */
    @Excel(name = "厂商")
    private String manufacturer;

    /** 资源名称 */
    @Excel(name = "资源名称")
    private String name;

    /** 所属区域 */
    @Excel(name = "所属区域")
    private String regionIndexCode;

    /** 资源类型 */
    @Excel(name = "资源类型")
    private String resourceType;

    /** 接入协议 */
    @Excel(name = "接入协议")
    private String treatyType;

    /** 0 ok 1 failed */
    @Excel(name = "0 ok 1 failed")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCapability(String capability) 
    {
        this.capability = capability;
    }

    public String getCapability() 
    {
        return capability;
    }
    public void setDeviceType(String deviceType) 
    {
        this.deviceType = deviceType;
    }

    public String getDeviceType() 
    {
        return deviceType;
    }
    public void setDevSerialNum(String devSerialNum) 
    {
        this.devSerialNum = devSerialNum;
    }

    public String getDevSerialNum() 
    {
        return devSerialNum;
    }
    public void setDeviceCode(String deviceCode) 
    {
        this.deviceCode = deviceCode;
    }

    public String getDeviceCode() 
    {
        return deviceCode;
    }
    public void setIndexCode(String indexCode) 
    {
        this.indexCode = indexCode;
    }

    public String getIndexCode() 
    {
        return indexCode;
    }
    public void setManufacturer(String manufacturer) 
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setRegionIndexCode(String regionIndexCode) 
    {
        this.regionIndexCode = regionIndexCode;
    }

    public String getRegionIndexCode() 
    {
        return regionIndexCode;
    }
    public void setResourceType(String resourceType) 
    {
        this.resourceType = resourceType;
    }

    public String getResourceType() 
    {
        return resourceType;
    }
    public void setTreatyType(String treatyType) 
    {
        this.treatyType = treatyType;
    }

    public String getTreatyType() 
    {
        return treatyType;
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
            .append("capability", getCapability())
            .append("deviceType", getDeviceType())
            .append("devSerialNum", getDevSerialNum())
            .append("deviceCode", getDeviceCode())
            .append("indexCode", getIndexCode())
            .append("manufacturer", getManufacturer())
            .append("name", getName())
            .append("regionIndexCode", getRegionIndexCode())
            .append("resourceType", getResourceType())
            .append("treatyType", getTreatyType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
