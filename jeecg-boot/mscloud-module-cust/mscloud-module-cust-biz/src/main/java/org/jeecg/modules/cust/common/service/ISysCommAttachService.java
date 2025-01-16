package org.jeecg.modules.cust.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cust.common.entity.SysCommAttach;
import org.jeecg.modules.cust.common.entity.SysCommAttachItem;

import java.util.List;

/**
 * 通用附件业务
 */
public interface ISysCommAttachService extends IService<SysCommAttach> {
    /**
     * 保存附件信息
     * @param sysCommAttach
     * @param items
     * @return
     */
    SysCommAttach saveOrUpdateAttach(SysCommAttach sysCommAttach, List<SysCommAttachItem> items);
}
