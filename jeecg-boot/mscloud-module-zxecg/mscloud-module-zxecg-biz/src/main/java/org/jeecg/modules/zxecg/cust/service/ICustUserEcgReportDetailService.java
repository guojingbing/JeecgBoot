package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportDetail;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/2
 */


public interface ICustUserEcgReportDetailService extends IService<CustUserEcgReportDetail> {
    List<Long> getRepIdsByEcgId(Long ecgId);

    List<CustUserEcgReportDetail> getListByRepId(Long repId);

    void deleteByEcgId(Long ecgId);

    void deleteByRepId(Long repId);
}
