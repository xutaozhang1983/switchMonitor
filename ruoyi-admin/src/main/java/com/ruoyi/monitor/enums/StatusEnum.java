package com.ruoyi.monitor.enums;

public enum StatusEnum {
    OK("0","%s - %s - %s 正常"),

    WARING("1", "%s- %s - %s 无法连接,请尽快检查！"),
    ERROR("2", "%s- %s - %s 无法连接,请尽快检查！");

    private final String code;
    private final String content;


    private StatusEnum(String code, String content) {
        this.code = code;
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }

    public static String getContentByCode(String code) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.getCode().equals(code)) {
                return status.getContent();
            }
        }
        return null;
    }
}
