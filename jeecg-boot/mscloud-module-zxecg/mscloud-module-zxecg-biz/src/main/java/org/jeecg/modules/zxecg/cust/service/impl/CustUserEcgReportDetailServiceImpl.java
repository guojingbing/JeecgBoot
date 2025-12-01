package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportDetail;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportDetailMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/2
 */

@Service
public class CustUserEcgReportDetailServiceImpl extends ServiceImpl<CustUserEcgReportDetailMapper, CustUserEcgReportDetail> implements ICustUserEcgReportDetailService {
    @Override
    public List<Long> getRepIdsByEcgId(Long ecgId) {
        return this.baseMapper.getRepIdsByEcgId(ecgId);
    }

    @Override
    public List<CustUserEcgReportDetail> getListByRepId(Long repId) {
        return this.baseMapper.getListByRepId(repId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByEcgId(Long ecgId) {
        this.baseMapper.deleteByEcgId(ecgId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRepId(Long repId) {
        this.baseMapper.deleteByRepId(repId);
    }
}
