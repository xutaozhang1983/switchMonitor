package com.ruoyi.monitor.task;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.monitor.domain.*;
import com.ruoyi.monitor.domain.vo.DeviceVO;
import com.ruoyi.monitor.enums.AlarmEnum;
import com.ruoyi.monitor.enums.DeviceItem;
import com.ruoyi.monitor.enums.StatusEnum;
import com.ruoyi.monitor.service.ITbDeviceItemHisService;
import com.ruoyi.monitor.service.ITbDeviceItemService;
import com.ruoyi.monitor.service.ITbDeviceService;
import com.ruoyi.monitor.service.ITbEventsService;
import com.ruoyi.utils.PingUtil;
import com.ruoyi.utils.SnmpDeviceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("monitorTask")
public class MonitorTask {

    @Autowired
    ITbDeviceService tbDeviceService ;

    @Autowired
    ITbDeviceItemService itemService;

    @Autowired
    ITbDeviceItemHisService itemHisService;

    @Autowired
    ITbEventsService eventsService;

    public void deviceMonitor(){
        TbDevice tbDevice = new TbDevice();
        tbDevice.setEnable(0);
        List<DeviceVO> deviceDTOList= tbDeviceService.selectTbDeviceList(tbDevice);
        for (DeviceVO device:deviceDTOList) {
            String lastStatus = device.getStatus();
            String nowStatus;
            if(PingUtil.ping(device.getDeviceIp())) {
                nowStatus = StatusEnum.OK.getCode();
            }else{
                nowStatus = StatusEnum.ERROR.getCode();
            }
            String content= "";
            if (!lastStatus.equals(nowStatus)){
                device.setStatus(nowStatus);
                tbDeviceService.updateTbDevice(device);

                if (!nowStatus.equals(StatusEnum.OK.getCode())){
                    content = String.format(StatusEnum.ERROR.getContent(),device.getGroupName(),device.getDeviceName(),device.getDeviceIp());
                }else{
                    content = String.format(StatusEnum.OK.getContent(),device.getGroupName(),device.getDeviceName(),device.getDeviceIp());
                    System.out.println(content);
                }
                saveEvent(nowStatus,content,device.getId());
            }


        }

    }
    public void deviceUpdate(){
        TbDevice tbDevice = new TbDevice();
//        tbDevice.setEnable(0);
//        tbDevice.setStatus("0");
        tbDevice.setId(2L);
        List<TbDevice> deviceDTOList= tbDeviceService.selectDeviceList(tbDevice);
        for (TbDevice device:deviceDTOList) {
            SnmpDeviceData snmpDevice = new SnmpDeviceData(device);
            device  = snmpDevice.acquireDeviceInfo();
            tbDeviceService.updateTbDevice(device);
        }
    }
    private void saveEvent(String status,String content,Long deviceId){
        TbEvents event = new TbEvents();
        event.setStatus(status);
        event.setAlarmContent(content);
        event.setAlarmLevel(AlarmEnum.getAlarmLevel(status));
        event.setDeviceId(deviceId);
        eventsService.insertTbEvents(event);
    }

    public void monitorItem(){
        TbDevice tbDevice = new TbDevice();
//        tbDevice.setEnable(0);
//        tbDevice.setStatus("0");
        tbDevice.setId(2L);
        List<TbDevice> deviceDTOList= tbDeviceService.selectDeviceList(tbDevice);
        for (TbDevice device:deviceDTOList) {
            System.out.println(device.getDeviceName()+"获取CPU 内存信息");
            SnmpDeviceData snmpDevice = new SnmpDeviceData(device);
            if( StringUtils.isEmpty(device.getManufacturer())){
                deviceUpdate();
            }
            Map<String ,String> cpuMemMap = snmpDevice.acquireCpuMem(device.getManufacturer());
            if(StringUtils.isEmpty(cpuMemMap)){
                System.out.println(device.getDeviceName()+"获取CPU 内存信息失败。。。。。");
                continue;
            }
            for (String key:cpuMemMap.keySet()) {
                TbDeviceItem item = itemService.selectItemExist(device.getId(), key);
                if (StringUtils.isNotNull(item)) {
                    item.setLastValue(item.getValue());
                    item.setValue(cpuMemMap.get(key));
                    item.setClock(DateUtils.timestamp());
                    itemService.updateTbDeviceItem(item);
                    saveItemHis(item.getId(),device.getId(),cpuMemMap.get(key),key);
                } else {
                    saveItem(1L, device.getId(), key, key, cpuMemMap.get(key));
                }
            }
            // 端口信息
            Map<String,String> ifStatusMap = snmpDevice.deviceItemInfo();
            for (String key:ifStatusMap.keySet()) {
                TbDeviceItem item = itemService.selectItemExist(device.getId(), key);
                if (StringUtils.isNotNull(item)) {
                    item.setStatus(ifStatusMap.get(key));
                    item.setClock(DateUtils.timestamp());
                    itemService.updateTbDeviceItem(item);
                }else {
                    saveItem(0L, device.getId(),key , "ifIn,ifOut", cpuMemMap.get(key));
                }
            }

            // 端口流量
            Map<String ,Long> ifInFlowMap = snmpDevice.ifInFlow();
            Map<String ,Long> ifOutFlowMap = snmpDevice.ifOutFlow();
            for (String key: ifInFlowMap.keySet()) {
                TbDeviceItem item = itemService.selectItemExist(device.getId(), key);
                if(StringUtils.isNotNull(item)){
                    String[] lastFlow = item.getValue().split(",");
                    item.setLastValue(item.getValue());
                    Long ifIn = ifInFlowMap.get(key) - Long.parseLong(lastFlow[0]);
                    Long ifOut = ifOutFlowMap.get(key) - Long.parseLong(lastFlow[1]);
                    item.setValue(String.valueOf(ifIn)+","+String.valueOf(ifOut));
                    itemService.updateTbDeviceItem(item);
                    saveItemHis(item.getId(),device.getId(),String.valueOf(ifInFlowMap.get(key)),"ifIn");
                    saveItemHis(item.getId(),device.getId(),String.valueOf(ifOutFlowMap.get(key)),"ifOut");
                }
            }
        }
    }

    private void saveItem(Long isPort,Long deviceId,String itemName,String counter,String value){
        TbDeviceItem deviceItem = new TbDeviceItem();
        deviceItem.setIsPort(isPort);
        deviceItem.setDeviceId(deviceId);
        deviceItem.setItemName(itemName);
        deviceItem.setCounter(counter);
        deviceItem.setValue(value);
        if (isPort == 0){
            deviceItem.setStatus(value);
        }
        itemService.insertTbDeviceItem(deviceItem);

    }

    private void saveItemHis(Long itemId,Long deviceId,String value,String counter){
        TbDeviceItemHis itemHis = new TbDeviceItemHis();
        itemHis.setClock(DateUtils.timestamp());
        itemHis.setItemId(itemId);
        itemHis.setDeviceId(deviceId);
        itemHis.setValue(value);
        itemHis.setCounter(counter);
        itemHisService.insertTbDeviceItemHis(itemHis);
    }
}
