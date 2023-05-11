package com.ruoyi.monitor.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 事件信息对象 tb_alarm_event
 * 
 * @author ruoyi
 * @date 2023-05-09
 */

@Data
public class TbAlarmEvent extends BaseEntity{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "事件ID")
    private Long eventId;

    private String sendType;

    private String receiveAddr;

    /** 接收人 */
    @Excel(name = "接收人")
    private String userName;

    /** 状态（0正常 1异常） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    private String status;

}
