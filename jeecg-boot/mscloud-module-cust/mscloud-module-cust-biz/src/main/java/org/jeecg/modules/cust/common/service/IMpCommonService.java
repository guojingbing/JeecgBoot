package org.jeecg.modules.cust.common.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 小程序通用业务
 */
public interface IMpCommonService extends IService<String> {
    /**
     * 根据dictCode和itemCode获取数据字典项目
     * @param dictCode
     * @param itemCode
     * @return
     */
    List<Map<String, Object>> getDictItemsByDictCode(String dictCode, String itemCode);
}
