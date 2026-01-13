package org.jeecg.modules.stock.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.stock.user.entity.CustUserThirdAccount;
import org.jeecg.modules.stock.user.mapper.CustUserThirdAccountMapper;
import org.jeecg.modules.stock.user.service.ICustUserThirdAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户三方登录
 * @Author:
 * @Date:   2020-02-17
 * @Version: V1.0
 */
@Service
public class CustUserThirdAccountServiceImpl extends ServiceImpl<CustUserThirdAccountMapper, CustUserThirdAccount> implements ICustUserThirdAccountService {
    @Autowired
    private CustUserThirdAccountMapper custUserThirdAccountMapper;
}
