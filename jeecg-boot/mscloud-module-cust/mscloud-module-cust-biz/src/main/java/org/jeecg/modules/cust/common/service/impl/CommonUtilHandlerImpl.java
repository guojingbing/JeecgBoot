package org.jeecg.modules.cust.common.service.impl;

import com.tencentyun.TLSSigAPIv2;
import org.jeecg.modules.cust.common.constant.MpConfig;
import org.jeecg.modules.cust.common.service.ICommonUtilHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2021-05-31 13:58:04
 **/
@Service
public class CommonUtilHandlerImpl implements ICommonUtilHandler {
    private Map<String, Map<String, String>> appAuth= MpConfig.appAuth;
    @Override
    public Map getTRTCConfig(String userID,String appid) {
        Map map = new HashMap();
        long sdkAppId=Long.parseLong(appAuth.get(appid).get("TRTC-SDKAPPID"));
        String sdkSecret=appAuth.get(appid).get("TRTC-SECRET");
        TLSSigAPIv2 api = new TLSSigAPIv2(sdkAppId, sdkSecret);
        String userSig = api.genUserSig(userID, 3 * 86400);
        Map trtcMap=new HashMap();
        trtcMap.put("sdkAppID", sdkAppId);
        trtcMap.put("userSig", userSig);
        map.put("trtcConfig",trtcMap);
        return trtcMap;
    }
}
