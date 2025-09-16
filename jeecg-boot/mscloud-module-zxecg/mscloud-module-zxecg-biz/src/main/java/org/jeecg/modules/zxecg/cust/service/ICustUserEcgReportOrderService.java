package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportOrder;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/5
 */


public interface ICustUserEcgReportOrderService extends IService<CustUserEcgReportOrder> {
    CustUserEcgReportOrder getByRepId(Long repId);
}
