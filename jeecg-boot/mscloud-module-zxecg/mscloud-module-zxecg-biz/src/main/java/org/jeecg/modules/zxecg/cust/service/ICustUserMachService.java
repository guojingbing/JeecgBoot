package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserMach;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 * @description 用户设备绑定
 */


public interface ICustUserMachService extends IService<CustUserMach> {
    Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order);

    /**
     * 解绑
     *
     * @param userMach
     */
    void machUnbind(CustUserMach userMach);

    CustUserMach getMachByUserIdAndMachSn(String machSn, Long userId);
}
