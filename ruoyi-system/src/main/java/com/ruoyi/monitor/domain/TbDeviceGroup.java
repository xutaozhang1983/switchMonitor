package com.ruoyi.monitor.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备列对象 tb_device_group
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
public class TbDeviceGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String groupName;

    /** 报警接收用户组，多个逗号隔开 */
    @Excel(name = "报警接收用户组，多个逗号隔开")
    private String alarmUserGrpId;

    /** 报警发送次数 */
    @Excel(name = "报警发送次数")
    private Integer alarmMaxTimes;

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
    public void setGroupName(String groupName) 
    {
        this.groupName = groupName;
    }

    public String getGroupName() 
    {
        return groupName;
    }
    public void setAlarmUserGrpId(String alarmUserGrpId) 
    {
        this.alarmUserGrpId = alarmUserGrpId;
    }

    public String getAlarmUserGrpId() 
    {
        return alarmUserGrpId;
    }
    public void setAlarmMaxTimes(Integer alarmMaxTimes) 
    {
        this.alarmMaxTimes = alarmMaxTimes;
    }

    public Integer getAlarmMaxTimes() 
    {
        return alarmMaxTimes;
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
            .append("groupName", getGroupName())
            .append("alarmUserGrpId", getAlarmUserGrpId())
            .append("alarmMaxTimes", getAlarmMaxTimes())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
