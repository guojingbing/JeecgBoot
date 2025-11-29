package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.EcgOutPackRec;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/27
 * @description ECG批量下载表
 */


public interface IEcgOutPackRecService extends IService<EcgOutPackRec> {
    Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order);
}
