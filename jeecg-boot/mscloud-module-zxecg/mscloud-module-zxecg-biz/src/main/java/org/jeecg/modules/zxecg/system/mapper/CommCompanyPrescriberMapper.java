package org.jeecg.modules.zxecg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.system.entity.CommCompanyPrescriber;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/8
 */


public interface CommCompanyPrescriberMapper extends BaseMapper<CommCompanyPrescriber> {
    List<Map<String, Object>> getCompanyPrescriber(@Param("companyId") Long companyId, @Param("deptId") Long deptId);
}
