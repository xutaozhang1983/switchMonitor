package com.ruoyi.monitor.task;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.monitor.domain.DeviceInfoDTO;
import com.ruoyi.monitor.domain.TbDevice;
import com.ruoyi.monitor.domain.TbDeviceItem;
import com.ruoyi.monitor.domain.TbDeviceItemHis;
import com.ruoyi.monitor.enums.AlarmEnum;
import com.ruoyi.monitor.enums.DeviceItem;
import com.ruoyi.monitor.enums.StatusEnum;
import com.ruoyi.monitor.service.ITbDeviceItemHisService;
import com.ruoyi.monitor.service.ITbDeviceItemService;
import com.ruoyi.monitor.service.ITbDeviceService;
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
    public void deviceMonitor(){
        TbDevice tbDevice = new TbDevice();
        tbDevice.setEnable(0);
        List<TbDevice> deviceDTOList= tbDeviceService.selectDeviceList(tbDevice);
        for (TbDevice device:deviceDTOList) {
            String lastStatus = device.getStatus();
            String nowStatus;
            if(PingUtil.ping(device.getDeviceIp())) {
                nowStatus = StatusEnum.OK.getCode();
            }else{
                nowStatus = StatusEnum.ERROR.getCode();
            }
            System.out.println(nowStatus+"------");
            if (!lastStatus.equals(nowStatus)){
                device.setStatus(nowStatus);
                tbDeviceService.updateTbDevice(device);
                String content;
                if (nowStatus.equals(StatusEnum.ERROR.getCode())){
                    content = String.format(StatusEnum.ERROR.getContent(),"设备",device.getDeviceName()+device.getDeviceIp());
                    System.out.println(content);
                    System.out.println("ERRor");
                }else{
                    content = String.format(StatusEnum.OK.getContent(),"设备",device.getDeviceName()+device.getDeviceIp());
                }
//                saveAlarmEvent(nowStatus,content,device.getId());

            }
        }

    }
    public void deviceUpdate(){
        TbDevice tbDevice = new TbDevice();
        tbDevice.setEnable(0);
        tbDevice.setStatus("0");
        List<TbDevice> deviceDTOList= tbDeviceService.selectDeviceList(tbDevice);
        for (TbDevice device:deviceDTOList) {
            SnmpDeviceData snmpDevice = new SnmpDeviceData(device);
            device  = snmpDevice.acquireDeviceInfo();
            tbDeviceService.updateTbDevice(device);
        }
    }
//    private void saveAlarmEvent(String status,String content,Long deviceId){
//        TbEvent alarmEvent = new TbAlarmEvent();
//        alarmEvent.setStatus(status);
//        alarmEvent.setAlarmContent(content);
//        alarmEvent.setAlarmLevel(AlarmEnum.getAlarmLevel(status));
//        alarmEvent.setDeviceId(deviceId);
//        alarmEventService.insertTbAlarmEvent(alarmEvent);
//    }

    public void monitorItem(){
        TbDevice tbDevice = new TbDevice();
        tbDevice.setEnable(0);
        tbDevice.setStatus("0");
        List<TbDevice> deviceDTOList= tbDeviceService.selectDeviceList(tbDevice);
        for (TbDevice device:deviceDTOList) {
            SnmpDeviceData snmpDevice = new SnmpDeviceData(device);
            if( StringUtils.isEmpty(device.getManufacturer())){
                deviceUpdate();
            }
            Map<String ,String> cpuMemMap = snmpDevice.acquireCpuMem(device.getManufacturer());
            for (String key:cpuMemMap.keySet()
                 ) {
                TbDeviceItem item = itemService.selectItemExist(device.getId(), key);
                if (StringUtils.isNotNull(item)) {
                    item.setLastValue(item.getValue());
                    item.setValue(cpuMemMap.get(key));
                    itemService.updateTbDeviceItem(item);
                    saveItemHis(item.getId(),device.getId(),cpuMemMap.get(key),key);
                } else {
                    saveItem(1L, device.getId(), key, key, cpuMemMap.get(key));
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
