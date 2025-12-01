package org.jeecg.modules.zxecg.system.service;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/8
 */


public interface ICommCompanyPrescriberService {
    List<Map<String, Object>> getCompanyPrescriber(Long companyId, Long deptId);
}
