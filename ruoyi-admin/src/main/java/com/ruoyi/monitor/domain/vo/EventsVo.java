package com.ruoyi.monitor.domain.vo;

import com.ruoyi.monitor.domain.TbEvents;
import lombok.Data;

@Data
public class EventsVo extends TbEvents {
    private String groupName;
    private String deviceName;

}
