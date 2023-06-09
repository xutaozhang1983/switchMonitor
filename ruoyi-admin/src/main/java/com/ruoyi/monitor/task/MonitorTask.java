package com.ruoyi.monitor.task;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.monitor.domain.*;
import com.ruoyi.monitor.domain.vo.DeviceVO;
import com.ruoyi.monitor.enums.DeviceItem;
import com.ruoyi.monitor.enums.AlarmEnum;
import com.ruoyi.monitor.service.ITbDeviceItemHisService;
import com.ruoyi.monitor.service.ITbDeviceItemService;
import com.ruoyi.monitor.service.ITbDeviceService;
import com.ruoyi.monitor.service.ITbEventsService;
import com.ruoyi.utils.PingUtil;
import com.ruoyi.snmpUtils.SnmpDeviceData;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
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
        log.info("start device check");
        for (DeviceVO device:deviceDTOList) {
            String lastStatus = device.getStatus();
            String nowStatus ="0";
            if(PingUtil.ping(device.getDeviceIp())) {
                nowStatus = AlarmEnum.OK.getCode();
            }else{
                nowStatus = AlarmEnum.ERROR.getCode();
            }
            String content= "";
            if (!lastStatus.equals(nowStatus)){
                device.setStatus(nowStatus);
                tbDeviceService.updateTbDevice(device);
                content = String.format(AlarmEnum.getContentByCode(nowStatus),device.getGroupName(),device.getDeviceName(),device.getDeviceIp());
                saveEvent(nowStatus,content,device.getId(),null);
                if (nowStatus.equals(AlarmEnum.OK.getCode())){ // 如果本次检查是OK，查询上次报警事件是否关闭，如果没关闭 自动关闭
                    TbEvents events = eventsService.selectLastEvent(device.getId(),null,"0");
                    if (StringUtils.isNotNull(events)){
                        events.setClosedUser("system");
                        events.setClosedAt(DateUtils.getNowDate());
                        events.setStatus("1");
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
        log.info("start device info update");
        for (TbDevice device:deviceDTOList) {
            SnmpDeviceData snmpDevice = new SnmpDeviceData(device);
            device  = snmpDevice.acquireDeviceInfo();
            tbDeviceService.updateTbDevice(device);
        }
    }

    public void monitorItem(){
        TbDevice tbDevice = new TbDevice();
//        tbDevice.setEnable(0);
//        tbDevice.setStatus("0");
        tbDevice.setModel(1L);
        tbDevice.setId(2L);
        List<TbDevice> deviceDTOList= tbDeviceService.selectDeviceList(tbDevice);
        List<TbDeviceItemHis> itemHisList = new ArrayList<>();
        for (TbDevice device:deviceDTOList) {
            log.info(device.getDeviceName()+"获取CPU 内存信息");
            SnmpDeviceData snmpDevice = new SnmpDeviceData(device);
            List<TbDeviceItem> itemList = itemService.selectItemList(device.getId());
            Map<String ,String> cpuMemMap = snmpDevice.acquireCpuMem(device.getManufacturer());
            for (String key:cpuMemMap.keySet()) {
                boolean isExists = false;
                for (TbDeviceItem deviceItem:itemList) {
                    if(deviceItem.getItemName().equals(key)){
                        isExists = true;
                        deviceItem.setLastValue(deviceItem.getValue());
                        deviceItem.setValue(cpuMemMap.get(key));
                        itemService.updateTbDeviceItem(deviceItem);
                        itemHisList.add(formatItemHis(deviceItem.getId(),device.getId(),Long.parseLong(cpuMemMap.get(key)),key));
                    }
                }
                if (!isExists){
                    saveItem(DeviceItem.getIsPortByItem(key), device.getId(), key, key, cpuMemMap.get(key), AlarmEnum.OK.getCode());
                }
            }
            // 端口信息
            log.info(device.getDeviceName()+"端口出入流量");
            Map<String,String> ifStatusMap = snmpDevice.deviceItemInfo();
            Map<String ,Long> ifInFlowMap = snmpDevice.ifInFlow(); // 端口入流量
            Map<String ,Long> ifOutFlowMap = snmpDevice.ifOutFlow(); // 端口出流量
            Long allIn = 0L;
            Long allOut = 0L;
            for (String key: ifStatusMap.keySet()) {
                boolean itemExists = false;
                String lastValue = "0,0";
                for (TbDeviceItem deviceItem:itemList) {
                    if (deviceItem.getItemName().equals(key)) {
                        itemExists = true;
                        lastValue = StringUtils.isNotNull(deviceItem.getLastValue()) ? deviceItem.getLastValue() : lastValue;
                        String[] lastFlow = lastValue.split(",");
                        deviceItem.setStatus(ifStatusMap.get(key));  // 端口状态
                        if (ifInFlowMap.containsKey(key) && ifOutFlowMap.containsKey(key)) {  // 获取不到端口的流量不更新流量 不写his
                            log.info("端口出入流量"+ifInFlowMap.get(key)+"----"+ifOutFlowMap.get(key));
                            deviceItem.setLastValue(lastValue);
                            Long ifIn = ifInFlowMap.get(key) - Long.parseLong(lastFlow[0]);
                            Long ifOut = ifOutFlowMap.get(key) - Long.parseLong(lastFlow[1]);
                            ifIn = (ifIn <= 0) ? 0L : ifIn;
                            ifOut = (ifOut <= 0) ? 0L : ifOut;
                            deviceItem.setValue(ifIn+ "," + ifOut);
                            deviceItem.setLastValue(ifInFlowMap.get(key)+","+ifOutFlowMap.get(key)); // 本次获取流量总
                            itemHisList.add(formatItemHis(deviceItem.getId(), device.getId(), ifIn, DeviceItem.IFIN.getItem()));
                            itemHisList.add(formatItemHis(deviceItem.getId(), device.getId(), ifOut, DeviceItem.IFOUT.getItem()));
                            allIn = allIn+ ifIn;
                            allOut = allOut + ifOut;

                        }
                        itemService.updateTbDeviceItem(deviceItem);
                        // 是否端口状态发生改变
                        if (!deviceItem.getStatus().equals(ifStatusMap.get(key))) {
                            String status;
                            if (ifStatusMap.get(key).equals(AlarmEnum.ERROR.getCode())) {
                                status = AlarmEnum.WARING.getCode();
                            } else {
                                status = AlarmEnum.OK.getCode();
                            }
                            String text = String.format(AlarmEnum.getContentByCode(status), device.getDeviceName() + device.getDeviceIp(), deviceItem.getItemName());
                            saveEvent(status, text, device.getId(), deviceItem.getId());
                        }
                    }
                } // 循环处理item
                if(!itemExists){
                    if (ifInFlowMap.containsKey(key) && ifOutFlowMap.containsKey(key)){
                        lastValue = ifInFlowMap.get(key)+","+ifOutFlowMap.get(key);
                    }
                    String counter = DeviceItem.IFIN.getItem()+"," + DeviceItem.IFOUT.getItem();
                    saveItem(DeviceItem.IFIN.getIsPort(), device.getId(),key , counter, lastValue,ifStatusMap.get(key));
                }

            } // snmp item 信息
            itemHisList.add(formatItemHis(0L, device.getId(), allOut, DeviceItem.IFOUT.getItem()));
            itemHisList.add(formatItemHis(0L, device.getId(), allIn, DeviceItem.IFIN.getItem()));
        }
        itemHisService.insertBatch(itemHisList); // 整体历史数据写入
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
    private TbDeviceItemHis formatItemHis(Long itemId,Long deviceId,Long value,String counter){
        TbDeviceItemHis itemHis = new TbDeviceItemHis();
        itemHis.setItemId(itemId);
        itemHis.setDeviceId(deviceId);
        itemHis.setValue(value);
        itemHis.setCounter(counter);
        itemHis.setClock(DateUtils.timestamp());
        return itemHis;
    }
    private void saveEvent(String status,String content,Long deviceId,Long itemId){
        TbEvents event = new TbEvents();
        event.setAlarmContent(content);
        event.setAlarmLevel(AlarmEnum.getLevelByCode(status));
        event.setItemId(itemId);
        event.setDeviceId(deviceId);
        eventsService.insertTbEvents(event);
    }

}
