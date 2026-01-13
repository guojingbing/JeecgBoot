package org.jeecg.modules.stock.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2021-06-18 16:36:31
 **/
public class TushareParamJSONObject extends JSONObject{
    final String token="3b9dab3d791354c86d7a9ce6edb50600be8b83b25c67e7b5fc83bff5";
    public TushareParamJSONObject(String apiName,JSONObject params,String fields){
        this.put("token",token);
        this.put("api_name",apiName);
        this.put("params",params);
        if(fields!=null){
            this.put("fields",fields);
        }
    }
}
