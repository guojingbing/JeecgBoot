package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserWarningEvent;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/4
 */


public interface ICustUserWarningEventService extends IService<CustUserWarningEvent> {
    Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order);

    void markRead(String ids, Long loginUserId);
}
