package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportOrder;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportOrderMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportOrderService;
import org.springframework.stereotype.Service;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/5
 */

@Service
public class CustUserEcgReportOrderServiceImpl extends ServiceImpl<CustUserEcgReportOrderMapper, CustUserEcgReportOrder> implements ICustUserEcgReportOrderService {
    @Override
    public CustUserEcgReportOrder getByRepId(Long repId) {
        return this.baseMapper.getByRepId(repId);
    }
}
