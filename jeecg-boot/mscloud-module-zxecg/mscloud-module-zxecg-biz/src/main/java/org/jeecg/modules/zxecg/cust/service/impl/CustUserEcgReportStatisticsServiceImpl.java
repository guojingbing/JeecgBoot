package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportStatistics;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportStatisticsMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportStatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/1
 */

@Service
public class CustUserEcgReportStatisticsServiceImpl extends ServiceImpl<CustUserEcgReportStatisticsMapper, CustUserEcgReportStatistics> implements ICustUserEcgReportStatisticsService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRepId(Long repId) {
        this.baseMapper.deleteByRepId(repId);
    }

    @Override
    public CustUserEcgReportStatistics getByRepId(Long repId) {
        return this.baseMapper.getByRepId(repId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAdvice(Long repId, String conclusion, Long loginUserId) {
        CustUserEcgReportStatistics statistics = this.getByRepId(repId);
        statistics.setDoctorConclusion(conclusion);
        statistics.setDoctorUserId(loginUserId);
        statistics.setConfirmTime(new Timestamp(System.currentTimeMillis()));
        this.updateById(statistics);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustUserEcgReportStatistics saveAnaConclusion(Long repId, String templ, String content, Integer wrap, Long loginUserId) {
        CustUserEcgReportStatistics st = this.getByRepId(repId);
        st.setConclusionWrap(wrap);
        st.setLastModifyUserId(loginUserId);
        st.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        st.setLastAnaConclusionTime(new Timestamp(System.currentTimeMillis()));
        if (StringUtils.isNotBlank(content)) {
            //用户修改后结论
            st.setCustAnaConclusion(1);
            st.setAnaConclusion(content);
            //结论模板置空
            st.setAnaConclusionTemplate(null);
        } else if (StringUtils.isNotEmpty(templ)) {
            //将teml进行排序，使其按照顺序排序
            String[] items = templ.split(",");
            List<String> stringList = Arrays.stream(items).sorted().collect(Collectors.toList());
            StringBuffer anaConBuffer = new StringBuffer();
            //todo   genConclusion 后续再完成
//            for (int i = 0; i < stringList.size(); i++) {
//                anaConBuffer.append(genConclusion(st, stringList.get(i)));
//            }
            content = anaConBuffer.toString();
            st.setAnaConclusion(content);
            st.setAnaConclusionTemplate(StringUtils.join(stringList, ","));
            st.setCustAnaConclusion(0);
        } else {//同时为空则意味清除结论
            st.setAnaConclusion(null);
            st.setAnaConclusionTemplate(null);
            st.setCustAnaConclusion(1);
        }
        //保存结论信息
        this.updateById(st);
        return st;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void itemPrint(Long repId, String item, boolean printId) {
        CustUserEcgReportStatistics statistics = this.getByRepId(repId);
        String excludeItems = statistics.getExcludeItems();
        String reportItems = statistics.getReportItems();
        List<String> excludeItemList = new ArrayList<>();
        List<String> reportItemList = new ArrayList<>();
        if (StringUtils.isNotEmpty(excludeItems)) {
            excludeItemList = new ArrayList<>(Arrays.asList(excludeItems.split(",")));
        }
        if (StringUtils.isNotEmpty(reportItems)) {
            reportItemList = new ArrayList<>(Arrays.asList(reportItems.split(",")));
        }
        //项目打印
        if (printId) {
            if (excludeItemList.contains(item)) {
                excludeItemList.remove(item);
            }
            if (!reportItemList.contains(item)) {
                reportItemList.add(item);
            }
        } else {
            //项目取消打印
            if (!excludeItemList.contains(item)) {
                excludeItemList.add(item);
            }
            if (reportItemList.contains(item)) {
                reportItemList.remove(item);
            }
        }
        if (excludeItemList.size() > 0) {
            statistics.setExcludeItems(StringUtils.join(excludeItemList, ","));
        } else
            statistics.setExcludeItems(null);

        if (reportItemList.size() > 0) {
            statistics.setReportItems(StringUtils.join(reportItemList, ","));
        } else {
            statistics.setReportItems(null);
        }
        this.updateById(statistics);
    }
}
