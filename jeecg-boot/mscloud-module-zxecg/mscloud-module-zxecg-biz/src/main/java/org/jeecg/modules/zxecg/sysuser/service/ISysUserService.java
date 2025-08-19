package org.jeecg.modules.zxecg.sysuser.service;

import org.jeecg.modules.zxecg.sysuser.vo.SysUserVO;

/**
 * 测试接口
 */
public interface ISysUserService {
    SysUserVO getSystemUserVOByUserNoOrPhone(String userNo);
}
