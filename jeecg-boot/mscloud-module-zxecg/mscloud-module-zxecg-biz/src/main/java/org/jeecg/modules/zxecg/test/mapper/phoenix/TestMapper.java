package org.jeecg.modules.zxecg.test.mapper.phoenix;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.zxecg.test.entity.TestInfo;

import java.util.List;

public interface TestMapper extends BaseMapper<TestInfo> {
    List<TestInfo> getTestInfoList();
}
