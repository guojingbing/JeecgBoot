package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/2
 */


public interface ICustUserEcgReportScreenEventService extends IService<Map> {
    Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order);

    Page<Map> loadNoteListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order);
}
