package com.ruoyi.monitor.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.monitor.domain.TbAlarmEvent;
import lombok.Data;

@Data
public class AlarmEventVo extends TbAlarmEvent {
    @Excel(name = "设备名称")
    private String deviceName;
    @Excel(name = "设备IP")
    private String deviceIp;
    @Excel(name = "监控指标名称")
    private String itemName;
}
