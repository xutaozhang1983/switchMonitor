package com.ruoyi.monitor.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 摄像头对象 tb_device_camera
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
public class TbDeviceCamera extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 	海拔 */
    @Excel(name = "	海拔")
    private String altitude;

    /** 	监控点唯一标识 */
    @Excel(name = "	监控点唯一标识")
    private String cameraIndexCode;

    /** 监控点名称 */
    @Excel(name = "监控点名称")
    private String cameraName;

    /** 监控点类型 */
    @Excel(name = "监控点类型")
    private Long cameraType;

    /** 监控点类型说明 */
    @Excel(name = "监控点类型说明")
    private String cameraTypeName;

    /** 能力集 */
    @Excel(name = "能力集")
    private String capabilitySet;

    /** 能力集说明 */
    @Excel(name = "能力集说明")
    private String capabilitySetName;

    /** 智能分析能力集 */
    @Excel(name = "智能分析能力集")
    private String intelligentSet;

    /** 智能分析能力集 */
    @Excel(name = "智能分析能力集")
    private String intelligentSetName;

    /** 通道编号 */
    @Excel(name = "通道编号")
    private String channelNo;

    /** 通道类型 */
    @Excel(name = "通道类型")
    private String channelType;

    /** 通道子类型说明 */
    @Excel(name = "通道子类型说明")
    private String channelTypeName;

    /** 所属编码设备唯一标识 */
    @Excel(name = "所属编码设备唯一标识")
    private String encodeDevIndexCode;

    /** 所属设备类型（详见数据字典， */
    @Excel(name = "所属设备类型", readConverterExp = "所属设备类型（详见数据字典，")
    private String encodeDevResourceType;

    /** 所属设备类型说明 */
    @Excel(name = "所属设备类型说明")
    private String encodeDevResourceTypeName;

    /** 监控点国标编号 */
    @Excel(name = "监控点国标编号")
    private String gbIndexCode;

    /** 安装位置 */
    @Excel(name = "安装位置")
    private String installLocation;

    /** 键盘控制码 */
    @Excel(name = "键盘控制码")
    private String keyBoardCode;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 1-普通像素，2-130万高清，3-200万高清，4-300万高清 */
    @Excel(name = "1-普通像素，2-130万高清，3-200万高清，4-300万高清")
    private Long pixel;

    /** 1-全方位云台（带转动和变焦），2-只有变焦,不带转动，3-只有转动，不带变焦，4-无云台，无变焦 */
    @Excel(name = "1-全方位云台", readConverterExp = "带=转动和变焦")
    private Long ptz;

    /** 1-DVR，2-模拟矩阵，3-MU4000，4-NC600 */
    @Excel(name = "1-DVR，2-模拟矩阵，3-MU4000，4-NC600")
    private Long ptzController;

    /** 云台控制说明 */
    @Excel(name = "云台控制说明")
    private String ptzControllerName;

    /** 云镜类型说明 */
    @Excel(name = "云镜类型说明")
    private String ptzName;

    /** 0-中心存储，1-设备存储 */
    @Excel(name = "0-中心存储，1-设备存储")
    private String recordLocation;

    /** 录像存储位置说明 */
    @Excel(name = "录像存储位置说明")
    private String recordLocationName;

    /** 所属区域唯一标识 */
    @Excel(name = "所属区域唯一标识")
    private String regionIndexCode;

    /** 0-不在线，1-在线 */
    @Excel(name = "0-不在线，1-在线")
    private Long status;

    /** 状态说明 */
    @Excel(name = "状态说明")
    private String statusName;

    /** 传输协议（0-UDP，1-TCP） */
    @Excel(name = "传输协议", readConverterExp = "0=-UDP，1-TCP")
    private Long transType;

    /** 传输协议类型说明 */
    @Excel(name = "传输协议类型说明")
    private String transTypeName;

    /** 接入协议 */
    @Excel(name = "接入协议")
    private String treatyType;

    /** 接入协议类型说明 */
    @Excel(name = "接入协议类型说明")
    private String treatyTypeName;

    /** 可视域相关 JSON格式 */
    @Excel(name = "可视域相关 JSON格式")
    private String viewshed;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAltitude(String altitude) 
    {
        this.altitude = altitude;
    }

    public String getAltitude() 
    {
        return altitude;
    }
    public void setCameraIndexCode(String cameraIndexCode) 
    {
        this.cameraIndexCode = cameraIndexCode;
    }

    public String getCameraIndexCode() 
    {
        return cameraIndexCode;
    }
    public void setCameraName(String cameraName) 
    {
        this.cameraName = cameraName;
    }

    public String getCameraName() 
    {
        return cameraName;
    }
    public void setCameraType(Long cameraType) 
    {
        this.cameraType = cameraType;
    }

    public Long getCameraType() 
    {
        return cameraType;
    }
    public void setCameraTypeName(String cameraTypeName) 
    {
        this.cameraTypeName = cameraTypeName;
    }

    public String getCameraTypeName() 
    {
        return cameraTypeName;
    }
    public void setCapabilitySet(String capabilitySet) 
    {
        this.capabilitySet = capabilitySet;
    }

    public String getCapabilitySet() 
    {
        return capabilitySet;
    }
    public void setCapabilitySetName(String capabilitySetName) 
    {
        this.capabilitySetName = capabilitySetName;
    }

    public String getCapabilitySetName() 
    {
        return capabilitySetName;
    }
    public void setIntelligentSet(String intelligentSet) 
    {
        this.intelligentSet = intelligentSet;
    }

    public String getIntelligentSet() 
    {
        return intelligentSet;
    }
    public void setIntelligentSetName(String intelligentSetName) 
    {
        this.intelligentSetName = intelligentSetName;
    }

    public String getIntelligentSetName() 
    {
        return intelligentSetName;
    }
    public void setChannelNo(String channelNo) 
    {
        this.channelNo = channelNo;
    }

    public String getChannelNo() 
    {
        return channelNo;
    }
    public void setChannelType(String channelType) 
    {
        this.channelType = channelType;
    }

    public String getChannelType() 
    {
        return channelType;
    }
    public void setChannelTypeName(String channelTypeName) 
    {
        this.channelTypeName = channelTypeName;
    }

    public String getChannelTypeName() 
    {
        return channelTypeName;
    }
    public void setEncodeDevIndexCode(String encodeDevIndexCode) 
    {
        this.encodeDevIndexCode = encodeDevIndexCode;
    }

    public String getEncodeDevIndexCode() 
    {
        return encodeDevIndexCode;
    }
    public void setEncodeDevResourceType(String encodeDevResourceType) 
    {
        this.encodeDevResourceType = encodeDevResourceType;
    }

    public String getEncodeDevResourceType() 
    {
        return encodeDevResourceType;
    }
    public void setEncodeDevResourceTypeName(String encodeDevResourceTypeName) 
    {
        this.encodeDevResourceTypeName = encodeDevResourceTypeName;
    }

    public String getEncodeDevResourceTypeName() 
    {
        return encodeDevResourceTypeName;
    }
    public void setGbIndexCode(String gbIndexCode) 
    {
        this.gbIndexCode = gbIndexCode;
    }

    public String getGbIndexCode() 
    {
        return gbIndexCode;
    }
    public void setInstallLocation(String installLocation) 
    {
        this.installLocation = installLocation;
    }

    public String getInstallLocation() 
    {
        return installLocation;
    }
    public void setKeyBoardCode(String keyBoardCode) 
    {
        this.keyBoardCode = keyBoardCode;
    }

    public String getKeyBoardCode() 
    {
        return keyBoardCode;
    }
    public void setLatitude(String latitude) 
    {
        this.latitude = latitude;
    }

    public String getLatitude() 
    {
        return latitude;
    }
    public void setLongitude(String longitude) 
    {
        this.longitude = longitude;
    }

    public String getLongitude() 
    {
        return longitude;
    }
    public void setPixel(Long pixel) 
    {
        this.pixel = pixel;
    }

    public Long getPixel() 
    {
        return pixel;
    }
    public void setPtz(Long ptz) 
    {
        this.ptz = ptz;
    }

    public Long getPtz() 
    {
        return ptz;
    }
    public void setPtzController(Long ptzController) 
    {
        this.ptzController = ptzController;
    }

    public Long getPtzController() 
    {
        return ptzController;
    }
    public void setPtzControllerName(String ptzControllerName) 
    {
        this.ptzControllerName = ptzControllerName;
    }

    public String getPtzControllerName() 
    {
        return ptzControllerName;
    }
    public void setPtzName(String ptzName) 
    {
        this.ptzName = ptzName;
    }

    public String getPtzName() 
    {
        return ptzName;
    }
    public void setRecordLocation(String recordLocation) 
    {
        this.recordLocation = recordLocation;
    }

    public String getRecordLocation() 
    {
        return recordLocation;
    }
    public void setRecordLocationName(String recordLocationName) 
    {
        this.recordLocationName = recordLocationName;
    }

    public String getRecordLocationName() 
    {
        return recordLocationName;
    }
    public void setRegionIndexCode(String regionIndexCode) 
    {
        this.regionIndexCode = regionIndexCode;
    }

    public String getRegionIndexCode() 
    {
        return regionIndexCode;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setStatusName(String statusName) 
    {
        this.statusName = statusName;
    }

    public String getStatusName() 
    {
        return statusName;
    }
    public void setTransType(Long transType) 
    {
        this.transType = transType;
    }

    public Long getTransType() 
    {
        return transType;
    }
    public void setTransTypeName(String transTypeName) 
    {
        this.transTypeName = transTypeName;
    }

    public String getTransTypeName() 
    {
        return transTypeName;
    }
    public void setTreatyType(String treatyType) 
    {
        this.treatyType = treatyType;
    }

    public String getTreatyType() 
    {
        return treatyType;
    }
    public void setTreatyTypeName(String treatyTypeName) 
    {
        this.treatyTypeName = treatyTypeName;
    }

    public String getTreatyTypeName() 
    {
        return treatyTypeName;
    }
    public void setViewshed(String viewshed) 
    {
        this.viewshed = viewshed;
    }

    public String getViewshed() 
    {
        return viewshed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("altitude", getAltitude())
            .append("cameraIndexCode", getCameraIndexCode())
            .append("cameraName", getCameraName())
            .append("cameraType", getCameraType())
            .append("cameraTypeName", getCameraTypeName())
            .append("capabilitySet", getCapabilitySet())
            .append("capabilitySetName", getCapabilitySetName())
            .append("intelligentSet", getIntelligentSet())
            .append("intelligentSetName", getIntelligentSetName())
            .append("channelNo", getChannelNo())
            .append("channelType", getChannelType())
            .append("channelTypeName", getChannelTypeName())
            .append("encodeDevIndexCode", getEncodeDevIndexCode())
            .append("encodeDevResourceType", getEncodeDevResourceType())
            .append("encodeDevResourceTypeName", getEncodeDevResourceTypeName())
            .append("gbIndexCode", getGbIndexCode())
            .append("installLocation", getInstallLocation())
            .append("keyBoardCode", getKeyBoardCode())
            .append("latitude", getLatitude())
            .append("longitude", getLongitude())
            .append("pixel", getPixel())
            .append("ptz", getPtz())
            .append("ptzController", getPtzController())
            .append("ptzControllerName", getPtzControllerName())
            .append("ptzName", getPtzName())
            .append("recordLocation", getRecordLocation())
            .append("recordLocationName", getRecordLocationName())
            .append("regionIndexCode", getRegionIndexCode())
            .append("status", getStatus())
            .append("statusName", getStatusName())
            .append("transType", getTransType())
            .append("transTypeName", getTransTypeName())
            .append("treatyType", getTreatyType())
            .append("treatyTypeName", getTreatyTypeName())
            .append("viewshed", getViewshed())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
