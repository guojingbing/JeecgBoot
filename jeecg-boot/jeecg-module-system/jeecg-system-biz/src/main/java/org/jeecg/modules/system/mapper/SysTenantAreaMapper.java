package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.system.entity.SysTenantArea;

import java.util.List;

/**
 * @Description: 租户区域授权
 */
public interface SysTenantAreaMapper extends BaseMapper<SysTenantArea> {
    /**
     * 删除租户区域授权
     * @param tenantIdList
     */
    void deleteAreasByTenantIds(@Param("tenantIdList") List<Integer> tenantIdList);
}
