package org.jeecg.modules.openapi.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.openapi.base.entity.SysOpenAuthUserApi;
import org.jeecg.modules.openapi.base.mapper.SysOpenAuthUserApiMapper;
import org.jeecg.modules.openapi.base.service.ISysOpenAuthUserApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 三方授权机构管理接口配置
 * @author Administrator
 */
@Service
public class SysOpenAuthUserServiceApiImpl extends ServiceImpl<SysOpenAuthUserApiMapper, SysOpenAuthUserApi> implements ISysOpenAuthUserApiService {

    @Resource
    SysOpenAuthUserApiMapper apiMapper;

    @Override
    public List<SysOpenAuthUserApi> selectListByAuthId(String id, Integer type,Integer code) {
        return apiMapper.selectListByAuthId(id,type,code);
    }
}
