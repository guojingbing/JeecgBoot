package org.jeecg.modules.cust.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 通用业务
 */
public interface MpCommonMapper extends BaseMapper<String> {
    /**
     * 通过dict_code查询数据字典子项
     * @param dictCode
     * @return
     */
    List<Map<String,Object>> selectDictItemsByDictCode(@Param("dictCode") String dictCode);
}
