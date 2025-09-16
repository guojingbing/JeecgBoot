package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportScreenVisit;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/3
 */


public interface ICustUserEcgReportScreenVisitService extends IService<CustUserEcgReportScreenVisit> {
    Page<Map> loadListPaging(Page<Map> pageList, Map<String, Object> likeMap, String column, String order);
}
