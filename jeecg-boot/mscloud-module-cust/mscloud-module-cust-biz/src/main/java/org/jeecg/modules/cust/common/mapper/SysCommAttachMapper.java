package org.jeecg.modules.cust.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cust.common.entity.SysCommAttach;

import java.util.List;
import java.util.Map;

/**
 * 通用附件业务表
 */
public interface SysCommAttachMapper extends BaseMapper<SysCommAttach> {
    List<Map<String,Object>> selectAreas(@Param("level") Integer level, @Param("province") String province, @Param("city") String city, @Param("district") String district);

    /**
     * 通过dict_code查询数据字典子项
     * @param dictCode
     * @return
     */
    List<Map<String,Object>> selectDictItemsByDictCode(@Param("dictCode") String dictCode);
}
