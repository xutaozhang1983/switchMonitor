package com.ruoyi.monitor.task;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.monitor.domain.DeviceInfoDTO;
import com.ruoyi.monitor.domain.TbDevice;
import com.ruoyi.monitor.domain.TbDeviceItem;
import com.ruoyi.monitor.enums.AlarmEnum;
import com.ruoyi.monitor.enums.DeviceItem;
import com.ruoyi.monitor.enums.StatusEnum;
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

    public void deviceMonitor(){
        TbDevice tbDevice = new TbDevice();
        tbDevice.setEnable(0);
        List<TbDevice> deviceDTOList= tbDeviceService.selectDeviceList(tbDevice);
        for (TbDevice device:deviceDTOList) {
            String lastStatus = device.getStatus();
            String nowStatus;
            if(!PingUtil.ping(device.getDeviceIp())) {
                nowStatus = StatusEnum.ERROR.getCode();
            }else{
                nowStatus = StatusEnum.OK.getCode();
            }
            System.out.println(nowStatus+"------");
            if (!lastStatus.equals(nowStatus)){
                device.setStatus(nowStatus);
                tbDeviceService.updateTbDevice(device);
                String content;
                if (nowStatus.equals(StatusEnum.ERROR.getCode())){
                    content = String.format(StatusEnum.ERROR.getContent(),"设备",device.getDeviceName()+device.getDeviceIp());
//
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
        List<TbDevice> deviceDTOList= tbDeviceService.selectDeviceList(tbDevice);
        for (TbDevice device:deviceDTOList) {
            SnmpDeviceData snmpDevice = new SnmpDeviceData(device);
            if( StringUtils.isEmpty(device.getManufacturer())){
                deviceUpdate();
            }
            Map<String ,String> cpuMem = snmpDevice.acquireCpuMem(device.getManufacturer());
            TbDeviceItem item = itemService.selectItemExist(device.getId(), DeviceItem.CPU.getItem());
            if (item.getId() >0){
                item.setLastValue(item.getValue());
                item.setValue(cpuMem.get(DeviceItem.CPU.getItem()));
                itemService.updateTbDeviceItem(item);
            }
        }
    }


}
