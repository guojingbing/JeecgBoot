package org.jeecg.common.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2024-07-17 10:31:14
 **/
@Component
public class SysRedisUtil extends RedisUtil {
    /**
     * 短信验证码缓存key
     * @param templateCode
     * @param smsTel
     * @return
     */
    public String initSmsCodeKey(String templateCode,String smsTel){
        return initKey("cache","sms","code",templateCode,smsTel);
    }

    /**
     * 获取阿里云NLS token缓存key
     * @return
     */
    public String getAliyunNLSTokenCacheKey(){
        return initKey("cache","aliyun","nls","token");
    }

    /**
     * LLM豆包用户聊天历史记录
     * @param userId
     * @return
     */
    public String getLLMDoubaoMessageHis(String userId){
        return initKey("cache","llm","chat","his","doubao",userId);
    }

    /**
     * 组装缓存key
     * @param keys
     * @return
     */
    public String initKey(String... keys){
        if(keys!=null){
            String keyStr="";
            for(String obj:keys){
                if(StringUtils.isNotBlank(keyStr)){
                    keyStr+=":";
                }
                keyStr+=obj;
            }
            return keyStr;
        }
        return null;
    }
}
