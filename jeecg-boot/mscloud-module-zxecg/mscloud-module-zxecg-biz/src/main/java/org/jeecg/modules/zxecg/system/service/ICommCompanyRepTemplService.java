package org.jeecg.modules.zxecg.system.service;

import org.jeecg.modules.zxecg.system.entity.CommCompanyRepTempl;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/8
 */


public interface ICommCompanyRepTemplService {
    CommCompanyRepTempl getRepTemplByCompanyId(Long companyId);
}
