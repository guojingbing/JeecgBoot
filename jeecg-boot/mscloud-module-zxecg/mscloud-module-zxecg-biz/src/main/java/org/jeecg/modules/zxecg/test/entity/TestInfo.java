package org.jeecg.modules.zxecg.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("test_info")
public class TestInfo {
    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
}
