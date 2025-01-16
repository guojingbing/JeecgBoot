package org.jeecg.modules.zxecg.oapi;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.exception.ExternalApiException;
import org.jeecg.common.util.http.HttpUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 外部接口请求包装类
 * 调用外部接口时使用
 */
@Slf4j
public class ExternalAPIRequest {
    private JSONObject bodyRaw;
    private Map reqParams;
    private String URL;
    private Map<String, Object> headers;

    /**
     * 初始化接口
     * @param urlRoot 调用接口的根路径
     * @param uri "/oapi/u/GET/ecgs"
     * @param params
     */
    public ExternalAPIRequest(String urlRoot, String uri, JSONObject params, Map<String,Object> headers){
        URL=urlRoot+uri;
        bodyRaw=new JSONObject();
        reqParams=new HashMap();
        this.headers=headers;
        if(params!=null){
            Iterator iter=params.entrySet().iterator();
            while (iter.hasNext()){
                Map.Entry entry=(Map.Entry)iter.next();
                bodyRaw.put(entry.getKey().toString(),entry.getValue());
                reqParams.put(entry.getKey().toString(),entry.getValue());
            }
        }
    }

    /**
     * 发起post请求并返回结果
     * post body传参
     * @return
     */
    public JSONObject reqBodyParams(){
        try {
            JSONObject respData= HttpUtil.postBodyForJSONObject(URL,null,headers,bodyRaw,null,null);
            if(respData==null||!respData.containsKey("success")){
                throw new ExternalApiException("外部接口【"+URL+"】返回结果异常");
            }
            return respData;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ExternalApiException("外部接口【"+URL+"】调用发生异常",e);
        }
    }

    /**
     * 发起get请求并返回结果
     * @return
     * @throws ExternalApiException
     */
    public JSONObject reqParams() throws ExternalApiException {
        try {
            JSONObject respData = HttpUtil.getForJSONObject(URL,reqParams,headers,null,null);
            return respData;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ExternalApiException("外部接口调用异常："+URL);
        }
    }
    public static void main(String args[]){
        JSONObject params=new JSONObject();
        params.put("accessKey","111");
        params.put("accessSecret","111");
        ExternalAPIRequest tReq=new ExternalAPIRequest("http://ecg.zxthealth.com:8086/ecg","/oapi/token",params,null);
        JSONObject data=tReq.reqBodyParams();
        System.out.println(data);
    }
}
