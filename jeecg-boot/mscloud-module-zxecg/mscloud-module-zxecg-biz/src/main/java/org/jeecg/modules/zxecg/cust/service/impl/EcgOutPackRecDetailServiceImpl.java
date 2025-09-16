package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.EcgOutPackRecDetail;
import org.jeecg.modules.zxecg.cust.mapper.EcgOutPackRecDetailMapper;
import org.jeecg.modules.zxecg.cust.service.IEcgOutPackRecDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/27
 * @description ecg批量下载详情相关操作
 */

@Service
public class EcgOutPackRecDetailServiceImpl extends ServiceImpl<EcgOutPackRecDetailMapper, EcgOutPackRecDetail> implements IEcgOutPackRecDetailService {
    @Override
    public List<Map<String, Object>> getListByPackId(Long packId) {
        return this.baseMapper.getListByPackId(packId);
    }
}
