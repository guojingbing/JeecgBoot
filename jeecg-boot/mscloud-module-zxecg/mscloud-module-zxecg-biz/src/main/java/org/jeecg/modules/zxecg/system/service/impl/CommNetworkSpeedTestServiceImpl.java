package org.jeecg.modules.zxecg.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.system.entity.CommNetworkSpeedTest;
import org.jeecg.modules.zxecg.system.mapper.CommNetworkSpeedTestMapper;
import org.jeecg.modules.zxecg.system.service.ICommNetworkSpeedTestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 * @description 网络测速相关操作
 */

@Service
public class CommNetworkSpeedTestServiceImpl extends ServiceImpl<CommNetworkSpeedTestMapper, CommNetworkSpeedTest> implements ICommNetworkSpeedTestService {
    @Override
    public Page<Map> loadListPaging(Page<Map> pageList, Map<String, Object> likeMap, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadListPaging(pageList, likeMap, column, order));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void record(CommNetworkSpeedTest speedTest) {
        this.save(speedTest);
    }
}
