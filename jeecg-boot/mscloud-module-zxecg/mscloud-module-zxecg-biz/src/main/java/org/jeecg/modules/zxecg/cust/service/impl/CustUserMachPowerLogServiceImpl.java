package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserMachPowerLog;
import org.jeecg.modules.zxecg.cust.mapper.CustUserMachPowerLogMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserMachPowerLogService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 * @description
 */

@Service
public class CustUserMachPowerLogServiceImpl extends ServiceImpl<CustUserMachPowerLogMapper, CustUserMachPowerLog> implements ICustUserMachPowerLogService {
    @Override
    public Page<Map> loadListPaging(Page<Map> page, Long bindingId, String column, String order) {
        return page.setRecords(this.baseMapper.loadListPaging(page, bindingId, column, order));
    }
}
