package org.jeecg.modules.zxecg.cust.enums;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/9
 * @description
 */


public enum McOrderStatusEnum {
    NOTPAY(10),
    SUCCESS(20),
    ING(30),
    TRANS(40),
    FINISHED(50),
    CLOSED(60),
    REFUND(90);

    private int code;


    private McOrderStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
