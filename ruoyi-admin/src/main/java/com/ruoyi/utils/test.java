package com.ruoyi.utils;

import com.ruoyi.common.utils.des.DesUtil;
import com.ruoyi.framework.web.domain.Server;


public class test {


    public static void main(String[] args) throws Exception {
//        TbDevice device = new TbDevice();
//        device.setPortNum(161);
//        device.setDeviceIp("192.168.0.240");
//        device.setSnmpVersion(1);
//        device.setSnmpCommunity("Public");
//        SnmpDeviceData snmpConfig = new SnmpDeviceData(device);
//        System.out.println(snmpConfig.acquireCpuMem("Huawei"));
        Server server = new Server();
        server.copyTo();
        String data = "abcd";
        String key = "18oRJtbLy7ied4Nl";
        System.out.println(key+"---"+DesUtil.encryptToHex(data,key));
        String enStr = "";
        System.out.println(DesUtil.decryptFormHex("21f8bd61629eee27",key));
    }
}
