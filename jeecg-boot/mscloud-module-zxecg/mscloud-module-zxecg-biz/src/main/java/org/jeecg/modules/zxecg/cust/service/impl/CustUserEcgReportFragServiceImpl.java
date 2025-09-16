package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.zxecg.cust.constant.ReportConst;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportFrag;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportFragMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportFragService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/5
 */

@Service
public class CustUserEcgReportFragServiceImpl extends ServiceImpl<CustUserEcgReportFragMapper, CustUserEcgReportFrag> implements ICustUserEcgReportFragService {

    @Override
    public List<Integer> getCatIdByRepId(Long repId) {
        return this.baseMapper.getCatIdByRepId(repId);
    }

    /**
     * 报告留图片段翻转
     *
     * @param reportFrag
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void fragReverse(CustUserEcgReportFrag reportFrag) {
        int reversed = reportFrag.getIsReversed() == 1 ? 0 : 1;
        reportFrag.setIsReversed(reversed);
        this.updateById(reportFrag);
        //更新重点条图里面的片段
        int subCategoryId = 0;
        switch (reportFrag.getCategoryId()) {
            case ReportConst.REPORT_ITEM_VT:
                subCategoryId = 1;
                break;
            case ReportConst.REPORT_ITEM_SVT:
                subCategoryId = 2;
                break;
            case ReportConst.REPORT_ITEM_AF:
                subCategoryId = 3;
                break;
            case ReportConst.REPORT_ITEM_PAUSE:
                subCategoryId = 4;
                break;
            case ReportConst.REPORT_ITEM_BLOCK:
                subCategoryId = 5;
                break;
        }
        if (subCategoryId > 0) {
            this.baseMapper.updateReversed(reversed, reportFrag.getRepId(), reportFrag.getFragId(), subCategoryId, reportFrag.getFragCenterTime());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void fragOrder(Long repId, String fragIds, Long loginUserId) {
        int orderNo = 0;
        String[] fragIdArr = fragIds.split(",");
        Collection frags = new ArrayList();
        for (String id : fragIdArr) {
            orderNo = orderNo + 1;
            CustUserEcgReportFrag frag = this.getById(Long.parseLong(id));
            if (frag != null) {
                frag.setOrderNo(orderNo);
                frag.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
                frag.setLastModifyUserId(loginUserId);
                frags.add(frag);
            }
        }
        this.saveOrUpdateBatch(frags);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> minToFrag(Long repId, Long ecgId, Long fragStartTime, String title, Long loginUserId) {
        CustUserEcgReportFrag reportFrag = this.baseMapper.getFragByRepIdAndCatId(repId, ecgId, fragStartTime, ReportConst.REPORT_ITEM_ONE_MIN);
        if (null != reportFrag) {
            return Result.error("已留图");
        }
        reportFrag = new CustUserEcgReportFrag();
        reportFrag.setRepId(repId);
        reportFrag.setEcgId(ecgId);
        reportFrag.setCategoryId(ReportConst.REPORT_ITEM_ONE_MIN);
        reportFrag.setSubCategoryId(0);
        reportFrag.setFragStartTime(fragStartTime);
        reportFrag.setFragCenterTime(fragStartTime + 32 * 1000);
        reportFrag.setFragEndTime(fragStartTime + 64 * 1000);
        reportFrag.setFragTitle(title);
        reportFrag.setCreateUserId(loginUserId);
        reportFrag.setLastModifyUserId(loginUserId);
        reportFrag.setCreateTime(new Timestamp(System.currentTimeMillis()));
        reportFrag.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        reportFrag.setManulId(1);
        reportFrag.setIsReversed(0);
        this.save(reportFrag);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<?> userEventSummary(Long repId, Long fragId, String fragDesc, Long loginUserId) {
        CustUserEcgReportFrag reportFrag = this.baseMapper.getByRepIdAndFragId(repId, fragId);
        if (null == reportFrag) {
            return Result.error("片段不存在");
        }
        reportFrag.setFragDesc(fragDesc);
        reportFrag.setLastModifyUserId(loginUserId);
        reportFrag.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        this.updateById(reportFrag);
        return null;
    }
}
