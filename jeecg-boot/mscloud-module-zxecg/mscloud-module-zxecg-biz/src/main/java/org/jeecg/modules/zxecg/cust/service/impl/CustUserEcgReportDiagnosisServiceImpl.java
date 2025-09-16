package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportDiagnosis;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportDiagnosisMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportDiagnosisService;
import org.springframework.stereotype.Service;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/2
 */

@Service
public class CustUserEcgReportDiagnosisServiceImpl extends ServiceImpl<CustUserEcgReportDiagnosisMapper, CustUserEcgReportDiagnosis> implements ICustUserEcgReportDiagnosisService {
    @Override
    public CustUserEcgReportDiagnosis getDiagnosisByRepId(Long repId) {
        return this.baseMapper.getDiagnosisByRepId(repId);
    }
}
