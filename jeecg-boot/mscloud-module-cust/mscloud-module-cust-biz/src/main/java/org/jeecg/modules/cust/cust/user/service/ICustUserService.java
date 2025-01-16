package org.jeecg.modules.cust.cust.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cust.cust.user.entity.CustUser;

import java.util.Map;

/**
 * @Description: 用户业务
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
public interface ICustUserService extends IService<CustUser> {
    /**
     * 通过手机号获取业务用户账号信息
     * @param phoneNumber
     * @return
     */
    CustUser getUserByPhoneNumber(String phoneNumber, String appid);

    /**
     * 通过第三方用户账号获取绑定的业务账号
     * @param thirdType
     * @param appid
     * @param thirdUserId
     * @return
     */
    Map getUserByThirdUserId(String thirdType, String appid, String thirdUserId);
}
