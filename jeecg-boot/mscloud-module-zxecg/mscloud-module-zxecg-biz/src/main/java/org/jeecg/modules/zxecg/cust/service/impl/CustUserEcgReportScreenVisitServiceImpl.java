package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportScreenVisit;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportScreenVisitMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportScreenVisitService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/3
 */

@Service
public class CustUserEcgReportScreenVisitServiceImpl extends ServiceImpl<CustUserEcgReportScreenVisitMapper, CustUserEcgReportScreenVisit> implements ICustUserEcgReportScreenVisitService {
    @Override
    public Page<Map> loadListPaging(Page<Map> pageList, Map<String, Object> likeMap, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadListPaging(pageList,likeMap,column,order));
    }
}
