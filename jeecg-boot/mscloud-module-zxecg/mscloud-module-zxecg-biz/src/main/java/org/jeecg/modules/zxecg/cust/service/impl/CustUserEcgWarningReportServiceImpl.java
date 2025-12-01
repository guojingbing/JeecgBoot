package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgWarningReport;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgWarningReportMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgWarningReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/27
 * @description
 */

@Service
public class CustUserEcgWarningReportServiceImpl extends ServiceImpl<CustUserEcgWarningReportMapper, CustUserEcgWarningReport> implements ICustUserEcgWarningReportService {
    @Override
    public Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order) {
        List<Map> list = this.baseMapper.loadListPaging(pageList, loginUserId, likeMap, column, order);
        //todo  进一步处理数据
        return pageList.setRecords(list);
    }
}
