package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.entity.EcgOutPackRecDetail;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/27
 * @description ECG批量下载详情表
 */


public interface IEcgOutPackRecDetailService extends IService<EcgOutPackRecDetail> {
    List<Map<String, Object>> getListByPackId(Long packId);
}
