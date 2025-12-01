package org.jeecg.modules.zxecg.cust.enums;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/9
 */

public enum MaDispOrderStatusEnum {
    WAIT(10),
    ING(20),
    FINISHED(30),
    CLOSED(40);

    private int code;


    private MaDispOrderStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
