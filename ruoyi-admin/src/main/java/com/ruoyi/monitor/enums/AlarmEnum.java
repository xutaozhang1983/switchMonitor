package com.ruoyi.monitor.enums;

public enum AlarmEnum {
    OK("0","INFO","%s - %s - %s 正常"),

    WARING("1", "WARING","%s- %s - %s 无法连接,请尽快检查！"),
    ERROR("2", "ERROR","%s- %s - %s 无法连接,请尽快检查！");

    private final String code;
    private final String level;
    private final String content;


    private AlarmEnum(String code, String level, String content) {
        this.code = code;
        this.level = level;
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public String getLevel() {
        return level;
    }

    public String getContent() {
        return content;
    }

    public static String getContentByCode(String code) {
        for (AlarmEnum status : AlarmEnum.values()) {
            if (status.getCode().equals(code)) {
                return status.getContent();
            }
        }
        return null;
    }

    public static String getLevelByCode(String code) {
        for (AlarmEnum status : AlarmEnum.values()) {
            if (status.getCode().equals(code)) {
                return status.getLevel();
            }
        }
        return AlarmEnum.OK.getLevel();
    }
}
