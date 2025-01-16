package org.jeecg.modules.cust.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.cust.entity.MpHelloEntity;
import org.jeecg.modules.cust.mapper.MpHelloMapper;
import org.jeecg.modules.cust.service.IMpHelloService;
import org.springframework.stereotype.Service;

/**
 * 测试Service
 */
@Service
public class MpHelloServiceImpl extends ServiceImpl<MpHelloMapper, MpHelloEntity> implements IMpHelloService {

    @Override
    public String hello() {
        return "hello ，我是 mp 微服务节点!";
    }
}
