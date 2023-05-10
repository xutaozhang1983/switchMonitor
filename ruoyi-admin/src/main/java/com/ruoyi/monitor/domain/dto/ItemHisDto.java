package com.ruoyi.monitor.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ItemHisDto {
    private Long deviceId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startClock;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endClock;
    private Long itemId;
    private String[] counters;
    private Long step;
}
