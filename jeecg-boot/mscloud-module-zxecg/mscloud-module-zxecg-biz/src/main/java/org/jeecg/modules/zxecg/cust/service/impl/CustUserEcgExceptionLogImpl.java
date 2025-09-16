package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgExceptionLog;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgExceptionLogMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgExceptionLogService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 * @description
 */

@Service
public class CustUserEcgExceptionLogImpl extends ServiceImpl<CustUserEcgExceptionLogMapper, CustUserEcgExceptionLog> implements ICustUserEcgExceptionLogService {
    @Override
    public Page<Map> loadListPaging(Page<Map> pageList, Map<String, Object> likeMap, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadListPaging(pageList, likeMap, column, order));
    }
}
