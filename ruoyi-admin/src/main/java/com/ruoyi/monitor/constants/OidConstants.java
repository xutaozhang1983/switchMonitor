package com.ruoyi.monitor.constants;

public class OidConstants {

    /**
     * 设备名
     * */
    public static final String sysDescr = ".1.3.6.1.2.1.1.5.0";

    public static String oidSysDescr = ".1.3.6.1.2.1.1.1.0"; // sysDescr OID
    /**
     * 设备时间（从打开设备时开始计算的时间）
     * */
    public static final String sysTime = "1.3.6.1.2.1.1.3.0";
    /**
     * 设备编号
     * */
    public static final String sysObjectID = "1.3.6.1.2.1.1.2.0";

    /**
     * CPU
     */
    public static final String CPU_HR_PROCESSOR_LOAD = "1.3.6.1.4.1.2011.6.3.4.1.4";


    /**
     * 物理内存总容量
     */
    public static final String MEMORY_TOTAL = "1.3.6.1.4.1.2021.4.5.0";
    /**
     * 物理内存空闲容量
     */
    public static final String MEMORY_AVAIL = "1.3.6.1.4.1.2021.4.6.0";
    /**
     * 物理内存缓冲
     */
    public static final String MEMORY_BUFFER = "1.3.6.1.4.1.2021.4.14.0";
    /**
     * 物理内存缓存内存
     */
    public static final String MEMORY_CACHE = "1.3.6.1.4.1.2021.4.15.0";

    /**
     * 端口数量
     * */
    public static final String IF_NUMBER = "1.3.6.1.2.1.2.1";

    public static final String IF_NUM = ".1.3.6.1.2.1.2.2.1.3";
    /**
     * 端口名称
     * */
    public static final String IF_DESCR = "1.3.6.1.2.1.2.2.1.2";
    /**
     * 端口入流量
     * */
    public static final String IF_HC_IN_OCTETS = "1.3.6.1.2.1.31.1.1.1.6";
    /**
     * 端口出流量
     * */
    public static final String IF_HC_OUT_OCTETS = "1.3.6.1.2.1.31.1.1.1.10";
    /**
     * 端口mac地址
     * */
    public static final String IF_PHYS_ADDRESS = "1.3.6.1.2.1.2.2.1.6";
    /**
     * 端口状态
     * */
    public static final String IF_OPER_STATUS = "1.3.6.1.2.1.2.2.1.8";
    /**
     * 端口上次状态
     * */
    public static final String IF_LAST_OPER_STATUS = "1.3.6.1.2.1.2.2.1.9";
    /**
     * 端口当前预估带宽
     * */
    public static final String IF_SPEED = "1.3.6.1.2.1.2.2.1.5";
}
