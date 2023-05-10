package com.ruoyi.utils;

import java.text.DecimalFormat;

public class CommonUtils {

    public static String convertTraffic(String bytes) {
        Double value = Double.valueOf(bytes) / 1024;
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(value);
    }


    public static void main(String[] args) {
        System.out.println(convertTraffic("2000"));
    }
}
