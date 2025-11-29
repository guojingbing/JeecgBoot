package org.jeecg.modules.zxecg.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.modules.zxecg.system.entity.CommSystemBussiEventWarning;
import org.jeecg.modules.zxecg.system.mapper.CommSystemBussiEventWarningMapper;
import org.jeecg.modules.zxecg.system.service.ICommSystemBussiEventWarningService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 * @description 运维预警相关操作
 */

@Service
public class CommSystemBussiEventWarningServiceImpl extends ServiceImpl<CommSystemBussiEventWarningMapper, CommSystemBussiEventWarning> implements ICommSystemBussiEventWarningService {
    @Override
    public Page<Map> loadListPaging(Page<Map> page, Map<String, Object> likeMap, String column, String order) {
        return page.setRecords(this.baseMapper.loadListPaging(page, likeMap, column, order));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealWarning(String ids, Integer dealResult, Long loginUserId) {
        List<String> repsList = Arrays.asList(ids.split(","));
        List<Long> list = repsList.stream().map(Long::new).collect(Collectors.toList());
        Collection newList = new ArrayList<>(list.size());
        for (Long id : list) {
            //根据id查询出相应记录
            CommSystemBussiEventWarning warning = this.getById(id);
            if (null == warning) {
                continue;
            }
            warning.setDealResult(dealResult);
            warning.setDealStatus(1);
            warning.setDealUserId(13L);
            warning.setDealTime(new Timestamp(System.currentTimeMillis()));
            newList.add(warning);
        }
        if (CollectionUtils.isNotEmpty(newList)) {
            this.updateBatchById(newList);
        }
    }

}
