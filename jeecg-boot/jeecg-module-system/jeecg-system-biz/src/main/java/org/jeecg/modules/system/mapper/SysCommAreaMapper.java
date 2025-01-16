package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.system.entity.SysCommArea;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 区域 Mapper 接口
 * <p>
 * 
 * @Author: Steve
 * @Since：   2019-01-22
 */
public interface SysCommAreaMapper extends BaseMapper<SysCommArea> {
	/**
	 * 查询行政区域代码信息
     * @param level 查询深度
	 * @param tenantId 租户编号
	 * @param onlyAuthorized 是否仅查询授权给租户的区域
     * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getAreaTree(@Param("level") Integer level, @Param("tenantId") String tenantId, @Param("onlyAuthorized") Boolean onlyAuthorized);
}
