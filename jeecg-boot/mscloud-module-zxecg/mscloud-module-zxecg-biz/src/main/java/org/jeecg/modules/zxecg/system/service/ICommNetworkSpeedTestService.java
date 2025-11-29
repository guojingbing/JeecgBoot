package org.jeecg.modules.zxecg.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.zxecg.system.entity.CommNetworkSpeedTest;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 * @description 网络测速
 */


public interface ICommNetworkSpeedTestService {
    Page<Map> loadListPaging(Page<Map> pageList, Map<String, Object> likeMap, String column, String order);

    void record(CommNetworkSpeedTest speedTest);
}
