package com.ruoyi.monitor.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.monitor.domain.TbDevice;
import lombok.Data;


/**
 * 设备列对象 tb_device
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@Data
public class DeviceVO extends TbDevice
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "主机组")
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
