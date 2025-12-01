package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.zxecg.cust.entity.CustUserAppExceptionLogs;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/27
 */


public interface CustUserAppExceptionLogsMapper extends BaseMapper<CustUserAppExceptionLogs> {
    List<Map> loadListPaging(Page<Map> pageList, Map<String, Object> likeMap, String column, String order);
}
