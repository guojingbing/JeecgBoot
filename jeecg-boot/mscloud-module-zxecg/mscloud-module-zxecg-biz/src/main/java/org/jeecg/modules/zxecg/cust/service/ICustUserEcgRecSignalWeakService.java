package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgRecSignalWeak;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/27
 */


public interface ICustUserEcgRecSignalWeakService extends IService<CustUserEcgRecSignalWeak> {
    Page<Map> loadListPaging(Page<Map> pageList, Map<String, Object> likeMap, String column, String order);
}
