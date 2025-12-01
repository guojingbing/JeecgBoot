package org.jeecg.modules.zxecg.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.system.entity.CommCompanyPrescriber;
import org.jeecg.modules.zxecg.system.mapper.CommCompanyPrescriberMapper;
import org.jeecg.modules.zxecg.system.service.ICommCompanyPrescriberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/8
 * @description 开单医生相关操作
 */

@Service
public class CommCompanyPrescriberServiceImpl extends ServiceImpl<CommCompanyPrescriberMapper, CommCompanyPrescriber> implements ICommCompanyPrescriberService {
    @Override
    public List<Map<String, Object>> getCompanyPrescriber(Long companyId, Long deptId) {
        return this.baseMapper.getCompanyPrescriber(companyId, deptId);
    }
}
