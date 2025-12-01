package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReport;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportStatistics;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/29
 */


public interface ICustUserEcgReportService extends IService<CustUserEcgReport> {
    Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order);

    void changeDept(Long repId, Long deptId);

    List<Map> connectRepList(Long userId, Integer repType);

    Map repState(Long repId);

    Map getRepDiagInfo(Long repId);

    void diagUpdate(Long repId, String content, String inDesc);

    Map<String, Object> getRepItems(Long repId);


    void editReportUserInfo(Long repId, String userName, Date birthDate, Integer userGender, Long loginUserId);

    Page<Map> loadMergeUserListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order);

    List<Map> repDate(Long userId, String userName, String startMonth, String endMonth, Long loginUserId);

    CustUserEcgReportStatistics getRepStatistics(Long repId);
}
