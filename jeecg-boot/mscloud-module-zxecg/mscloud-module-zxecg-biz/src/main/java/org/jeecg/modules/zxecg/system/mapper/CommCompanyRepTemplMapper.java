package org.jeecg.modules.zxecg.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.system.entity.CommCompanyRepTempl;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/8
 */


public interface CommCompanyRepTemplMapper extends BaseMapper<CommCompanyRepTempl> {
    CommCompanyRepTempl getRepTemplByCompanyId(@Param("companyId") Long companyId);
}
