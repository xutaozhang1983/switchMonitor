package com.ruoyi.monitor.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class ItemGraphVo {

    private static final long serialVersionUID = 1L;


    private Long itemId;

    private String counter;

    private Long value;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date clock;

    private String itemName;

    private String deviceIp;

    private String deviceName;
}
