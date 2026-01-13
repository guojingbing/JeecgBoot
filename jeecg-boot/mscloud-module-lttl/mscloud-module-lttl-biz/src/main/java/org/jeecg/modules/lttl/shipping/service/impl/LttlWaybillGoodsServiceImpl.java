package org.jeecg.modules.lttl.shipping.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybillGoods;
import org.jeecg.modules.lttl.shipping.mapper.LttlWaybillGoodsMapper;
import org.jeecg.modules.lttl.shipping.service.ILttlWaybillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 运单货物表
 * @Author: jeecg-boot
 * @Date:   2025-12-29
 * @Version: V1.0
 */
@Service
public class LttlWaybillGoodsServiceImpl extends ServiceImpl<LttlWaybillGoodsMapper, LttlWaybillGoods> implements ILttlWaybillGoodsService {
	
	@Autowired
	private LttlWaybillGoodsMapper lttlWaybillGoodsMapper;
	
	@Override
	public List<LttlWaybillGoods> selectByMainId(String mainId) {
		return lttlWaybillGoodsMapper.selectByMainId(mainId);
	}
}
