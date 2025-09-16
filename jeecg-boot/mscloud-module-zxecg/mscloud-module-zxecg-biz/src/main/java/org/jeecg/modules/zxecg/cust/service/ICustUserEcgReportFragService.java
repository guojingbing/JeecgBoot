package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportFrag;

import java.util.List;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/5
 */


public interface ICustUserEcgReportFragService extends IService<CustUserEcgReportFrag> {
    List<Integer> getCatIdByRepId(Long repId);

    /**
     * 报告留图片段翻转
     *
     * @param reportFrag
     */
    void fragReverse(CustUserEcgReportFrag reportFrag);

    /**
     * 片段排序
     *
     * @param repId
     * @param fragIds
     * @param loginUserId
     */
    void fragOrder(Long repId, String fragIds, Long loginUserId);

    /**
     * 按分钟自主留图
     *
     * @param repId
     * @param ecgId
     * @param fragStartTime
     * @param title
     * @param loginUserId
     * @return
     */
    Result<?> minToFrag(Long repId, Long ecgId, Long fragStartTime, String title, Long loginUserId);

    /**
     * 用户事件填写结论
     * @param repId
     * @param fragId
     * @param fragDesc
     * @param loginUserId
     * @return
     */
    Result<?> userEventSummary(Long repId, Long fragId, String fragDesc, Long loginUserId);
}
