package org.jeecg.modules.zxecg.test.mapper.phoenix;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.zxecg.test.entity.TestInfo;

//@PhoenixMapper
public interface TestPhoenixMapper extends BaseMapper<TestInfo> {
    TestInfo getTestInfoById(@Param("id") String id);
}
