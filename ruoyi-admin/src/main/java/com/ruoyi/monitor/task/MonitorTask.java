package com.ruoyi.monitor.task;

import com.ruoyi.monitor.domain.TbAlarmEvent;
import com.ruoyi.monitor.domain.TbDevice;
import com.ruoyi.monitor.enums.AlarmEnum;
import com.ruoyi.monitor.enums.StatusEnum;
import com.ruoyi.monitor.service.ITbAlarmEventService;
import com.ruoyi.monitor.service.ITbDeviceService;
import com.ruoyi.monitor.service.ITbEventsService;
import com.ruoyi.utils.PingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("monitorTask")
public class MonitorTask {

    @Autowired
    private ITbDeviceService tbDeviceService;

    @Autowired
    ITbEventsService eventsService;
    @Autowired
    ITbAlarmEventService alarmEventService;

    public void deviceMonitor(){
        TbDevice tbDevice = new TbDevice();
        tbDevice.setEnable(1);
        List<TbDevice> deviceDTOList= tbDeviceService.selectDeviceList(tbDevice);

        for (TbDevice device:deviceDTOList) {
            String lastStatus = device.getStatus();
            if(!PingUtil.ping(device.getDeviceIp())){
                device.setStatus(StatusEnum.ERROR.getCode());
                tbDeviceService.updateTbDevice(device);
                // 保存报警事件
                String content = String.format(StatusEnum.ERROR.getContent(),"设备",device.getHostname()+device.getDeviceIp());
                saveAlarmEvent(StatusEnum.ERROR.getCode(),content,device.getId());
            } else {
                // 如果之前不通，现在检查通了发送一个通知
                if (lastStatus.equals(StatusEnum.ERROR.getCode())){
                    String content = String.format(StatusEnum.OK.getContent(),"设备",device.getHostname()+device.getDeviceIp());
                    saveAlarmEvent(StatusEnum.OK.getCode(),content,device.getId());
                }
            }
        }

    }

    private void saveAlarmEvent(String status,String content,Long deviceId){
        TbAlarmEvent alarmEvent = new TbAlarmEvent();
        alarmEvent.setStatus(status);
        alarmEvent.setAlarmContent(content);
        alarmEvent.setAlarmLevel(AlarmEnum.getAlarmLevel(status));
        alarmEvent.setDeviceId(deviceId);
        alarmEventService.insertTbAlarmEvent(alarmEvent);
    }

}
