package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportDiagnosis;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/2
 */


public interface CustUserEcgReportDiagnosisMapper extends BaseMapper<CustUserEcgReportDiagnosis> {
    CustUserEcgReportDiagnosis getDiagnosisByRepId(@Param("repId") Long repId);
}
