package org.jeecg.modules.zxecg.oapi.service;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.exception.ExternalApiException;
import org.jeecg.modules.openapi.base.entity.SysOpenAuthUser;
import org.jeecg.modules.zxecg.vo.ZxecgUserReportVo;
import org.jeecg.modules.zxecg.vo.ZxecgUserVo;

import java.util.List;
import java.util.Map;

/**
 * 操作外部API接口
 */
public interface IZxecgExternalAPICallService {
    /**
     * 鉴权获取token
     *
     * @param sysOpenAuthUser
     * @return JSONObject
     * @throws ExternalApiException
     */
    JSONObject getToken(SysOpenAuthUser sysOpenAuthUser) throws ExternalApiException;

    /**
     * 根据authId获取token
     *
     * @param sysOpenAuthUser
     * @return String
     * @throws ExternalApiException
     */
    String checkOAPIToken(SysOpenAuthUser sysOpenAuthUser) throws ExternalApiException;

    /**
     * 获取指定用户报告结论信息，用于智能体查询
     * @param userId
     * @param repDate
     * @return
     */
    Map<String,ZxecgUserReportVo> getUserRepInfo(String userId, String repDate) throws ExternalApiException;

    /**
     * 查询用户亲友列表信息
     * @param userId
     * @return
     * @throws ExternalApiException
     */
    List<ZxecgUserVo> getUserFriends(String userId) throws ExternalApiException;

    /**
     * 查询用户信息
     * @param userId
     * @return
     * @throws ExternalApiException
     */
    ZxecgUserVo getUserInfo(String userId) throws ExternalApiException;
}
