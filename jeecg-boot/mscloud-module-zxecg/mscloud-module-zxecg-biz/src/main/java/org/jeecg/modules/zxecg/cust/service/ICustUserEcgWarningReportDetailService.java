package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgWarningReportDetail;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/27
 * @description 监测筛查报告相关操作
 */


public interface ICustUserEcgWarningReportDetailService extends IService<CustUserEcgWarningReportDetail> {
    void deleteByEcgId(Long ecgId);
}
