package com.ruoyi.utils;

import com.ruoyi.monitor.domain.TbDevice;
import com.ruoyi.snmpUtils.SnmpDeviceData;


public class test {


    public static void main(String[] args) {
        TbDevice device = new TbDevice();
        device.setPortNum(161);
        device.setDeviceIp("192.168.0.240");
        device.setSnmpVersion(1);
        device.setSnmpCommunity("Public");
        SnmpDeviceData snmpConfig = new SnmpDeviceData(device);
        System.out.println(snmpConfig.acquireCpuMem("Huawei"));



    }
}
