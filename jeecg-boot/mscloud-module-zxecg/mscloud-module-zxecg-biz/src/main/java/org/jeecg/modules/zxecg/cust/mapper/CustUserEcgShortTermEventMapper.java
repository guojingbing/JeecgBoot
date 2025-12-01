package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgShortTermEvent;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 * @description
 */


public interface CustUserEcgShortTermEventMapper extends BaseMapper<CustUserEcgShortTermEvent> {
    void deleteEventsByEcgId(@Param("ecgId") Long ecgId);
}
