package org.jeecg.modules.zxecg.sysuser.service;

import org.jeecg.modules.zxecg.sysuser.vo.SysUserVO;

/**
 * 测试接口
 */
public interface ISysUserService {
    /**
     * 根据用户账号或手机号查询系统用户信息
     * @param userNo 用户账号或手机号
     * @return 系统用户信息
     */
    SysUserVO getSystemUserVOByUserNoOrPhone(String userNo);
    /**
     * 根据用户ID查询系统用户信息
     * @param userId 用户ID
     * @return 系统用户信息
     */
    SysUserVO getSystemUserVOByUserId(Long userId);
}
