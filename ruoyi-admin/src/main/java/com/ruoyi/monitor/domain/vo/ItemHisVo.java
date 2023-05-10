package com.ruoyi.monitor.domain.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ItemHisVo {
    private Long itemId;
    private String counter;


    private Long value;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date clock;
}
