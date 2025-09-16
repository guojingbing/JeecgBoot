package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgWarningReportDetail;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/27
 * @description
 */


public interface CustUserEcgWarningReportDetailMapper extends BaseMapper<CustUserEcgWarningReportDetail> {
    void deleteByEcgId(@Param("ecgId") Long ecgId);
}
