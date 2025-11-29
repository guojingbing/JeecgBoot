package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgShortTermEvent;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 * @description 快速筛查事件相关操作
 */


public interface ICustUserEcgShortTermEventService extends IService<CustUserEcgShortTermEvent> {
    void deleteEventsByEcgId(Long ecgId);
}
