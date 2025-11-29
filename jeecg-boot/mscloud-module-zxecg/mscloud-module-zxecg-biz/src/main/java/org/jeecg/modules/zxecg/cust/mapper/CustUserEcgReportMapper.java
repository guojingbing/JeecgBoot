package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReport;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/29
 */


public interface CustUserEcgReportMapper extends BaseMapper<CustUserEcgReport> {
    List<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order);

    List<Map> connectRepList(@Param("userId") Long userId, @Param("userName") String userName, @Param("repType") Integer repType);

    Map getRepDiagInfo(@Param("repId") Long repId);

    List<Map> loadMergeUserListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order);

    List<Map> getRepDateByUser(Long userId, String userName, String startMonth, String endMonth, Long loginUserId);
}
