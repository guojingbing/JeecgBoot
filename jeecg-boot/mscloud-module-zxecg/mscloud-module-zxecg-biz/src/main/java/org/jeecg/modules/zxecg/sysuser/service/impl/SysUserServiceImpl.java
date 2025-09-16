package org.jeecg.modules.zxecg.sysuser.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.entity.CommSystemUser;
import org.jeecg.modules.zxecg.sysuser.mapper.mysql.SysUserMapper;
import org.jeecg.modules.zxecg.sysuser.service.ISysUserService;
import org.jeecg.modules.zxecg.sysuser.vo.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
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
}
