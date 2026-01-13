package org.jeecg.modules.stock.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.stock.common.entity.SysCommAttachItem;
import org.jeecg.modules.stock.common.mapper.SysCommAttachItemMapper;
import org.jeecg.modules.stock.common.service.ISysCommAttachItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 通用附件业务
 */
@Service
public class SysCommAttachItemServiceImpl extends ServiceImpl<SysCommAttachItemMapper, SysCommAttachItem> implements ISysCommAttachItemService {
    @Autowired
    private SysCommAttachItemMapper mapper;

    @Override
    public List<Map> getAttachItemsByBussiDataId(String bussi, String bussiDataId){
        return mapper.getAttachItemsByBussiDataId(bussi,bussiDataId);
    }
}
