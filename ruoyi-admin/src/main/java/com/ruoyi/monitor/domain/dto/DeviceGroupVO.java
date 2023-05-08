package com.ruoyi.monitor.domain.dto;

import com.ruoyi.monitor.domain.TbDeviceGroup;
import lombok.Data;

@Data
public class DeviceGroupVO extends TbDeviceGroup {

    private Long deviceCount;
}
