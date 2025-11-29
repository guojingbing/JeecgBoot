package org.jeecg.modules.zxecg.stats.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/28
 * @description 统计分析
 */


public interface StatisticAnalysisMapper extends BaseMapper<Map> {
    List<Map> loadCompanyDataListPaging(Page<Map> pageList, String sdate, String edate, Map<String, Object> likeMap, String column, String order);

    List<Map> loadUserProvDataListPaging();

    List<Map> loadMachDataListPaging(Page<Map> pageList, String startDate, String endDate, Map<String, Object> likeMap, String column, String order);

    List<Map> loadPdfListPaging(Page<Map> pageList, String startDate, String endDate, Integer repType, Long companyId, String column, String order);

    List<Map> loadRepListPaging(Page<Map> pageList, String startDate, String endDate, Map<String, Object> likeMap, String column, String order);

    List<Map> loadPacemakerListPaging(Page<Map> pageList, Long loginUserId, String paramName, Integer rrNums, Map<String, Object> likeMap, String column, String order);
}
