package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportResultChange;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportResultChangeMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportResultChangeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/10
 * @description
 */

@Service
public class CustUserEcgReportResultChangeServiceImpl extends ServiceImpl<CustUserEcgReportResultChangeMapper, CustUserEcgReportResultChange> implements ICustUserEcgReportResultChangeService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void repResultChange(Long repId, Integer typeId, Integer operId, List<Long> ecgIds, List<Long> abnTimes, Long loginUserId) {
        if (CollectionUtils.isEmpty(ecgIds)) {
            return;
        }
        Collection list = new ArrayList();
        for (int i = 0; i < ecgIds.size(); i++) {
            CustUserEcgReportResultChange change = new CustUserEcgReportResultChange();
            change.setTypeId(typeId);
            change.setAbnTime(abnTimes.get(i));
            change.setEcgId(ecgIds.get(i));
            change.setRepId(repId);
            change.setOperId(operId);
            change.setCreateTime(new Timestamp(System.currentTimeMillis()));
            change.setCreateUserId(loginUserId);
            list.add(change);
        }
        this.saveBatch(list);
    }
}
