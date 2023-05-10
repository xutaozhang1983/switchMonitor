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
            String nowStatus ="0";
            if(PingUtil.ping(device.getDeviceIp())) {
                nowStatus = StatusEnum.OK.getCode();
            }else{
                nowStatus = StatusEnum.ERROR.getCode();
            }
            String content= "";
            if (!lastStatus.equals(nowStatus)){
                device.setStatus(nowStatus);
                tbDeviceService.updateTbDevice(device);
                content = String.format(StatusEnum.getContentByCode(nowStatus),device.getGroupName(),device.getDeviceName(),device.getDeviceIp());
                saveEvent(nowStatus,content,device.getId(),null);
                if (nowStatus.equals(StatusEnum.OK.getCode())){ // 如果本次检查是OK，查询上次报警事件是否关闭，如果没关闭 自动关闭
                    TbEvents events = eventsService.selectEvent(device.getId(),null,StatusEnum.ERROR.getCode());
                    if (StringUtils.isNotNull(events)){
                        events.setClosedUser("system");
                        events.setClosedAt(DateUtils.getNowDate());
                        eventsService.saveEvent(events);
                    }
                }
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
    private void saveEvent(String status,String content,Long deviceId,Long itemId){
        TbEvents event = new TbEvents();
        event.setStatus(status);
        event.setAlarmContent(content);
        event.setAlarmLevel(AlarmEnum.getAlarmLevel(status));
        event.setItemId(itemId);
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
            for (String key:cpuMemMap.keySet()) {
                TbDeviceItem item = itemService.selectItemExist(device.getId(), key);
                if (StringUtils.isNotNull(item)) {
                    item.setLastValue(item.getValue());
                    item.setValue(cpuMemMap.get(key));
                    itemService.updateTbDeviceItem(item);
                    saveItemHis(item.getId(),device.getId(),cpuMemMap.get(key),key);
                } else {
                    saveItem(1L, device.getId(), key, key, cpuMemMap.get(key),"0");
                }
            }
            // 端口信息
            Map<String,String> ifStatusMap = snmpDevice.deviceItemInfo();
            // 端口流量
            Map<String ,Long> ifInFlowMap = snmpDevice.ifInFlow();
            Map<String ,Long> ifOutFlowMap = snmpDevice.ifOutFlow();

            for (String key: ifStatusMap.keySet()) {
                TbDeviceItem item = itemService.selectItemExist(device.getId(), key);
                String value = "0,0";
                if(StringUtils.isNotNull(item)){
                    value = StringUtils.isNotNull(item.getValue()) ? item.getValue() : value;
                    String[] lastFlow = value.split(",");
                    Long ifIn = 0L;
                    Long ifOut = 0L;
                    if (ifInFlowMap.containsKey(key) && ifOutFlowMap.containsKey(key)){  // 获取不到端口的状态不更新
                        ifIn = ifInFlowMap.get(key) - Long.parseLong(lastFlow[0]);
                        ifOut = ifOutFlowMap.get(key) - Long.parseLong(lastFlow[1]);
                        saveItemHis(item.getId(),device.getId(),String.valueOf(ifInFlowMap.get(key)),"ifIn");
                        saveItemHis(item.getId(), device.getId(), String.valueOf(ifOutFlowMap.get(key)), "ifOut");
                        item.setLastValue(value);
                        item.setValue(ifIn + "," + ifOut);
                        item.setStatus(ifStatusMap.get(key));
                        itemService.updateTbDeviceItem(item);
                    }

                    // 是否端口状态发生改变
                    if (!item.getStatus().equals(ifStatusMap.get(key))){
                        String status ;
                        if (ifStatusMap.get(key).equals(StatusEnum.ERROR.getCode())){
                            status = StatusEnum.ERROR.getCode();
                        }else{
                            status = StatusEnum.OK.getCode();
                        }
                        String text = String.format(StatusEnum.getContentByCode(status),device.getDeviceName()+device.getDeviceIp(),item.getItemName());
                        saveEvent(status,text,device.getId(),item.getId());
                    }
                }else {
                    if (ifInFlowMap.containsKey(key) && ifOutFlowMap.containsKey(key)){
                        value = ifInFlowMap.get(key)+","+ifOutFlowMap.get(key);
                    }
                    saveItem(0L, device.getId(),key , "ifIn,ifOut", value,ifStatusMap.get(key));
                }
            }
        }
    }

    private void saveItem(Long isPort,Long deviceId,String itemName,String counter,String value,String status){
        TbDeviceItem deviceItem = new TbDeviceItem();
        deviceItem.setIsPort(isPort);
        deviceItem.setDeviceId(deviceId);
        deviceItem.setItemName(itemName);
        deviceItem.setCounter(counter);
        deviceItem.setValue(value);
        deviceItem.setStatus(status);
        itemService.insertTbDeviceItem(deviceItem);

    }

    private void saveItemHis(Long itemId,Long deviceId,String value,String counter){
        TbDeviceItemHis itemHis = new TbDeviceItemHis();
        itemHis.setItemId(itemId);
        itemHis.setDeviceId(deviceId);
        itemHis.setValue(value);
        itemHis.setCounter(counter);
        itemHisService.insertTbDeviceItemHis(itemHis);
    }
}
