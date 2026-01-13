package org.jeecg.modules.lttl.shipping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybillFee;

import java.util.List;

/**
 * @Description: 运单费用表
 * @Author: jeecg-boot
 * @Date:   2025-12-29
 * @Version: V1.0
 */
public interface ILttlWaybillFeeService extends IService<LttlWaybillFee> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<LttlWaybillFee>
	 */
	public List<LttlWaybillFee> selectByMainId(String mainId);
}
