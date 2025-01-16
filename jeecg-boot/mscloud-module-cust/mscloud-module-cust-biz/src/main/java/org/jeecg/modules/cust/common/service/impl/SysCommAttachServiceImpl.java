package org.jeecg.modules.cust.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.cust.common.entity.SysCommAttach;
import org.jeecg.modules.cust.common.entity.SysCommAttachItem;
import org.jeecg.modules.cust.common.mapper.SysCommAttachMapper;
import org.jeecg.modules.cust.common.service.ISysCommAttachItemService;
import org.jeecg.modules.cust.common.service.ISysCommAttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用附件业务
 */
@Service
public class SysCommAttachServiceImpl extends ServiceImpl<SysCommAttachMapper, SysCommAttach> implements ISysCommAttachService {
    @Autowired
    ISysCommAttachItemService itemSer;

    @Override
    @Transactional
    public SysCommAttach saveOrUpdateAttach(SysCommAttach sysCommAttach, List<SysCommAttachItem> items) {
        if(sysCommAttach.getId()==null){
            sysCommAttach.setCreateTime(new Date());
            super.saveOrUpdate(sysCommAttach);
        }
        sysCommAttach.setUpdateTime(new Date());
        if(!CollectionUtils.isEmpty(items)){
            items=items.stream().map(o->o.setAttachId(sysCommAttach.getId())).collect(Collectors.toList());
            itemSer.saveOrUpdateBatch(items);
        }
        return sysCommAttach;
    }
}
