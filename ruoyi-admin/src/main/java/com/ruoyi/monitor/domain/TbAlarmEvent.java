package com.ruoyi.monitor.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报警信息对象 tb_alarm_event
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@Data
public class TbAlarmEvent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
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
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    private String status;

}
