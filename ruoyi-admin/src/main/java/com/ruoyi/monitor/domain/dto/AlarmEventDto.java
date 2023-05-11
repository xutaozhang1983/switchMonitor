package com.ruoyi.monitor.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.monitor.domain.TbAlarmEvent;
import lombok.Data;

import java.util.Date;

@Data
public class AlarmEventDto extends TbAlarmEvent {
    private Long deviceId;
    private Long itemId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
