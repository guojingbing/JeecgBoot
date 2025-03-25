package org.jeecg.modules.openapi.oapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.openapi.oapi.mapper.EcgOrderDispMapper;
import org.jeecg.modules.openapi.oapi.service.IEcgOrderDispService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class EcgOrderDispServiceImpl extends ServiceImpl<EcgOrderDispMapper,String> implements IEcgOrderDispService {
    @Override
    public Map selectDispByRep(String repId) {
        return baseMapper.selectDispByRep(repId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRep(String repId, String downloadUrl) {
        baseMapper.updateDispRep(repId,downloadUrl);
    }
}
