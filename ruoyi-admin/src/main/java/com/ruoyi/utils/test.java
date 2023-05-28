package com.ruoyi.utils;

import cn.hutool.core.net.NetUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.des.DesUtil;
import com.ruoyi.common.utils.sign.Md5Utils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.web.domain.Server;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.service.ISysLicenseService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;


public class test {



    public static void main(String[] args) throws Exception {
//        TbDevice device = new TbDevice();
//        device.setPortNum(161);
//        device.setDeviceIp("192.168.0.240");
//        device.setSnmpVersion(1);
//        device.setSnmpCommunity("Public");
//        SnmpDeviceData snmpConfig = new SnmpDeviceData(device);
//        System.out.println(snmpConfig.acquireCpuMem("Huawei"));
        String key = DesUtil.generateMD5(NetUtil.getMacAddress(NetUtil.getLocalhost()));
        System.out.println(key);

        String data = "{\"GUID\":\"f4135e2c-ac06-9cf4-99e9-19206db83272\",\"CUSTOM\":\"SoftStar\",\"EXPIRES\":\"2023-06-25\",\"DEVICES\":\"50\",\"STAR\":\"2023-05-01\"}";
        System.out.println(DesUtil.AESEncrypt(data,key));
        System.out.println(DesUtil.AESDecrypt(DesUtil.AESEncrypt(data,key),key));

//        System.out.println(NetUtil.getMacAddress(InetAddress.getLocalHost()));
    }

}
