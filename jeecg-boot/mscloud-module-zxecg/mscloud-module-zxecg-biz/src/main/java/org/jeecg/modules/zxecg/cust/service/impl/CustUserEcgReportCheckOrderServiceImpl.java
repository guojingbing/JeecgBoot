package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportCheckOrder;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportCheckOrderMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportCheckOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/1
 * @description
 */

@Service
public class CustUserEcgReportCheckOrderServiceImpl extends ServiceImpl<CustUserEcgReportCheckOrderMapper, CustUserEcgReportCheckOrder> implements ICustUserEcgReportCheckOrderService {
    @Override
    public Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Integer orderType, Map<String, Object> likeMap, String column, String order) {
        List<Map> list = this.baseMapper.loadListPaging(pageList, loginUserId, orderType, likeMap, column, order);
        //todo  进一步处理数据
        return pageList.setRecords(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearHolterRepId(Long repId, Long loginUserId) {
        List<CustUserEcgReportCheckOrder> checkOrders = this.baseMapper.getListByRepId(repId);
        if (CollectionUtils.isNotEmpty(checkOrders)) {
            for (CustUserEcgReportCheckOrder checkOrder : checkOrders) {
                checkOrder.setHolterRepId(null);
                checkOrder.setHolterRepStatus(10);
                checkOrder.setHolterRepOperUser(loginUserId);
                checkOrder.setHolterRepOperTime(new Timestamp(System.currentTimeMillis()));
                this.updateById(checkOrder);
            }
        }
    }
}
