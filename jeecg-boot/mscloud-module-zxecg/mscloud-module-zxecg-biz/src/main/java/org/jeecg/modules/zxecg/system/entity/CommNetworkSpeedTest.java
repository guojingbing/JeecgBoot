package org.jeecg.modules.zxecg.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 * @description 网络测速
 */

@Data
@TableName("comm_network_speed_test")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "comm_network_speed_test对象", description = "网络测速表")
public class CommNetworkSpeedTest implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "下载速度")
    private Double downloadSpeed;
    @ApiModelProperty(value = "响应速度")
    private Double respSpeed;
    @ApiModelProperty(value = "抖动")
    private Double respShake;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "测试时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp testTime;
    @ApiModelProperty(value = "用户识别，app或后端账号主")
    private Long testUserId;
}
