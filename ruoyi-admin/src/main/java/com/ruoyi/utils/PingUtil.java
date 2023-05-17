package com.ruoyi.utils;

import com.ruoyi.framework.web.domain.server.Sys;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class PingUtil {
    private static int timeOut = 2000;
    private static int maxTries = 2;

    private static boolean status = false;
    public static boolean ping(String ipAddress){

        int tries = 0;
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            address.getHostAddress();
            while (tries < maxTries) {
                 status = address.isReachable(timeOut);
                if (status) {
                    return true;
                }
                tries++;
            }
            if (!status){
                return pingReachable(address.getHostAddress());
            }

        }catch (Exception e){
           log.error(ipAddress+":"+e);
        }
        return false;
    }
    private static final boolean pingReachable(String hostname) {
        try {
            return 0 == Runtime.getRuntime().exec("ping -c 2 " + hostname).waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        log.error(new Date());
        int i = 1;
        while (true){
            if (i >=50){
                log.error("50");
                break;
            }
            System.out.println(ping("192.168.0.241"));
            i++;

        }

    }
}
