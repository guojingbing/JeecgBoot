package org.jeecg.modules.zxecg.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserMach;
import org.jeecg.modules.zxecg.cust.service.ICustUserMachService;
import org.jeecg.modules.zxecg.system.entity.CommCompanyMach;
import org.jeecg.modules.zxecg.system.mapper.CommCompanyMachMapper;
import org.jeecg.modules.zxecg.system.service.ICommCompanyMachService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/26
 */

@Service
public class CommCompanyMachServiceImpl extends ServiceImpl<CommCompanyMachMapper, CommCompanyMach> implements ICommCompanyMachService {
    @Resource
    ICustUserMachService userMachService;

    @Override
    public Page<Map> loadListPaging(Page<Map> page, Long loginUserId, Map<String, Object> likeMap, String column, String order) {
        return page.setRecords(this.baseMapper.loadListPaging(page, loginUserId, likeMap, column, order));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(CommCompanyMach companyMach) {
        // todo 获取当前登录用户id
        Long loginUserId = 13L;
        Integer status = null == companyMach.getMachStatus() ? 1 : companyMach.getMachStatus();
        //原状态为启用中, 则变成禁用
        if (status == 1) {
            companyMach.setMachStatus(2);
            //禁用操作同时后台解绑当前绑定用户
            Map machInfo = this.baseMapper.getMachInfo(companyMach.getMachId(), loginUserId);
            if (machInfo != null) {
                CustUserMach mach = userMachService.getMachByUserIdAndMachSn(companyMach.getMachSn(), (Long) machInfo.get("userId"));
                if (null != mach) {
                    mach.setBindingStatus(2);
                    mach.setUnbindTime(new Timestamp(System.currentTimeMillis()));
                    userMachService.updateById(mach);
                }
            }
        } else if (status == 2) {
            //原状态为禁用, 则变成启用中
            companyMach.setMachStatus(1);
        }
        companyMach.setStatusTime(new Timestamp(System.currentTimeMillis()));
        companyMach.setOperUserId(loginUserId);
        this.updateById(companyMach);
    }
}
