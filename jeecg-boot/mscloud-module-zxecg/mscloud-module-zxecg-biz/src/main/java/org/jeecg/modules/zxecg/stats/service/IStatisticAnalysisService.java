package org.jeecg.modules.zxecg.stats.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/28
 * @description 统计分析
 */


public interface IStatisticAnalysisService extends IService<Map> {
    Page<Map> loadCompanyDataListPaging(Page<Map> pageList, String sdate, String edate, Map<String, Object> likeMap, String column, String order);

    List<Map> loadUserProvDataListPaging();

    Page<Map> loadMachDataListPaging(Page<Map> pageList, String startDate, String endDate, Map<String, Object> likeMap, String column, String order);

    Page<Map> loadPdfListPaging(Page<Map> pageList, String startDate, String endDate, Integer repType, Long companyId, String column, String order);

    Page<Map> loadRepListPaging(Page<Map> pageList, String startDate, String endDate, Map<String, Object> likeMap, String column, String order);

    Page<Map> loadPacemakerListPaging(Page<Map> pageList, Long loginUserId, String paramName, Integer rrNums, Map<String, Object> likeMap, String column, String order);
}
