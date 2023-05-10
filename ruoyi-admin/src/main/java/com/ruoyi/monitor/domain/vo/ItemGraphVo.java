package com.ruoyi.monitor.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ItemGraphVo {
    private Long itemId;
    private String counter;
    private Long value;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date clock;
}
