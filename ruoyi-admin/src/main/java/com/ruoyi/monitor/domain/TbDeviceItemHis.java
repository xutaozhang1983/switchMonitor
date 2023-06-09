package com.ruoyi.monitor.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 设备监控指标对象 tb_device_item_his
 * 
 * @author ruoyi
 * @date 2023-05-07
 */

@Data
public class TbDeviceItemHis
{
    private static final long serialVersionUID = 1L;


    private Long id;

    /** 主机IP */
    @Excel(name = "主机IP")
    private Long deviceId;

    @Excel(name = "itemId")
    private Long itemId;

    /** 监控指标名称 */
    @Excel(name = "监控指标名称")
    private String itemName;

    @Excel(name = "counter")
    private String counter;
    /** 监控值 */
    @Excel(name = "监控值")
    private Long value;

    /** 收集时间 */
    @Excel(name = "收集时间")
    private Long clock;
}
