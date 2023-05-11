package com.ruoyi.monitor.domain.dto;

import com.ruoyi.monitor.domain.TbAlarmEvent;
import lombok.Data;

@Data
public class AlarmEventDto extends TbAlarmEvent {
    private Long deviceId;
    private Long itemId;
}
