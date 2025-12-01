package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.zxecg.cust.entity.CustUserMcOrder;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/9
 */


public interface CustUserMcOrderMapper extends BaseMapper<CustUserMcOrder> {
    List<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Long userId, Map<String, Object> likeMap, String column, String order);
}
