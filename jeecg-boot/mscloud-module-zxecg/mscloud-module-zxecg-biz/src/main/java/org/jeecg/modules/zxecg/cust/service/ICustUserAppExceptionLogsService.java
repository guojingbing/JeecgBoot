package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserAppExceptionLogs;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 * @description 采样率异常相关操作
 */


public interface ICustUserAppExceptionLogsService extends IService<CustUserAppExceptionLogs> {
    Page<Map> loadListPaging(Page<Map> pageList, Map<String, Object> likeMap, String column, String order);
}
