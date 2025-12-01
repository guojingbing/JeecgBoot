package org.jeecg.modules.zxecg.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.system.entity.CommCompanyMach;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/26
 * @description 设备管理
 */


public interface ICommCompanyMachService extends IService<CommCompanyMach> {
    Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order);

    void updateStatus(CommCompanyMach companyMach);
}
