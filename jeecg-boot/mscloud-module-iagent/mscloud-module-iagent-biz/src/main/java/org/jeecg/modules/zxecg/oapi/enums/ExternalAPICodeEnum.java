package org.jeecg.modules.zxecg.oapi.enums;

/**
 * 接口代码
 * @author Administrator
 */
public enum ExternalAPICodeEnum {
    //判图软件
    ECG_TOKEN(110,"/oapi/token","判图软件鉴权获取token接口"),

    ECG_GET_USER_REPINFO(111,"/oapi/agent/u/rep","判图软件用户报告结论获取接口"),
    ECG_GET_USER_FRIENDS(112,"/oapi/agent/u/friends","判图软件用户亲友获取接口"),
    ECG_GET_USER_INFO(113,"/oapi/agent/u/info","判图软件用户信息获取接口"),

    //订单来源平台相关接口（互联网医院平台）
    ORDER_ORIGN_TOKEN(210,"/oapi/token","互联网医院平台鉴权获取token接口"),
    ORDER_ORIGN_SYNC_EXPRESS(211,"","互联网医院平台设备发货信息同步接口"),
    ORDER_ORIGN_SYNC_REP(212,"","互联网医院平台报告信息同步接口"),
    ORDER_ORIGN_SYNC_DEPOSIT(213,"","互联网医院平台设备回收状态同步接口"),
    ORDER_ORIGN_SYNC_REFUND(214,"","订单退款申请审核结果通知接口");

    private int code;

    private String defaultUri;

    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDefaultUri() {
        return defaultUri;
    }

    public void setDefaultUri(String defaultUri) {
        this.defaultUri = defaultUri;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ExternalAPICodeEnum(int code, String defaultUri, String desc) {
        this.code = code;
        this.defaultUri=defaultUri;
        this.desc = desc;
    }

    public static ExternalAPICodeEnum getExternalAPICodeEnumByCode(Integer code) {
        for (ExternalAPICodeEnum statusEnum : ExternalAPICodeEnum.values()) {
            if (code.equals(statusEnum.getCode())) {
                return statusEnum;
            }
        }
        return null;
    }
}
