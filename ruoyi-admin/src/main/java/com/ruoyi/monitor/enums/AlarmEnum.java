package com.ruoyi.monitor.enums;

public enum AlarmEnum {
    INFO("0","INFO"),
    WARING("1","WARING"),
    ERROR("2", "ERROR");


    private final String alarmId;
    private final String alarmLevel;


    private AlarmEnum(String alarmId, String alarmLevel) {
        this.alarmId = alarmId;
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public static String getAlarmLevel(String code) {
        for (AlarmEnum alarm : AlarmEnum.values()) {
            if (alarm.getAlarmId().equals(code)) {
                return alarm.getAlarmLevel();
            }
        }
        return AlarmEnum.INFO.getAlarmLevel();
    }
}
