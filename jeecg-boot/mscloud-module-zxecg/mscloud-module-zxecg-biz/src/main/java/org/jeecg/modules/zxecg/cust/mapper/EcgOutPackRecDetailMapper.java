package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.cust.entity.EcgOutPackRecDetail;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/27
 * @description
 */


public interface EcgOutPackRecDetailMapper extends BaseMapper<EcgOutPackRecDetail> {
    List<Map<String, Object>> getListByPackId(@Param("packId") Long packId);
}
