package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.zxecg.cust.entity.CustUserMaOrder;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/8
 */


public interface ICustUserMaOrderService extends IService<CustUserMaOrder> {
    Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Integer audit, Integer orderType, Map<String, Object> likeMap, String column, String order);

    Result<?> auditOrder(Long orderId, Integer oper, String desc, Long loginUserId);
}
