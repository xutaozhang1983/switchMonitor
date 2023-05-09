package com.ruoyi.monitor.constants;

public enum OidEnum {

    HW_CPU(".1.3.6.1.4.1.2011.6.3.4.1.4","HUAWEI_CPU","CPU"),
    HW_MEM("1.3.6.1.4.1.2011.5.25.31.1.1.1.1.7","HUAWEI_MEM","MEM");


    private String oid;
    private String name;

    private String counter;

    public String getOid(){
        return this.oid;
    }

    public String getName() {
        return name;
    }

    public String getCounter() {
        return counter;
    }

    OidEnum(String oid, String name,String counter) {
        this.oid = oid;
        this.name = name;
        this.counter = counter;
    }
    public static String getOidByName(String name) {

        for (OidEnum oid : OidEnum.values()) {

            if (oid.getName().equals(name.toUpperCase())) {
                return oid.getOid();
            }
        }
        return null;
    }
}
