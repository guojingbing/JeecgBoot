package org.jeecg.modules.zxecg.system.service;

import org.jeecg.modules.zxecg.system.entity.CommSystemBussiEventWarningNotify;

import java.util.List;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 * @description 运维预警通知
 */


public interface ICommSystemBussiEventWarningNotifyService {

    List<CommSystemBussiEventWarningNotify> getNotifyList();

    void setWarning(Integer notifyLevel, String notifyPhones);
}
