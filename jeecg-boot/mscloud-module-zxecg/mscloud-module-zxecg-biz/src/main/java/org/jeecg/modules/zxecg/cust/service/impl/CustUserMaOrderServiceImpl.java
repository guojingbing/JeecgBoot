package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.zxecg.cust.entity.CustUserMaOrder;
import org.jeecg.modules.zxecg.cust.entity.CustUserMaOrderAuditLog;
import org.jeecg.modules.zxecg.cust.mapper.CustUserMaOrderMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserMaOrderAuditLogService;
import org.jeecg.modules.zxecg.cust.service.ICustUserMaOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/8
 */

@Service
public class CustUserMaOrderServiceImpl extends ServiceImpl<CustUserMaOrderMapper, CustUserMaOrder> implements ICustUserMaOrderService {
    @Resource
    ICustUserMaOrderAuditLogService auditLogService;

    @Override
    public Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Integer audit, Integer orderType, Map<String, Object> likeMap, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadListPaging(pageList, loginUserId, audit, orderType, likeMap, column, order));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> auditOrder(Long orderId, Integer oper, String desc, Long loginUserId) {
        CustUserMaOrder maOrder = this.getById(orderId);
        if (null == maOrder) {
            return Result.error("订单不存在");
        }
        maOrder.setAuditStatus(oper);
        maOrder.setAuditUserId(loginUserId);
        maOrder.setAuditDesc(desc);
        maOrder.setAuditTime(new Timestamp(System.currentTimeMillis()));
        this.updateById(maOrder);
        //处理写入审核记录
        CustUserMaOrderAuditLog log = new CustUserMaOrderAuditLog();
        log.setOrderId(orderId);
        log.setOper(oper);
        log.setOperDesc(desc);
        log.setOperUserId(loginUserId);
        log.setOperTime(maOrder.getAuditTime());
        auditLogService.save(log);
        //todo 处理报告签名信息
        return null;
    }
}
