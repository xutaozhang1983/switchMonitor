package com.ruoyi.monitor.domain;

import lombok.Data;
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

}
