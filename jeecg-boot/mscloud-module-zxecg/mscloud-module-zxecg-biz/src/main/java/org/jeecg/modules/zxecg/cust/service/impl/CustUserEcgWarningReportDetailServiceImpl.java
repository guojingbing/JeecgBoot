package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgWarningReportDetail;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgWarningReportDetailMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgWarningReportDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/27
 * @description
 */

@Service
public class CustUserEcgWarningReportDetailServiceImpl extends ServiceImpl<CustUserEcgWarningReportDetailMapper, CustUserEcgWarningReportDetail> implements ICustUserEcgWarningReportDetailService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByEcgId(Long ecgId) {
        this.baseMapper.deleteByEcgId(ecgId);
    }
}
