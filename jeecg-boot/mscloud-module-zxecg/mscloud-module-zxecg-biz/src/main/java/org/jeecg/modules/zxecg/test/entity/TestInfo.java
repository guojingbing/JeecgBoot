package org.jeecg.modules.zxecg.test.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName(value = "test_info", keepGlobalPrefix = true, schema = "TEST")
public class TestInfo {
    @TableId(value = "id", type = IdType.NONE/*, type = IdType.ASSIGN_ID*/)
    private String id;
    @TableField(value = "name"/*, condition = SqlCondition.LIKE*/)
    private String name;
    @TableField(value = "age")
    private Integer age;
}
