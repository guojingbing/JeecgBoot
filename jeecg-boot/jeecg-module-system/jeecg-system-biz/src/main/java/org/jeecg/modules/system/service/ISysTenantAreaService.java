package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.SysTenantArea;

import java.util.List;

/**
 * @Description: 租户授权区域
 */
public interface ISysTenantAreaService extends IService<SysTenantArea> {
    /**
     * 租户区域授权
     * @param records
     * @return
     */
    List<SysTenantArea> setTenantAreas(Integer tenantId, List<SysTenantArea> records);

    /**
     * 删除租户区域授权
     * @param tenantId
     */
    void delTenantAreas(Integer tenantId);
}
