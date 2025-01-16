package org.jeecg.modules.openapi.base.enums;

/**
 * @author tanyn
 * @date 2022/10/18 10:44
 */
public enum SysOpenAuthUserTypeEnum {
    SUPPLIER(1,"设备厂家"),
    HOSP(2,"互联网医院");

    private int code;

    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    SysOpenAuthUserTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public static String getMsgByCode(Integer code) {
        for (SysOpenAuthUserTypeEnum statusEnum : SysOpenAuthUserTypeEnum.values()) {
            if (code.equals(statusEnum.getCode())) {
                return statusEnum.getDesc();
            }
        }
        return null;
    }
}
