package com.ruoyi.monitor.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.monitor.domain.TbAlarmEvent;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Date;

@Data
public class AlarmEventDto extends TbAlarmEvent {
    @ApiModelProperty("设备ID")
    private Long deviceId;
    @ApiModelProperty("监控指标ID")
    private Long itemId;
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
