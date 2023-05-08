package com.ruoyi.monitor.domain.dto;

import com.ruoyi.monitor.domain.TbDeviceGroup;
import lombok.Data;

@Data
public class DeviceGroupDTO extends TbDeviceGroup {

    private Long deviceCount;
}
