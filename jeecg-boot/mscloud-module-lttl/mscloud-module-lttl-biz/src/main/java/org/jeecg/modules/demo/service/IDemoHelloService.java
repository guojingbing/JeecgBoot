package org.jeecg.modules.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.entity.DemoHelloEntity;

/**
 * 测试接口
 */
public interface IDemoHelloService extends IService<DemoHelloEntity> {

    String hello();

}
