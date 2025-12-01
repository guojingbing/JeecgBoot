package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportFragStyle;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/11
 */


public interface CustUserEcgReportFragStyleMapper extends BaseMapper<CustUserEcgReportFragStyle> {
    CustUserEcgReportFragStyle getByRepIdAndCatId(@Param("repId") Long repId, @Param("catId") Integer categoryId);
}
