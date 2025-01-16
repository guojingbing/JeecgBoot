package org.jeecg.modules.openapi.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.util.internal.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.openapi.base.entity.SysOpenAuthUser;
import org.jeecg.modules.openapi.base.mapper.SysOpenAuthUserMapper;
import org.jeecg.modules.openapi.base.service.ISysOpenAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 三方授权机构管理
 * @author Administrator
 */
@Service
public class SysOpenAuthUserServiceImpl extends ServiceImpl<SysOpenAuthUserMapper, SysOpenAuthUser> implements ISysOpenAuthUserService {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<SysOpenAuthUser> querySuppliers(int type) {
        return baseMapper.selectSysOpenAuthUserByType(type);
    }

    @Override
    public void redisCacheIP(){
        List<SysOpenAuthUser> list=this.list();
        if(!CollectionUtils.isEmpty(list)){
            for(SysOpenAuthUser obj:list){
                if(!StringUtil.isNullOrEmpty(obj.getIpRange())){
                    redisUtil.set(CommonConstant.PREFIX_OAPI_IP+obj.getId(), obj.getIpRange());
                }
            }
        }
    }
}
