package org.jeecg.modules.zxecg.sysuser.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.desensitization.annotation.SensitiveEncode;
import org.jeecg.modules.zxecg.constant.ZxecgCacheConstant;
import org.jeecg.modules.zxecg.entity.CommSystemUser;
import org.jeecg.modules.zxecg.sysuser.mapper.SysUserMapper;
import org.jeecg.modules.zxecg.sysuser.service.ISysUserService;
import org.jeecg.modules.zxecg.sysuser.vo.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 测试Service
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, CommSystemUser> implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUserVO getSystemUserVOByUserNoOrPhone(String userNo) {
        return sysUserMapper.getSystemUserByUserNoOrPhone(userNo,userNo);
    }

    @Override
    @Cacheable(cacheNames= ZxecgCacheConstant.ZXECG_SYS_USER_CACHE, key="#userId")
    @SensitiveEncode
    public SysUserVO getSystemUserVOByUserId(Long userId) {
        return sysUserMapper.selectByUserId(userId);
    }
}
