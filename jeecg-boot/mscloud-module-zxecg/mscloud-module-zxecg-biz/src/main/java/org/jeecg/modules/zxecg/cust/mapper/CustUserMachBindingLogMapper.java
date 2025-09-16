package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.zxecg.cust.entity.CustUserMachBindingLog;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 * @description
 */


public interface CustUserMachBindingLogMapper extends BaseMapper<CustUserMachBindingLog> {
    List<Map> loadListPaging(Page<Map> page, Long bindingId, String column, String order);

}
