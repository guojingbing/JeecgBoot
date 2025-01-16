package org.jeecg.modules.zxecg.oapi.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.ExternalApiException;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.openapi.base.entity.SysOpenAuthUser;
import org.jeecg.modules.openapi.base.entity.SysOpenAuthUserApi;
import org.jeecg.modules.openapi.base.enums.SysOpenAuthUserTypeEnum;
import org.jeecg.modules.openapi.base.mapper.SysOpenAuthUserApiMapper;
import org.jeecg.modules.openapi.base.mapper.SysOpenAuthUserMapper;
import org.jeecg.modules.zxecg.constant.CommConstants;
import org.jeecg.modules.zxecg.oapi.ExternalAPIRequest;
import org.jeecg.modules.zxecg.oapi.enums.ExternalAPICodeEnum;
import org.jeecg.modules.zxecg.oapi.service.IZxecgExternalAPICallService;
import org.jeecg.modules.zxecg.vo.ZxecgUserReportVo;
import org.jeecg.modules.zxecg.vo.ZxecgUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ZxecgExternalAPICallServiceImpl implements IZxecgExternalAPICallService {
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private SysOpenAuthUserMapper sysOpenAuthUserMapper;
    @Resource
    private SysOpenAuthUserApiMapper sysOpenAuthUserApiMapper;

    //开放对接机构id
    private final static String oauthId="zxecg-iagent";

    /**
     * 调用符合平台标准规范外部API接口方法
     *
     * @param oauthId
     * @param apiCode
     * @param params
     * @return
     */
    JSONObject executeAPIRequest(String oauthId, Integer apiCode, JSONObject params) {
        try{
            //检查并获取token
            SysOpenAuthUser sysOpenAuthUser = sysOpenAuthUserMapper.selectById(oauthId);
            String token = checkOAPIToken(sysOpenAuthUser);
            Map<String, Object> headers = new HashMap<>();
            headers.put("X-Access-Token", token);
            //根据对接的判图软件确定调用接口
            String urlRoot = sysOpenAuthUser.getApiRootUrl();
            //接口路径从授权机构API配置信息根据apiCode获取
            List<SysOpenAuthUserApi> userApiList = sysOpenAuthUserApiMapper.selectListByAuthId(oauthId, sysOpenAuthUser.getType(), apiCode);
            if (CollectionUtils.isEmpty(userApiList) || StringUtils.isBlank(userApiList.get(0).getApiUri())) {
                throw new ExternalApiException("授权机构【" + sysOpenAuthUser.getCorpName() + "】未正确配置接口【" + apiCode + "】信息");
            }
            SysOpenAuthUserApi authUserApi = userApiList.get(0);
            String uri = authUserApi.getApiUri();
            String apiName = authUserApi.getApiName();

            //请求接口返回结果
            ExternalAPIRequest tReq = new ExternalAPIRequest(urlRoot, uri, params, headers);
            JSONObject data = tReq.reqBodyParams();
            if (data.getBoolean("success")) {
                JSONObject result = (JSONObject) data.get("result");
                return result;
            } else {
                String msg=data.getString("message");
                if(null==msg){
                    msg=data.getString("result");
                }
                throw new ExternalApiException("授权机构【" + sysOpenAuthUser.getCorpName() + "】" + apiName + "调用发生异常：" + msg);
            }
        }catch (ExternalApiException ex){
            throw ex;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            return null;
        }
    }

    /**
     * OPENAPI接口权限token检查，从缓存获取若过期重新获取
     * @param sysOpenAuthUser
     * @return
     * @throws ExternalApiException
     */

    @Override
    public String checkOAPIToken(SysOpenAuthUser sysOpenAuthUser) throws ExternalApiException {
        if (sysOpenAuthUser == null) {
            throw new ExternalApiException("授权对象不存在，鉴权失败");
        }
        String token = (String) redisUtil.get(CommConstants.ZXTHEALTH.ZXTHEALTH_TOKEN_KEY + "-" + sysOpenAuthUser.getId());
//        if (StringUtils.isNotBlank(token)) {
//            return token;
//        }
        JSONObject json = getToken(sysOpenAuthUser);
        return json.getString("token");
    }

    @Override
    public JSONObject getToken(SysOpenAuthUser sysOpenAuthUser) throws ExternalApiException {
        try {
            //根据授权方获取请求地址
            String urlRoot = sysOpenAuthUser.getApiRootUrl();
            String uri = ExternalAPICodeEnum.ECG_TOKEN.getDefaultUri();
            Integer code = ExternalAPICodeEnum.ECG_TOKEN.getCode();
            if (SysOpenAuthUserTypeEnum.HOSP.getCode() == sysOpenAuthUser.getType()) {
                code = ExternalAPICodeEnum.ORDER_ORIGN_TOKEN.getCode();
                uri = ExternalAPICodeEnum.ORDER_ORIGN_TOKEN.getDefaultUri();
            }

            List<SysOpenAuthUserApi> userApiList = sysOpenAuthUserApiMapper.selectListByAuthId(sysOpenAuthUser.getId(), sysOpenAuthUser.getType(), code);

            if (CollectionUtils.isNotEmpty(userApiList)) {
                SysOpenAuthUserApi authUserApi = userApiList.get(0);
                if (StringUtils.isNotBlank(authUserApi.getApiUri())) {
                    uri = authUserApi.getApiUri();
                } else {
                    if (SysOpenAuthUserTypeEnum.HOSP.getCode() == sysOpenAuthUser.getType()) {
                        throw new ExternalApiException("授权机构【" + sysOpenAuthUser.getCorpName() + "】未配置token获取接口地址");
                    }
                }
            } else {
                if (SysOpenAuthUserTypeEnum.HOSP.getCode() == sysOpenAuthUser.getType()) {
                    throw new ExternalApiException("授权机构【" + sysOpenAuthUser.getCorpName() + "】未配置token获取接口地址");
                }
            }

            //拼装请求参数
            JSONObject params = new JSONObject();
            params.put("accessKey", sysOpenAuthUser.getCallApiKey());
            params.put("accessSecret", sysOpenAuthUser.getCallApiSecret());

            //请求并返回结果
            ExternalAPIRequest tReq = new ExternalAPIRequest(urlRoot, uri, params, null);
            JSONObject data = tReq.reqBodyParams();

            //结果处理
            if (data.getBoolean("success")) {
                JSONObject result = data.getJSONObject("result");
                String token = result.getString("token");
                long time = result.getLong("expiredTime");
                int seconds = (int) (time - System.currentTimeMillis()) / 1000;
                redisUtil.set(CommConstants.ZXTHEALTH.ZXTHEALTH_TOKEN_KEY + "-" + sysOpenAuthUser.getId(), token, seconds);
                return result;
            } else {
                throw new ExternalApiException(sysOpenAuthUser.getCorpFullName() + "【" + sysOpenAuthUser.getCallApiKey() + "】鉴权获取token失败," + data.getString("message"));
            }
        } catch (Exception ex) {
            throw new ExternalApiException(sysOpenAuthUser.getCorpFullName() + "【" + sysOpenAuthUser.getCallApiKey() + "】鉴权获取token失败," + ex.getMessage());
        }
    }

    @Override
    public Map<String,ZxecgUserReportVo> getUserRepInfo(String userId, String repDate) throws ExternalApiException {
        JSONObject paramObject = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        params.put("repDate", repDate);
        paramObject.put("params", params);
        JSONObject result = executeAPIRequest(oauthId, ExternalAPICodeEnum.ECG_GET_USER_REPINFO.getCode(), paramObject);
        if(null==result){
            return null;
        }
        JSONObject data=result.getJSONObject("data");
        if(null==data){
            return null;
        }
        Map repMap=new HashMap();
        JSONObject doctorRepObj=data.getJSONObject("doctorRep");
        if(null!=doctorRepObj){
            ZxecgUserReportVo reportVo = doctorRepObj.toJavaObject(ZxecgUserReportVo.class);
            repMap.put("doctorRep",reportVo);
        }
        JSONObject autoRepObj=data.getJSONObject("autoRep");
        if(null!=autoRepObj){
            ZxecgUserReportVo reportVo = autoRepObj.toJavaObject(ZxecgUserReportVo.class);
            repMap.put("autoRep",reportVo);
        }

        return repMap;
    }

    @Override
    public List<ZxecgUserVo> getUserFriends(String userId) throws ExternalApiException {
        JSONObject paramObject = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        paramObject.put("params", params);
        JSONObject result = executeAPIRequest(oauthId, ExternalAPICodeEnum.ECG_GET_USER_FRIENDS.getCode(), paramObject);
        if(result!=null){
            JSONArray friendArr=result.getJSONArray("data");
            if(friendArr!=null&&friendArr.size()>0){
               List<ZxecgUserVo> friends=friendArr.toJavaList(ZxecgUserVo.class);
               return friends;
            }
        }
        return null;
    }

    @Override
    public ZxecgUserVo getUserInfo(String userId) throws ExternalApiException {
        JSONObject paramObject = new JSONObject();
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        paramObject.put("params", params);

        JSONObject result = executeAPIRequest(oauthId, ExternalAPICodeEnum.ECG_GET_USER_INFO.getCode(), paramObject);
        if(result!=null){
            JSONObject friendObj=result.getJSONObject("data");
            if(friendObj!=null){
                ZxecgUserVo friend = friendObj.toJavaObject(ZxecgUserVo.class);
                return friend;
            }
        }

        return null;
    }
}
