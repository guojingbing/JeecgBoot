package org.jeecg.modules.cust.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cust.common.entity.SysCommAttachItem;

import java.util.List;
import java.util.Map;

/**
 * 通用附件业务
 */
public interface ISysCommAttachItemService extends IService<SysCommAttachItem> {
    /**
     * 根据业务主键查询附件
     * @param bussi
     * @param bussiDataId
     * @return
     */
    List<Map> getAttachItemsByBussiDataId(String bussi, String bussiDataId);
}
