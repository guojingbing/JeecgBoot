package org.jeecg.modules.cust.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cust.common.entity.SysCommAttachItem;

import java.util.List;
import java.util.Map;

/**
 * 通用附件子表处理mapper
 */
public interface SysCommAttachItemMapper extends BaseMapper<SysCommAttachItem> {
    List<Map> getAttachItemsByBussiDataId(@Param("bussi") String bussi, @Param("bussiDataId") String bussiDataId);
}
