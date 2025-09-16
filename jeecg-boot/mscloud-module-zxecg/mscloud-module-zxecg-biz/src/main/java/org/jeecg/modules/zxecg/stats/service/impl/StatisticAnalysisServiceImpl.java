package org.jeecg.modules.zxecg.stats.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.stats.mapper.StatisticAnalysisMapper;
import org.jeecg.modules.zxecg.stats.service.IStatisticAnalysisService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/28
 * @description 统计分析
 */

@Service
public class StatisticAnalysisServiceImpl extends ServiceImpl<StatisticAnalysisMapper, Map> implements IStatisticAnalysisService {
    @Override
    public Page<Map> loadCompanyDataListPaging(Page<Map> pageList, String sdate, String edate, Map<String, Object> likeMap, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadCompanyDataListPaging(pageList, sdate, edate, likeMap, column, order));
    }

    @Override
    public List<Map> loadUserProvDataListPaging() {
        return this.baseMapper.loadUserProvDataListPaging();
    }

    @Override
    public Page<Map> loadMachDataListPaging(Page<Map> pageList, String startDate, String endDate, Map<String, Object> likeMap, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadMachDataListPaging(pageList, startDate, endDate, likeMap, column, order));
    }

    @Override
    public Page<Map> loadPdfListPaging(Page<Map> pageList, String startDate, String endDate, Integer repType, Long companyId, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadPdfListPaging(pageList, startDate, endDate, repType, companyId, column, order));
    }

    @Override
    public Page<Map> loadRepListPaging(Page<Map> pageList, String startDate, String endDate, Map<String, Object> likeMap, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadRepListPaging(pageList, startDate, endDate, likeMap, column, order));
    }

    @Override
    public Page<Map> loadPacemakerListPaging(Page<Map> pageList, Long loginUserId, String paramName, Integer rrNums, Map<String, Object> likeMap, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadPacemakerListPaging(pageList, loginUserId, paramName, rrNums, likeMap, column, order));
    }
}
