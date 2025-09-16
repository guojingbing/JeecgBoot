package org.jeecg.modules.zxecg.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.test.entity.TestInfo;

import java.util.List;

public interface ITestService extends IService<TestInfo> {
    List<TestInfo> getTestInfoList();
}
