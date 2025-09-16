package org.jeecg.modules.zxecg.sysuser.mapper.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.zxecg.entity.CommSystemUser;
import org.jeecg.modules.zxecg.sysuser.vo.SysUserVO;
import org.springframework.data.repository.query.Param;

public interface SysUserMapper extends BaseMapper<CommSystemUser> {
    SysUserVO getSystemUserByUserNoOrPhone(@Param("userNo") String userNo, @Param("phone") String phone);
}
