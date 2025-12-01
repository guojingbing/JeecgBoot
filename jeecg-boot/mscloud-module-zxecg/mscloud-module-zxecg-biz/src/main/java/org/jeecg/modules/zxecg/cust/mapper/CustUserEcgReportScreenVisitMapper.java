package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportScreenVisit;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/3
 */


public interface CustUserEcgReportScreenVisitMapper extends BaseMapper<CustUserEcgReportScreenVisit> {
    List<Map> loadListPaging(Page<Map> pageList, Map<String, Object> likeMap, String column, String order);
}
