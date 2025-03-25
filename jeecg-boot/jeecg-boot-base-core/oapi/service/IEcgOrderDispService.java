package org.jeecg.modules.openapi.oapi.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface IEcgOrderDispService extends IService<String> {
    Map selectDispByRep(String repId);

    void updateRep(String repId, String downloadUrl);
}
