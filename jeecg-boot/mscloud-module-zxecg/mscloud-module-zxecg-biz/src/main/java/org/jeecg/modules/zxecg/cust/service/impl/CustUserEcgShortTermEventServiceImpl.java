package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgShortTermEvent;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgShortTermEventMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgShortTermEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 */

@Service
public class CustUserEcgShortTermEventServiceImpl extends ServiceImpl<CustUserEcgShortTermEventMapper, CustUserEcgShortTermEvent> implements ICustUserEcgShortTermEventService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEventsByEcgId(Long ecgId) {
        this.baseMapper.deleteEventsByEcgId(ecgId);
    }
}
