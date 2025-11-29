package org.jeecg.modules.zxecg.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.system.entity.CommSystemBussiEventWarningNotify;
import org.jeecg.modules.zxecg.system.mapper.CommSystemBussiEventWarningNotifyMapper;
import org.jeecg.modules.zxecg.system.service.ICommSystemBussiEventWarningNotifyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 * @description 运维预警通知相关操作
 */

@Service
public class CommSystemBussiEventWarningNotifyServiceImpl extends ServiceImpl<CommSystemBussiEventWarningNotifyMapper, CommSystemBussiEventWarningNotify> implements ICommSystemBussiEventWarningNotifyService {

    @Override
    public List<CommSystemBussiEventWarningNotify> getNotifyList() {
        return this.baseMapper.selectList(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setWarning(Integer notifyLevel, String notifyPhones) {
        List<CommSystemBussiEventWarningNotify> notifyList = getNotifyList();
        CommSystemBussiEventWarningNotify notify = null;
        if (CollectionUtils.isEmpty(notifyList)) {
            notify = new CommSystemBussiEventWarningNotify();
        } else {
            notify = notifyList.get(0);
        }
        notify.setNotifyLevel(notifyLevel);
        notify.setNotifyPhones(notifyPhones);
        this.saveOrUpdate(notify);
    }
}
