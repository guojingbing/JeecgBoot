package org.jeecg.modules.zxecg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.system.entity.CommCompany;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/15
 * @description
 */


public interface CommCompanyMapper extends BaseMapper<CommCompany> {
    List<Map<String, Object>> getListByUserId(@Param("userId") long loginUserId);
}
