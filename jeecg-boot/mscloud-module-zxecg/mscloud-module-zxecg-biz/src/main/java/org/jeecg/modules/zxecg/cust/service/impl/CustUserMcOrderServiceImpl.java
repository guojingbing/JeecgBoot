package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.zxecg.cust.entity.CustUserMcOrder;
import org.jeecg.modules.zxecg.cust.entity.CustUserMcOrderDispatch;
import org.jeecg.modules.zxecg.cust.enums.MaDispOrderStatusEnum;
import org.jeecg.modules.zxecg.cust.enums.McOrderStatusEnum;
import org.jeecg.modules.zxecg.cust.mapper.CustUserMcOrderMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserMcOrderDispatchService;
import org.jeecg.modules.zxecg.cust.service.ICustUserMcOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/9
 */

@Service
public class CustUserMcOrderServiceImpl extends ServiceImpl<CustUserMcOrderMapper, CustUserMcOrder> implements ICustUserMcOrderService {
    @Resource
    ICustUserMcOrderDispatchService mcOrderDispatchService;

    @Override
    public Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Long userId, Map<String, Object> likeMap, String column, String order) {
        List<Map> list = this.baseMapper.loadListPaging(pageList, loginUserId, userId, likeMap, column, order);
        //todo  进一步处理数据
        return pageList.setRecords(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> interpretRep(Long dispId, String conclusion) {
        CustUserMcOrderDispatch orderDispatch = mcOrderDispatchService.getById(dispId);
        if (null == orderDispatch) {
            return Result.error("咨询单不存在");
        }
        orderDispatch.setMcConclusion(conclusion);
        orderDispatch.setFillinTime(new Timestamp(System.currentTimeMillis()));
        orderDispatch.setDispStatus(String.valueOf(MaDispOrderStatusEnum.FINISHED.getCode()));
        mcOrderDispatchService.updateById(orderDispatch);
        CustUserMcOrder mcOrder = this.getById(orderDispatch.getOrderId());
        if (null == mcOrder) {
            return null;
        }
        mcOrder.setOrderConclusion(conclusion);
        mcOrder.setFillinTime(new Timestamp(System.currentTimeMillis()));
        mcOrder.setOrderStatus(String.valueOf(McOrderStatusEnum.FINISHED.getCode()));
        this.updateById(mcOrder);
        return null;
    }
}
