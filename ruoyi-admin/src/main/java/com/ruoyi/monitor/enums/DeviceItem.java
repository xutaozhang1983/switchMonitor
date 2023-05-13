package com.ruoyi.monitor.enums;

import java.util.ArrayList;
import java.util.List;

public enum DeviceItem {
    CPU("CPU",1L),
    MEM("Memory",1L),
    IFIN("ifIn",0L),
    IFOUT("ifOut",0L);
    private String item;
    private Long isPort;
    public String getItem(){
        return this.item;
    }

    public Long getIsPort() {
        return isPort;
    }

    DeviceItem(String item, Long isPort) {
        this.item = item;
        this.isPort = isPort;
    }
    public static Long getIsPortByItem(String item) {
        for (DeviceItem deviceItem : DeviceItem.values()) {
            if (deviceItem.getItem().equals(item)) {
                return deviceItem.getIsPort();
            }
        }
        return 0L;
    }

    public static List<String> flowCounters(){
        List<String> counters = new ArrayList<>();
        counters.add(DeviceItem.IFIN.getItem());
        counters.add(DeviceItem.IFOUT.getItem());
        return counters;
    }
    public static void main(String[] args) {
        System.out.println(getIsPortByItem("CPU"));
    }
}
