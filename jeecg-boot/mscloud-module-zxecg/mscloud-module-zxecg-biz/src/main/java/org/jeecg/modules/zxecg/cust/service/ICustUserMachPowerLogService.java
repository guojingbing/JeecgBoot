package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.CustUserMachPowerLog;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 * @description 设备电量
 */


public interface ICustUserMachPowerLogService extends IService<CustUserMachPowerLog> {

    Page<Map> loadListPaging(Page<Map> pageList, Long bindingId, String column, String order);
}
