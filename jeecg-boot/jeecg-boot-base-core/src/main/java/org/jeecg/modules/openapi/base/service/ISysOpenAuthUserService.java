package org.jeecg.modules.openapi.base.service;

import org.jeecg.modules.openapi.base.entity.SysOpenAuthUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface ISysOpenAuthUserService extends IService<SysOpenAuthUser> {

    /**
     * 根据类型查询第三方授权
     * @param type 第三方授权类型
     * @return
     */
    List<SysOpenAuthUser> querySuppliers(int type);

    /**
     * 缓存授权对象的IP白名单
     * @return
     */
    void redisCacheIP();
}
