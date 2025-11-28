package org.jeecg.modules.zxecg.test.service;

import org.jeecg.modules.zxecg.test.entity.TestInfo;

/**
 * 测试接口
 */
public interface ITestInfoService {
    TestInfo getTestInfoById(String id);
}