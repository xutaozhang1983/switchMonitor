package com.ruoyi.utils;

public class TrafficUtils {

    public static String convertTraffic(String bytes) {

        Long value = Long.valueOf(bytes) / 8;
        String[] sizes = {"B", "KB", "MB", "GB", "TB"};
        int order = 0;
        while (value >= 1024 && order < sizes.length - 1) {
            order++;
            value /= 1024;
        }
        return String.format("%d %s", bytes, sizes[order]);
    }
}
