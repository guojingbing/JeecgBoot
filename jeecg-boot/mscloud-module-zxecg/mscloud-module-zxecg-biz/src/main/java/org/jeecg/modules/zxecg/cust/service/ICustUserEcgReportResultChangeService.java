package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportResultChange;

import java.util.List;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/10
 * @description 智能筛查自动分析结果操作
 */


public interface ICustUserEcgReportResultChangeService extends IService<CustUserEcgReportResultChange> {
    void repResultChange(Long repId, Integer typeId, Integer operId, List<Long> ecgIds, List<Long> abnTimes, Long loginUserId);
}
