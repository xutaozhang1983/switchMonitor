package com.ruoyi.monitor.enums;

// 设备厂商编号
public enum ManufacturerEnum {

    HUAWEI(2506,"HUAWEI"),
    H3C(25506,"H3C"),
    CISCO(9, "Cisco");


    private final Integer objectId;
    private final String manufacturerName;

    private ManufacturerEnum(Integer objectId, String manufacturerName){
        this.objectId = objectId;
        this.manufacturerName = manufacturerName;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public static String getManufactureName(String objectId){
        ManufacturerEnum[] manufacturerEnums = ManufacturerEnum.values();
        //遍历枚举类，判断传递参数是否存在相同
        for (ManufacturerEnum manufacturerEnum : manufacturerEnums) {
            Integer enumObjectId = manufacturerEnum.getObjectId();
            if (enumObjectId.equals(Integer.parseInt(objectId))) {
                return manufacturerEnum.getManufacturerName();
            }
        }
        return "unknow";
    }
}
