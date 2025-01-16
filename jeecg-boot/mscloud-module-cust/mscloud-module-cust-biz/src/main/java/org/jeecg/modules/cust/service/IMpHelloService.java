package org.jeecg.modules.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cust.entity.MpHelloEntity;

/**
 * 测试接口
 */
public interface IMpHelloService extends IService<MpHelloEntity> {

    String hello();

}
