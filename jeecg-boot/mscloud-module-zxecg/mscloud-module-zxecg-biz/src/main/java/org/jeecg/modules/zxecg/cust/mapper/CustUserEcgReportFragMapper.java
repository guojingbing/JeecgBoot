package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportFrag;

import java.util.List;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/5
 */


public interface CustUserEcgReportFragMapper extends BaseMapper<CustUserEcgReportFrag> {
    List<Integer> getCatIdByRepId(@Param("repId") Long repId);

    void updateReversed(@Param("reversed") int reversed, @Param("repId") Long repId, @Param("fragId") Long fragId,
                        @Param("subCategoryId") int subCategoryId, @Param("fragCenterTime") Long fragCenterTime);

    CustUserEcgReportFrag getFragByRepIdAndCatId(@Param("repId") Long repId, @Param("ecgId") Long ecgId,
                                                 @Param("fragStartTime") Long fragStartTime, @Param("categoryId") int categoryId);

    CustUserEcgReportFrag getByRepIdAndFragId(@Param("repId") Long repId, @Param("fragId") Long fragId);
}
