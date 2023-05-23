package com.ruoyi.utils;

import com.ruoyi.common.utils.StringUtils;

import java.text.DecimalFormat;

public class CommonUtils {

    public static String convertTraffic(String bytes) {
        Double value = Double.valueOf(bytes) / 1024;
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(value);
    }
    public static Double int2Double(int value){
        return int2Double(value,1);
    }
    public static Double int2Double(int value,int divide){
        if(value ==0){
            return Double.parseDouble("0.0");
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String res = decimalFormat.format((float) value / (float) divide);
        return Double.parseDouble(res);
    }


    public static void main(String[] args) {
        System.out.println(convertTraffic("2000"));
    }



}
