package org.jeecg.modules.openapi.base.service;

import org.jeecg.modules.openapi.base.entity.SysOpenAuthUserApi;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface ISysOpenAuthUserApiService extends IService<SysOpenAuthUserApi> {


    List<SysOpenAuthUserApi> selectListByAuthId(String id, Integer type, Integer code);
}
