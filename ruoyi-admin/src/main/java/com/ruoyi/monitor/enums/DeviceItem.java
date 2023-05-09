package com.ruoyi.monitor.enums;

public enum DeviceItem {
    CPU("CPU"),
    MEM("Memory");

    private String item;

    public String getItem(){
        return this.item;
    }
    DeviceItem(String item) {
        this.item = item;
    }
}
