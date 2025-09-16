package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportFragStyle;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/11
 */


public interface ICustUserEcgReportFragStyleService extends IService<CustUserEcgReportFragStyle> {
    void fragStyle(Long repId, Integer categoryId, Integer style);
}
