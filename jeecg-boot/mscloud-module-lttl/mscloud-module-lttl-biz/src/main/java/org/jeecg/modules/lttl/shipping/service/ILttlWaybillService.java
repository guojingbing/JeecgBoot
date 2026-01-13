package org.jeecg.modules.lttl.shipping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybill;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybillFee;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybillGoods;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 运单主表
 * @Author: jeecg-boot
 * @Date:   2025-12-29
 * @Version: V1.0
 */
public interface ILttlWaybillService extends IService<LttlWaybill> {

	/**
	 * 添加一对多
	 *
	 * @param lttlWaybill
	 * @param lttlWaybillGoodsList
	 * @param lttlWaybillFeeList
	 */
	public void saveMain(LttlWaybill lttlWaybill, List<LttlWaybillGoods> lttlWaybillGoodsList, List<LttlWaybillFee> lttlWaybillFeeList) ;
	
	/**
	 * 修改一对多
	 *
	 * @param lttlWaybill
	 * @param lttlWaybillGoodsList
	 * @param lttlWaybillFeeList
	 */
	public void updateMain(LttlWaybill lttlWaybill,List<LttlWaybillGoods> lttlWaybillGoodsList,List<LttlWaybillFee> lttlWaybillFeeList);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
