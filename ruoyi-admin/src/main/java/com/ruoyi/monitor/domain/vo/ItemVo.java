package com.ruoyi.monitor.domain.vo;

import com.ruoyi.monitor.domain.TbDeviceItem;
import com.ruoyi.monitor.domain.TbDeviceItemHis;
import lombok.Data;

@Data
public class ItemVo extends TbDeviceItem {

    private Long ifIn;
    private Long ifOut;

    private String deviceIp;
    private String deviceName;
}
