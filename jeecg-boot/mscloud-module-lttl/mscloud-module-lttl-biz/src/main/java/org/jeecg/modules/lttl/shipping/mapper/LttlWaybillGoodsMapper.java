package org.jeecg.modules.lttl.shipping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybillGoods;

import java.util.List;

/**
 * @Description: 运单货物表
 * @Author: jeecg-boot
 * @Date:   2025-12-29
 * @Version: V1.0
 */
public interface LttlWaybillGoodsMapper extends BaseMapper<LttlWaybillGoods> {

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
   * @return List<LttlWaybillGoods>
   */
	public List<LttlWaybillGoods> selectByMainId(@Param("mainId") String mainId);
}
