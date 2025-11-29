package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportStatistics;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/1
 */


public interface CustUserEcgReportStatisticsMapper extends BaseMapper<CustUserEcgReportStatistics> {
    void deleteByRepId(@Param("repId") Long repId);

    CustUserEcgReportStatistics getByRepId(@Param("repId") Long repId);
}
