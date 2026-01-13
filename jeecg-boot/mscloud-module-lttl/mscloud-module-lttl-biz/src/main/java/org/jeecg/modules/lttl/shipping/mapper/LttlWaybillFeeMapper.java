package org.jeecg.modules.lttl.shipping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybillFee;

import java.util.List;

/**
 * @Description: 运单费用表
 * @Author: jeecg-boot
 * @Date:   2025-12-29
 * @Version: V1.0
 */
public interface LttlWaybillFeeMapper extends BaseMapper<LttlWaybillFee> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<LttlWaybillFee>
   */
	public List<LttlWaybillFee> selectByMainId(@Param("mainId") String mainId);
}
