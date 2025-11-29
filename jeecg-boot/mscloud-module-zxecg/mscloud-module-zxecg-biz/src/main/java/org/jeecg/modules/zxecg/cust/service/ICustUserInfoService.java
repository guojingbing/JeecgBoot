package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserInfo;
import org.jeecg.modules.zxecg.cust.vo.CardInfoVo;
import org.jeecg.modules.zxecg.cust.vo.CustUserInfoVo;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/21
 * @description 用户相关操作
 */


public interface ICustUserInfoService extends IService<CustUserInfo> {
    /**
     * 用户列表查询
     *
     * @param pageList
     * @param likeMap
     * @param column
     * @param order
     * @return
     */
    Page<Map> loadListPaging(Page<Map> pageList, Map<String, Object> likeMap, String column, String order);

    /**
     * 根据用户id查询用户详情
     *
     * @param userId
     * @return
     */
    CustUserInfoVo userInfo(Long userId);

    Page<Map> loadVoucherListPaging(Page<Map> pageList, Map<String, Object> likeMap, String column, String order);

    Page<Map> loadRelatedListPaging(Page<Map> pageList, Map<String, Object> likeMap, String column, String order);

    /**
     * 根据用户账号或者手机号查询用户信息
     *
     * @param userNo
     * @return
     */
    CustUserInfo getUserInfoByNoOrTel(String userNo,String userTel);

    /**
     * 根据卡号查询卡券信息
     *
     * @param cardNo
     * @return
     */
    CardInfoVo getVoucherInfo(String cardNo);

}
