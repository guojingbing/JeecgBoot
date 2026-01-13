package org.jeecg.modules.stock.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.util.http.HttpUtil;
import org.jeecg.modules.stock.exception.TushareAPIException;

/**
 * @Description: Tushare接口处理类
 * @Author: Kingpin
 * @Date: 2021-06-18 16:31:02
 **/
public class TushareAPIHttpClient {
    final static String baseUrl = "http://api.waditu.com";

    public static JSONObject executeGetAPI(String apiName, JSONObject params, String fields) throws TushareAPIException {
        TushareParamJSONObject p = new TushareParamJSONObject(apiName, params, fields);
        try {
            JSONObject jsonObject = HttpUtil.getForJSONObject(baseUrl, p, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject executePostAPI(String apiName, JSONObject params, String fields) throws TushareAPIException {
        TushareParamJSONObject bodyParams = new TushareParamJSONObject(apiName, params, fields);
        try {
            JSONObject jsonObject = HttpUtil.postBodyForJSONObject(baseUrl, null, null, bodyParams, null, null);
            if(jsonObject==null){
                throw new TushareAPIException("Tushare接口【"+apiName+"】请求结果异常,返回结果为空");
            }
            if(jsonObject.getInteger("code")!=0){
                if(StringUtils.isNotBlank(jsonObject.getString("msg"))){
                    throw new TushareAPIException("Tushare接口【"+apiName+"】请求结果异常,"+jsonObject.getString("msg"));
                }else{
                    throw new TushareAPIException("Tushare接口【"+apiName+"】请求结果异常,发生未知错误");
                }
            }
            return jsonObject.getJSONObject("data");
        } catch (Exception e) {
            e.printStackTrace();
            throw new TushareAPIException("Tushare接口【"+apiName+"】请求发生异常");
        }
    }
}
