package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportDetail;

import java.util.List;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/2
 * @description
 */


public interface CustUserEcgReportDetailMapper extends BaseMapper<CustUserEcgReportDetail> {
    List<Long> getRepIdsByEcgId(@Param("ecgId") Long ecgId);

    List<CustUserEcgReportDetail> getListByRepId(@Param("repId") Long repId);

    void deleteByEcgId(Long ecgId);

    void deleteByRepId(Long repId);
}
