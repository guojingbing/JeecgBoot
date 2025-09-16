package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportDeepAna;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportDeepAnaMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportDeepAnaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 * @description
 */

@Service
public class CustUserEcgReportDeepAnaServiceImpl extends ServiceImpl<CustUserEcgReportDeepAnaMapper, CustUserEcgReportDeepAna> implements ICustUserEcgReportDeepAnaService {
    @Override
    public Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadListPaging(pageList, loginUserId, likeMap, column, order));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reportFinish(List<Long> repIdList) {
        List<CustUserEcgReportDeepAna> list = this.baseMapper.getListByRepId(repIdList);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Collection uList = new ArrayList();
        for (CustUserEcgReportDeepAna custUserEcgReportDeepAna : list) {
            custUserEcgReportDeepAna.setDealStatus(2);
            custUserEcgReportDeepAna.setDealStatusTime(new Timestamp(System.currentTimeMillis()));
            uList.add(custUserEcgReportDeepAna);
        }
        this.updateBatchById(uList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDeepAna(Long repId) {
        List<CustUserEcgReportDeepAna> list = this.baseMapper.getListByRepId(Arrays.asList(repId));
        CustUserEcgReportDeepAna deepAna = null;
        if (CollectionUtils.isEmpty(list)) {
            //新增
            deepAna = new CustUserEcgReportDeepAna();
            deepAna.setRepId(repId);
            deepAna.setCreateTime(new Timestamp(System.currentTimeMillis()));
            deepAna.setRepReadStatus(2);
            deepAna.setSrc(2);
        } else {
            deepAna = list.get(0);
            //相同报告系统预置请求修改为人工请求，同时处理状态修改为处理中
            if (deepAna.getSrc() == 1) {
                deepAna.setSrc(2);
                if (null != deepAna.getDealStatus() && deepAna.getDealStatus() == 2) {
                    deepAna.setDealStatus(1);
                }
            }
        }
        this.saveOrUpdate(deepAna);
    }
}
