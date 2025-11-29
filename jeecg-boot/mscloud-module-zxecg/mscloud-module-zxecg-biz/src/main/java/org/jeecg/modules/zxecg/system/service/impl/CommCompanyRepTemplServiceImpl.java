package org.jeecg.modules.zxecg.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.system.entity.CommCompanyRepTempl;
import org.jeecg.modules.zxecg.system.mapper.CommCompanyRepTemplMapper;
import org.jeecg.modules.zxecg.system.service.ICommCompanyRepTemplService;
import org.springframework.stereotype.Service;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/8
 */

@Service
public class CommCompanyRepTemplServiceImpl extends ServiceImpl<CommCompanyRepTemplMapper, CommCompanyRepTempl> implements ICommCompanyRepTemplService {
    @Override
    public CommCompanyRepTempl getRepTemplByCompanyId(Long companyId) {
        return this.baseMapper.getRepTemplByCompanyId(companyId);
    }
}
