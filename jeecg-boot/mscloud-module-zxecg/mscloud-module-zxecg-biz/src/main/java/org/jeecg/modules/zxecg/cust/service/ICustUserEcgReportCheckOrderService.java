package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportCheckOrder;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/1
 * @description 遥测报告相关操作
 */


public interface ICustUserEcgReportCheckOrderService extends IService<CustUserEcgReportCheckOrder> {
    Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Integer orderType, Map<String, Object> likeMap, String column, String order);

    void clearHolterRepId(Long repId, Long loginUserId);
}
