package com.ruoyi.utils;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.monitor.constants.OidConstants;
import com.ruoyi.monitor.constants.OidEnum;
import com.ruoyi.monitor.domain.DeviceInfoDTO;
import com.ruoyi.monitor.domain.TbDevice;
import com.ruoyi.monitor.enums.DeviceItem;
import lombok.extern.slf4j.Slf4j;
import org.snmp4j.PDU;
import org.snmp4j.Target;

import java.io.IOException;
import java.util.*;

@Slf4j
public class SnmpDeviceData extends SnmpConfig {
    private static final int PDU_GET = PDU.GET;
    private static final int PDU_WALK = PDU.GETBULK;

    private static final  int PDU_GETNEXT = PDU.GETNEXT;
    private static final int PORT_OPEN = 1;
    private static final String PORT_ERROR_NAME = "NULL0";

    TbDevice tbDevice;
    Target target;

        public SnmpDeviceData(TbDevice tbDevice){
        this.tbDevice = tbDevice;
    }

    /*初始化SNMP参数*/
    public void init(){
        String deviceIp = "udp:" + tbDevice.getDeviceIp() + "/161";
        this.target = createTarget(tbDevice.getSnmpVersion(), tbDevice.getSnmpCommunity(), deviceIp);
        initSnmpV2();
    }

