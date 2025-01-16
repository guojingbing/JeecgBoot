package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.system.entity.SysTenantArea;
import org.jeecg.modules.system.mapper.SysTenantAreaMapper;
import org.jeecg.modules.system.service.ISysTenantAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 租户区域授权
 */
@Service
public class SysTenantAreaServiceImpl extends ServiceImpl<SysTenantAreaMapper, SysTenantArea> implements ISysTenantAreaService {
    @Autowired
    private SysTenantAreaMapper sysTenantAreaMapper;

    @Override
    public List<SysTenantArea> setTenantAreas(Integer tenantId, List<SysTenantArea> records) {
        this.delTenantAreas(tenantId);
        this.saveBatch(records);
        return records;
    }

    /**
     * 删除租户区域授权
     * @param tenantId
     */
    public void delTenantAreas(Integer tenantId) {
//        LambdaQueryWrapper<SysTenantArea> query = new LambdaQueryWrapper<>();
//        query.eq(SysTenantArea::getTenantId, tenantId);
        List<Integer> tenantIdList=Arrays.asList(tenantId);
        sysTenantAreaMapper.deleteAreasByTenantIds(tenantIdList);
    }
}
