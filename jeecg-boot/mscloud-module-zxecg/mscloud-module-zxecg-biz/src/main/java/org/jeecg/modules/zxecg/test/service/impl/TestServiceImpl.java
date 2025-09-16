package org.jeecg.modules.zxecg.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.test.entity.TestInfo;
import org.jeecg.modules.zxecg.test.mapper.phoenix.TestMapper;
import org.jeecg.modules.zxecg.test.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 分类字典
 * @Author: jeecg-boot
 * @Date:   2019-05-29
 * @Version: V1.0
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, TestInfo> implements ITestService {
    @Autowired
    TestMapper  testMapper;

    @Override
    public List<TestInfo> getTestInfoList() {
        return testMapper.getTestInfoList();
    }
}