    /*关闭SNMP开启的服务*/
    public void snmpClose(){
        try {
            snmp.close();
            transportMapping.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*采集设备基础信息*/
    public TbDevice acquireDeviceInfo(){
        init();
        // 查询使用的oid
        ArrayList<String> oidList = new ArrayList<>();
        oidList.add(OidConstants.sysDescr);
        oidList.add(OidConstants.oidSysDescr);
        oidList.add(OidConstants.sysTime);

        List<String> configList = createPDUs(oidList, PDU_GET, target);
        String deviceName = configList.get(0);
        String sysDescr = configList.get(1);
        String uptime = configList.get(2);
        String[] arr = sysDescr.split("\\n");
        if (arr.length > 0){
            String model = arr[0];
            tbDevice.setDeviceModel(model);
        }
        tbDevice.setDeviceName(deviceName);
        //调用判断厂商id的方法，返回对应id的厂商名
        tbDevice.setManufacturer(manufacturerEstimate(sysDescr));
        tbDevice.setPortNum(getIfNumber());
        tbDevice.setUptime(uptime);

        snmpClose();
        return tbDevice;
    }

    /*采集CPU使用率*/
    public Map<String,String> acquireCpuMem(String deviceMenu){
        init();
        Map<String,String> map = new HashMap<>();
        String cpuOid = OidEnum.getOidByName(deviceMenu+"_CPU");
        String memOid = OidEnum.getOidByName(deviceMenu+"_MEM");
        if (StringUtils.isNotNull(cpuOid)){
            String cpuUsage = createPDU(cpuOid, PDU_GETNEXT, target);
            map.put(DeviceItem.CPU.getItem(),cpuUsage);
        }
        if (StringUtils.isNotNull(memOid)){
            List<String> memList = createTable(memOid, PDU_GETNEXT,target);
            for (String usage:memList) {
                if (Long.parseLong(usage) >0){
                    map.put(DeviceItem.MEM.getItem(),usage);
                    break;
                }
            }
        }
        snmpClose();
        return map;
    }

    /*采集内存使用信息*/
    public DeviceInfoDTO acquireMemoryUsage(){
        init();
        ArrayList<String> oidList = new ArrayList<>();
        oidList.add(OidConstants.MEMORY_TOTAL);   //物理内存总容量
        oidList.add(OidConstants.MEMORY_AVAIL);   //物理内存空闲容量
        oidList.add(OidConstants.MEMORY_BUFFER);  //物理内存缓冲容量
        oidList.add(OidConstants.MEMORY_CACHE);   //物理内存缓存容量

        List<String> memoryInfoList = createPDUs(oidList, PDU_GET, target);
        long memoryTotal = Long.parseLong(memoryInfoList.get(0));
        long memoryFree = Long.parseLong(memoryInfoList.get(1));
        long memoryBuffer = Long.parseLong(memoryInfoList.get(2));
        long memoryCache = Long.parseLong(memoryInfoList.get(3));

        //获取物理内存已使用容量
        long memoryUsed = (memoryTotal - memoryFree - memoryBuffer - memoryCache) + (memoryBuffer + memoryCache);
        //计算最终物理内存使用率
        double memoryUsage = ((double) memoryUsed / (double) memoryTotal) * 100;

        DeviceInfoDTO memoryInfoDTO = new DeviceInfoDTO();
        memoryInfoDTO.setTotal(memoryTotal);
        memoryInfoDTO.setFree(memoryFree);
        memoryInfoDTO.setMemoryUsage(memoryUsage);

        return memoryInfoDTO;
    }



    /*采集设备所有端口信息*/
    public Map<String,String> deviceItemInfo(){
        init();
        List<String> oidList = new ArrayList<>();
        oidList.add(OidConstants.IF_DESCR); // 端口名
        oidList.add(OidConstants.IF_OPER_STATUS); //端口状态

        Map<Integer, List<String>> map = createTables(oidList, PDU_GET, target);
        Map<String,String> portMap = new HashMap<>();
        for (Integer key : map.keySet()) {
            List<String> resultList = map.get(key);
            if (resultList.get(0).toLowerCase().contains("ethernet")){
                portMap.put(resultList.get(0),resultList.get(1));
            }
        }
        snmpClose();
        return portMap;
    }

    /*采集所有端口入流量*/
    public Map<String, Long> acquirePortInFlow(){
        init();
        LinkedHashMap<String, Long> flowRateMap = new LinkedHashMap<>();

        ArrayList<String> oidList = new ArrayList<>();
        oidList.add(OidConstants.IF_DESCR); // 端口名
        oidList.add(OidConstants.IF_HC_IN_OCTETS); // 端口入流量


        Map<Integer, List<String>> InFlowMap = createTables(oidList, PDU_GET, target);

        for (Integer key: InFlowMap.keySet()) {

            List<String> valueList = InFlowMap.get(key);

                flowRateMap.put(valueList.get(0),Long.parseLong(valueList.get(1)));

        }
        snmpClose();
        return flowFiltration(flowRateMap);
    }

    /*采集所有端口出流量*/
    public Map<String, Long> acquirePortOutFlow(){
        init();
        LinkedHashMap<String, Long> flowRateMap = new LinkedHashMap<>();

        ArrayList<String> oidList = new ArrayList<>();
        oidList.add(OidConstants.IF_DESCR); // 端口名
        oidList.add(OidConstants.IF_HC_OUT_OCTETS); // 端口出流量

        Map<Integer, List<String>> OutFlowMap = createTables(oidList, PDU_GET, target);

        for (Integer key: OutFlowMap.keySet()) {
            List<String> valueList = OutFlowMap.get(key);
            flowRateMap.put(valueList.get(0),Long.parseLong(valueList.get(1)));
        }

        snmpClose();
        return flowFiltration(flowRateMap);
    }

    /**
     * 设备采集端口过滤
     * 过滤内容:
     *      1. 端口状态为 2 的端口  (1为开,2为关)
     *      2. 端口名为 NULL0 的端口
     * @param map 初始采集数据
     * @return 过滤后数据
     */
    private Map<String, Long> flowFiltration(Map<String, Long> map){
        init();
        Map<String, Long> resultMap = new LinkedHashMap<>();

        // 采集设备所有端口状态信息
        List<String> statusList = createTable(OidConstants.IF_OPER_STATUS, PDU_GET, target);

        if (statusList.size() != map.size()){
            log.info("<端口异常数据过滤> || 端口数量有误!!!");
            return map;
        }

        // 转换map的keySet集合类型
        ArrayList<String> keyList = new ArrayList<>(map.keySet());
        for (int i = 0; i < keyList.size(); i++) {
            String portName = keyList.get(i);
            int portStatus = Integer.parseInt(statusList.get(i));

            if (!portName.equals(PORT_ERROR_NAME) && portStatus == PORT_OPEN) {
                if (portName.toLowerCase().contains("ethernet")){
                    resultMap.put(portName,map.get(portName));
                }
            }
        }

        snmpClose();
        return resultMap;
    }


    public Integer getIfNumber(){
        init();
         List<String> ifPorts = createTable(OidConstants.IF_NUM, PDU_GET, target);
         Integer portNum = 0;
         for (String pType:ifPorts){
             if (pType.equals("6")){
                 portNum++;
             }
         }

         return portNum;

    }
    /**
     * 查询设备厂商
     * @param   厂商分类oid （我也不知道这个oid是干啥的，凑合看看就得了）
     * @return 厂商名
     */
    private String manufacturerEstimate(String sysDescr){
        String vendor ;
        if (sysDescr.toLowerCase().contains("cisco")) {
            vendor = "CISCO";
        } else if (sysDescr.toLowerCase().contains("huawei")) {
            vendor = "HUAWEI";
        } else if (sysDescr.toLowerCase().contains("h3c")) {
            vendor = "H3C";
        } else if (sysDescr.toLowerCase().contains("juniper")) {
            vendor = "Juniper";
        } else {
            vendor = "Unknown";
        }
        return vendor.toUpperCase();
    }



}
