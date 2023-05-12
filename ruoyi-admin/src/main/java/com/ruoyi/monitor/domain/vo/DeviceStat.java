package com.ruoyi.monitor.domain.vo;

import lombok.Data;

@Data
public class DeviceStat {
    private Integer all=0;
    private Integer ok=0;
    private Integer err=0;
    private Integer unknow=0;
}
