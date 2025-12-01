package org.jeecg.modules.zxecg.test.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.test.entity.TestInfo;
import org.jeecg.modules.zxecg.test.mapper.phoenix.TestPhoenixMapper;
import org.jeecg.modules.zxecg.test.service.ITestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试Service
 */
@Service
public class TestInfoServiceImpl extends ServiceImpl<TestPhoenixMapper, TestInfo> implements ITestInfoService {
    @Autowired
    private TestPhoenixMapper testPhoenixMapper;

    @Override
    public TestInfo getTestInfoById(String id) {
        return testPhoenixMapper.getTestInfoById(id);
    }
}
