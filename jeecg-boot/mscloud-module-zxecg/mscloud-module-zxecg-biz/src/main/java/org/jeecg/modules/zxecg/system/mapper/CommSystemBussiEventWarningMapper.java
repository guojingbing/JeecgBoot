package org.jeecg.modules.zxecg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.zxecg.system.entity.CommSystemBussiEventWarning;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 * @description 运维预警
 */


public interface CommSystemBussiEventWarningMapper extends BaseMapper<CommSystemBussiEventWarning> {
    List<Map> loadListPaging(Page<Map> page, Map<String, Object> likeMap, String column, String order);
}
