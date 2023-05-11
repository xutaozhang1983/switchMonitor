package com.ruoyi.monitor.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.monitor.domain.TbEvents;
import lombok.Data;

import java.util.Date;

@Data
public class TbEventsDto extends TbEvents {
    private  Long groupId;
    private String deviceIp;
    private String deviceName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
