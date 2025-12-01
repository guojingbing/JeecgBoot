package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportDiagnosis;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/2
 */


public interface ICustUserEcgReportDiagnosisService extends IService<CustUserEcgReportDiagnosis> {
    CustUserEcgReportDiagnosis getDiagnosisByRepId(Long repId);
}
