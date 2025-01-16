package org.jeecg.modules.openapi.base.mapper;

import org.jeecg.modules.openapi.base.entity.SysOpenAuthUserApi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 三方授权机构管理接口配置
 *
 * @author Administrator
 */
public interface SysOpenAuthUserApiMapper extends BaseMapper<SysOpenAuthUserApi> {

    /**
     * 根据授权机构id查询接口配置信息
     * @param authId
     * @param type
     * @return
     */
    List<SysOpenAuthUserApi> selectListByAuthId(@Param("authId") String authId, @Param("type") Integer type, @Param("code") Integer code);
}
