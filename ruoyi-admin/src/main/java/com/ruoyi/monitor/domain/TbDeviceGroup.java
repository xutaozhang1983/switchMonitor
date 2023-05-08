package com.ruoyi.monitor.domain;

import com.ruoyi.common.xss.Xss;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.Size;

/**
 * 设备列对象 tb_device_group
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@Data
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

    @Xss(message = "设备组不能包含脚本字符")
    @Size(min = 0, max = 30, message = "设备组长度不能超过30个字符")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
