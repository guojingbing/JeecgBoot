package org.jeecg.modules.zxecg.cust.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgShortTerm;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 * @description 快速筛查相关操作
 */


public interface ICustUserEcgShortTermService extends IService<CustUserEcgShortTerm> {
    Page<Map> loadListPaging(Page<Map> pageList, List<Long> deptIdList, Map<String, Object> likeMap, String column, String order);

    void resultEdit(CustUserEcgShortTerm shortTerm, JSONArray eventList);
}
