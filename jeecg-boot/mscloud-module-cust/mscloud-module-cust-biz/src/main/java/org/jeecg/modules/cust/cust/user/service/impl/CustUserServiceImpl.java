package org.jeecg.modules.cust.cust.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.cust.cust.user.entity.CustUser;
import org.jeecg.modules.cust.cust.user.mapper.CustUserMapper;
import org.jeecg.modules.cust.cust.user.mapper.CustUserThirdAccountMapper;
import org.jeecg.modules.cust.cust.user.service.ICustUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description: 用户业务
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class CustUserServiceImpl extends ServiceImpl<CustUserMapper, CustUser> implements ICustUserService {
    @Autowired
    CustUserMapper custUserMapper;
    @Autowired
    CustUserThirdAccountMapper custUserThirdAccountMapper;

    @Override
    public CustUser getUserByPhoneNumber(String phoneNumber,String appid) {
        LambdaQueryWrapper<CustUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(true, CustUser::getPhoneNumber, phoneNumber).eq(true, CustUser::getAppid, appid);
        return custUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Map getUserByThirdUserId(String thirdType, String appid, String thirdUserId){
        return custUserMapper.getUserByThirdUserId(thirdType, appid, thirdUserId);
    }
}
