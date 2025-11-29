package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportFavorite;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/29
 */


public interface CustUserEcgReportFavoriteMapper extends BaseMapper<CustUserEcgReportFavorite> {
    CustUserEcgReportFavorite getByRepIdAndUserId(@Param("repId") Long repId, @Param("userId") Long loginUserId);

    void deleteByRepId(@Param("repId") Long repId);
}
