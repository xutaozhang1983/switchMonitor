package com.ruoyi.monitor.enums;

public enum DeviceItem {
    CPU("CPU",1L),
    MEM("Memory",1L);

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
}
