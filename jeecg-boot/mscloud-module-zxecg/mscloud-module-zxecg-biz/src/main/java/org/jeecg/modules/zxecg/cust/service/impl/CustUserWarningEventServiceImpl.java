package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserWarningEvent;
import org.jeecg.modules.zxecg.cust.mapper.CustUserWarningEventMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserWarningEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/4
 */

@Service
public class CustUserWarningEventServiceImpl extends ServiceImpl<CustUserWarningEventMapper, CustUserWarningEvent> implements ICustUserWarningEventService {
    @Override
    public Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadListPaging(pageList, loginUserId, likeMap, column, order));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markRead(String ids, Long loginUserId) {
        List<String> sArr = Arrays.asList(ids.split(","));
        List<Long> idList = sArr.stream().map(Long::new).collect(Collectors.toList());
        for (Long id : idList) {
            CustUserWarningEvent warningEvent = this.getById(id);
            if (null == warningEvent) {
                continue;
            }
            warningEvent.setConfirmStatus(1);
            warningEvent.setConfirmTime(new Timestamp(System.currentTimeMillis()));
            warningEvent.setConfirmUserId(loginUserId);
            this.updateById(warningEvent);
        }
    }
}
