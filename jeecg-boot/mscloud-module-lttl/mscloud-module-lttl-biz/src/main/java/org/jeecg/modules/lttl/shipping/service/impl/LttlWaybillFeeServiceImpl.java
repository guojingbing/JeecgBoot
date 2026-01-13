package org.jeecg.modules.lttl.shipping.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybillFee;
import org.jeecg.modules.lttl.shipping.mapper.LttlWaybillFeeMapper;
import org.jeecg.modules.lttl.shipping.service.ILttlWaybillFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 运单费用表
 * @Author: jeecg-boot
 * @Date:   2025-12-29
 * @Version: V1.0
 */
@Service
public class LttlWaybillFeeServiceImpl extends ServiceImpl<LttlWaybillFeeMapper, LttlWaybillFee> implements ILttlWaybillFeeService {
	
	@Autowired
	private LttlWaybillFeeMapper lttlWaybillFeeMapper;
	
	@Override
	public List<LttlWaybillFee> selectByMainId(String mainId) {
		return lttlWaybillFeeMapper.selectByMainId(mainId);
	}
}
