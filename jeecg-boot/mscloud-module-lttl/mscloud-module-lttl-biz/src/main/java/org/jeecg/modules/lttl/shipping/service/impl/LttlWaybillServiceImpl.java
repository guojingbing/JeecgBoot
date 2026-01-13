package org.jeecg.modules.lttl.shipping.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybill;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybillFee;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybillGoods;
import org.jeecg.modules.lttl.shipping.mapper.LttlWaybillFeeMapper;
import org.jeecg.modules.lttl.shipping.mapper.LttlWaybillGoodsMapper;
import org.jeecg.modules.lttl.shipping.mapper.LttlWaybillMapper;
import org.jeecg.modules.lttl.shipping.service.ILttlWaybillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 运单主表
 * @Author: jeecg-boot
 * @Date:   2025-12-29
 * @Version: V1.0
 */
@Service
public class LttlWaybillServiceImpl extends ServiceImpl<LttlWaybillMapper, LttlWaybill> implements ILttlWaybillService {

	@Autowired
	private LttlWaybillMapper lttlWaybillMapper;
	@Autowired
	private LttlWaybillGoodsMapper lttlWaybillGoodsMapper;
	@Autowired
	private LttlWaybillFeeMapper lttlWaybillFeeMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(LttlWaybill lttlWaybill, List<LttlWaybillGoods> lttlWaybillGoodsList, List<LttlWaybillFee> lttlWaybillFeeList) {
		lttlWaybillMapper.insert(lttlWaybill);
		if(lttlWaybillGoodsList!=null && lttlWaybillGoodsList.size()>0) {
			for(LttlWaybillGoods entity:lttlWaybillGoodsList) {
				//外键设置
				entity.setWaybillId(lttlWaybill.getId());
				lttlWaybillGoodsMapper.insert(entity);
			}
		}
		if(lttlWaybillFeeList!=null && lttlWaybillFeeList.size()>0) {
			for(LttlWaybillFee entity:lttlWaybillFeeList) {
				//外键设置
				entity.setWaybillId(lttlWaybill.getId());
				lttlWaybillFeeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(LttlWaybill lttlWaybill,List<LttlWaybillGoods> lttlWaybillGoodsList,List<LttlWaybillFee> lttlWaybillFeeList) {
		lttlWaybillMapper.updateById(lttlWaybill);
		
		//1.先删除子表数据
		lttlWaybillGoodsMapper.deleteByMainId(lttlWaybill.getId());
		lttlWaybillFeeMapper.deleteByMainId(lttlWaybill.getId());
		
		//2.子表数据重新插入
		if(lttlWaybillGoodsList!=null && lttlWaybillGoodsList.size()>0) {
			for(LttlWaybillGoods entity:lttlWaybillGoodsList) {
				//外键设置
				entity.setWaybillId(lttlWaybill.getId());
				lttlWaybillGoodsMapper.insert(entity);
			}
		}
		if(lttlWaybillFeeList!=null && lttlWaybillFeeList.size()>0) {
			for(LttlWaybillFee entity:lttlWaybillFeeList) {
				//外键设置
				entity.setWaybillId(lttlWaybill.getId());
				lttlWaybillFeeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		lttlWaybillGoodsMapper.deleteByMainId(id);
		lttlWaybillFeeMapper.deleteByMainId(id);
		lttlWaybillMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			lttlWaybillGoodsMapper.deleteByMainId(id.toString());
			lttlWaybillFeeMapper.deleteByMainId(id.toString());
			lttlWaybillMapper.deleteById(id);
		}
	}
	
}
