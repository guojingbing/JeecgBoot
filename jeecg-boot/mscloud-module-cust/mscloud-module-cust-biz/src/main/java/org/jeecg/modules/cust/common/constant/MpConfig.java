package org.jeecg.modules.cust.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:小程序的初始配置信息
 * @Author: Kingpin
 * @Date: 2021-05-31 13:54:24
 **/
public class MpConfig {
    public static final String MP_APPID_NSHARE="wx5879c81e4cfc8ef5";
    public static final String MP_APPID_LOGIS_FJ="wx04df17893c0d291c";
    public static final String MP_APPID_LOGIS_JH="wxb6f4e856cd1d4066";
    public static final String MP_APPID_LOGIS_ARD="wx65cb013bfaaf25ae";

    //小程序配置信息
    public static final Map<String, Map<String, String>> appAuth = new HashMap<>();
    static {
        Map<String, String> config = new HashMap();
        config.put("MP-SECRET", "2a871425b7b79cf5dd569d4d1a17e0b4");
//        config.put("TRTC-SDKAPPID","1400474322");
//        config.put("TRTC-SECRET","739991025da854e1c67ca2bf1809a564c324926b22dfc1cdac2d7726622a5ea2");
        //近邻
        appAuth.put(MP_APPID_NSHARE, config);
        //飞驹
        config = new HashMap();
        config.put("MP-SECRET", "6d5e2f0bfb3bb54341253f83a8ea903e");
//        config.put("TRTC-SDKAPPID", "1400474322");
//        config.put("TRTC-SECRET", "739991025da854e1c67ca2bf1809a564c324926b22dfc1cdac2d7726622a5ea2");
        config.put("OAPI-AUTH-ID", "1");
        appAuth.put(MP_APPID_LOGIS_FJ, config);
        //锦华
        config = new HashMap();
        config.put("MP-SECRET", "5c6d922ab273572b5358ad3e179ab303");
//        config.put("TRTC-SDKAPPID", "1400474322");
//        config.put("TRTC-SECRET", "739991025da854e1c67ca2bf1809a564c324926b22dfc1cdac2d7726622a5ea2");
        config.put("OAPI-AUTH-ID", "1");
        appAuth.put(MP_APPID_LOGIS_JH, config);
        //安锐达
        config = new HashMap();
        config.put("MP-SECRET", "123a21b95cf3524f26198ec340f72e1c");
//        config.put("TRTC-SDKAPPID", "1400474322");
//        config.put("TRTC-SECRET", "739991025da854e1c67ca2bf1809a564c324926b22dfc1cdac2d7726622a5ea2");
        config.put("OAPI-AUTH-ID", "1");
        appAuth.put(MP_APPID_LOGIS_ARD, config);
    }

    /**
     * 获取默认配置
     * @return
     */
    public static Map<String, String> getDefalutConfig(){
        return appAuth.get(MP_APPID_LOGIS_ARD);
    }

    public static String getDefalutOPENAPIAuthId(){
        return appAuth.get(MP_APPID_LOGIS_ARD).get("OAPI-AUTH-ID");
    }
}
