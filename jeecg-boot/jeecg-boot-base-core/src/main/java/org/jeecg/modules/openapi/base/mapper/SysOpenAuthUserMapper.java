package org.jeecg.modules.openapi.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.openapi.base.entity.SysOpenAuthUser;

import java.util.List;
import java.util.Map;

/**
 * 三方授权机构管理
 *
 * @author Administrator
 */
public interface SysOpenAuthUserMapper extends BaseMapper<SysOpenAuthUser> {
    List<Map> selectUserOwners(@Param("userId") String userId);

    /**
     * 根据类型查询第三方授权
     *
     * @param type
     * @return
     */
    @Select("SELECT * FROM sys_open_auth_user where type = #{type}  and auth_status=1")
    List<SysOpenAuthUser> selectSysOpenAuthUserByType(@Param("type") int type);
}
