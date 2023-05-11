package com.ruoyi.monitor.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报警信息对象 tb_events
 * 
 * @author ruoyi
 * @date 2023-05-09
 */
@Data
public class TbEvents extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;


    @Excel(name = "设备ID")
    private Long deviceId;

    /** 监控指标ID */
    @Excel(name = "监控指标ID")
    private Long itemId;

    /** 监控指标 */
    @Excel(name = "监控指标")
    private String itemName;

    /** 报警内容 */
    @Excel(name = "报警内容")
    private String alarmContent;

    /** 报警级别 */
    @Excel(name = "报警级别")
    private String alarmLevel;

    /** 报警发送次数 */
    @Excel(name = "报警发送次数")
    private Integer alarmSendTimes;

    /** 报警关闭时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报警关闭时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date closedAt;

    /** 报警关闭内容 */
    @Excel(name = "报警关闭内容")
    private String closedNote;

    /** 报警关闭人姓名 */
    @Excel(name = "报警关闭人姓名")
    private String closedUser;

    /** 状态（0正常 1异常） */
    @Excel(name = "状态", readConverterExp = "0=未关闭,1=已关闭")
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
    public void setAlarmContent(String alarmContent) 
    {
        this.alarmContent = alarmContent;
    }

    public String getAlarmContent() 
    {
        return alarmContent;
    }
    public void setAlarmLevel(String alarmLevel) 
    {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmLevel() 
    {
        return alarmLevel;
    }
    public void setAlarmSendTimes(Integer alarmSendTimes) 
    {
        this.alarmSendTimes = alarmSendTimes;
    }

    public Integer getAlarmSendTimes() 
    {
        return alarmSendTimes;
    }
    public void setClosedAt(Date closedAt) 
    {
        this.closedAt = closedAt;
    }

    public Date getClosedAt() 
    {
        return closedAt;
    }
    public void setClosedNote(String closedNote) 
    {
        this.closedNote = closedNote;
    }

    public String getClosedNote() 
    {
        return closedNote;
    }
    public void setClosedUser(String closedUser) 
    {
        this.closedUser = closedUser;
    }

    public String getClosedUser() 
    {
        return closedUser;
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
            .append("itemId", getItemId())
            .append("itemName", getItemName())
            .append("alarmContent", getAlarmContent())
            .append("alarmLevel", getAlarmLevel())
            .append("alarmSendTimes", getAlarmSendTimes())
            .append("closedAt", getClosedAt())
            .append("closedNote", getClosedNote())
            .append("closedUser", getClosedUser())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
