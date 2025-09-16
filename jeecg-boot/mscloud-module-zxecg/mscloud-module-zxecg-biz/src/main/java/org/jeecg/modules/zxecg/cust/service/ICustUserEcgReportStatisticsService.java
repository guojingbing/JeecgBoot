package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportStatistics;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/1
 */


public interface ICustUserEcgReportStatisticsService extends IService<CustUserEcgReportStatistics> {
    void deleteByRepId(Long repId);

    CustUserEcgReportStatistics getByRepId(Long repId);

    void saveAdvice(Long repId, String conclusion, Long loginUserId);

    CustUserEcgReportStatistics saveAnaConclusion(Long repId, String templ, String content, Integer wrap, Long loginUserId);

    void itemPrint(Long repId, String item, boolean printId);
}
