package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportOrder;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/5
 */


public interface CustUserEcgReportOrderMapper extends BaseMapper<CustUserEcgReportOrder> {
    CustUserEcgReportOrder getByRepId(@Param("repId") Long repId);
}
