package org.jeecg.modules.zxecg.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 * @description 运维预警通知
 */

@Data
@TableName("comm_system_bussi_event_warning_notify")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "comm_system_bussi_event_warning_notify对象", description = "运维预警通知表")
public class CommSystemBussiEventWarningNotify implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "通知级别")
    private Integer notifyLevel;
    @ApiModelProperty(value = "通知电话")
    private String notifyPhones;
}
