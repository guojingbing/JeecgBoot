package org.jeecg.modules.iagent.nls.common;

import com.alibaba.nls.client.AccessToken;
import org.jeecg.common.util.SysRedisUtil;
import org.jeecg.modules.iagent.nls.common.config.AliyunNLSConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 阿里云NLS token处理类
 * @Author: Kingpin
 * @Date: 2024-12-12 14:36:50
 **/
@Component
public class AliyunNLSTokenUtil {
    @Autowired
    private AliyunNLSConfig config;
    @Autowired
    private SysRedisUtil sysRedisUtil;

    /**
     * 获取系统配置的NLS appkey
     * @return
     */
    public String getDefaultNlsAppKey(){
        return config.getAppKey();
    }

    /**
     * 获取token
     * @return
     */
    public String getToken(){
        String key=sysRedisUtil.getAliyunNLSTokenCacheKey();
        Object value=sysRedisUtil.get(key);
        if(value!=null){
            String thisTokenKey=key+":"+value;
            if(null!=sysRedisUtil.get(thisTokenKey)){
                return (String)value;
            }
        }

        String accessKeyId = config.getAccessKeyId();
        String accessKeySecret = config.getAccessKeySecret();
        AccessToken accessToken = new AccessToken(accessKeyId, accessKeySecret);
        try {
            accessToken.apply();
            String token=accessToken.getToken();
            long expiredSeconds=accessToken.getExpireTime()-System.currentTimeMillis()/1000;
            if(expiredSeconds<0){
                return null;
            }
            //缓存有效期设置为到期前5分钟
            expiredSeconds=expiredSeconds>5*60?expiredSeconds-5*60:expiredSeconds;
            sysRedisUtil.set(key,token,expiredSeconds);
            //缓存每个token
            sysRedisUtil.set(key+":"+token,token,expiredSeconds);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
