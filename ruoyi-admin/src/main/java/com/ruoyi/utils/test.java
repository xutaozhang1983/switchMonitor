package com.ruoyi.utils;

import com.ruoyi.common.utils.des.DesUtil;
import com.ruoyi.framework.web.domain.Server;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.service.ISysLicenseService;

import javax.crypto.SecretKey;


public class test {


    public static void main(String[] args) throws Exception {
//        TbDevice device = new TbDevice();
//        device.setPortNum(161);
//        device.setDeviceIp("192.168.0.240");
//        device.setSnmpVersion(1);
//        device.setSnmpCommunity("Public");
//        SnmpDeviceData snmpConfig = new SnmpDeviceData(device);
//        System.out.println(snmpConfig.acquireCpuMem("Huawei"));
        String key = "9kbx68MBvkeCSGGg";
        System.out.println(key);


        String data = "{\"GUID\":\"f4135e2c-ac06-9cf4-99e9-19206db83272\",\"CUSTOM\":\"SoftStar\",\"EXPIRES\":\"2023-06-25\",\"DEVICES\":\"50\"}";
        System.out.println(DesUtil.AESEncrypt(data,key));
        String aa = "Hcwhm79bSpd3sScTdSJsee2y4fhMP1OAmphQV254h0RSclXFxT5nFkIROmQMmBv0ja1MxofmmHCLe2J0XhaLM9pOUv0Dj8SO6O9WcD72Vh+bBVXEAKgABf/lEbbmnFgZ2baPGxQOpeqL1fDbXRUbgQ==";
        System.out.println(aa+"---"+DesUtil.AESDecrypt(aa,key));

    }
}
