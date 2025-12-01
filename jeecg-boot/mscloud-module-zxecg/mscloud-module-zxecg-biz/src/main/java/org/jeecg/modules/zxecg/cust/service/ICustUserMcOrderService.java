package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.zxecg.cust.entity.CustUserMcOrder;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/9
 */


public interface ICustUserMcOrderService extends IService<CustUserMcOrder> {
    Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Long userId, Map<String, Object> likeMap, String column, String order);

    Result<?> interpretRep(Long dispId, String conclusion);
}
