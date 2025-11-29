package org.jeecg.modules.zxecg.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25 运维预警
 */


public interface ICommSystemBussiEventWarningService {
    Page<Map> loadListPaging(Page<Map> pageList, Map<String, Object> likeMap, String column, String order);

    void dealWarning(String ids, Integer dealResult, Long loginUserId);

}
