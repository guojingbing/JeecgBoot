package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportCheckOrder;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/1
 */


public interface CustUserEcgReportCheckOrderMapper extends BaseMapper<CustUserEcgReportCheckOrder> {
    List<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Integer orderType, Map<String, Object> likeMap, String column, String order);

    List<CustUserEcgReportCheckOrder> getListByRepId(@Param("repId") Long repId);
}
