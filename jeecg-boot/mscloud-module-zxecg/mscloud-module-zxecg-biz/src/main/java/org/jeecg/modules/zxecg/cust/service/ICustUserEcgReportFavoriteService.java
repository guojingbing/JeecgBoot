package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportFavorite;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/29
 */


public interface ICustUserEcgReportFavoriteService extends IService<CustUserEcgReportFavorite> {
    void addFav(Long repId, Long loginUserId);

    void deleteByRepId(Long repId);
}
