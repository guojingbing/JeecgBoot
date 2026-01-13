package org.jeecg.modules.lttl.shipping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybillGoods;

import java.util.List;

/**
 * @Description: 运单货物表
 * @Author: jeecg-boot
 * @Date:   2025-12-29
 * @Version: V1.0
 */
public interface ILttlWaybillGoodsService extends IService<LttlWaybillGoods> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<LttlWaybillGoods>
	 */
	public List<LttlWaybillGoods> selectByMainId(String mainId);
}
