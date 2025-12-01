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
 * @description 运维预警
 */

@Data
@TableName("comm_system_bussi_event_warning")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "comm_system_bussi_event_warning对象", description = "运维预警表")
public class CommSystemBussiEventWarning implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "等级")
    private Integer level;
    @ApiModelProperty(value = "包含业务单号、用户编号、用户姓名等相关信息")
    private String eventDesc;
    @ApiModelProperty(value = "发生时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp eventTime;
    @ApiModelProperty(value = "业务来源：1、后端；2、APP")
    private Integer eventBussi;
    @ApiModelProperty(value = "用户识别，app或后端账号主")
    private Long userId;
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    @ApiModelProperty(value = "处理状态(0:未处理，1：已处理)")
    private Integer dealStatus;
    @ApiModelProperty(value = "基础代码管理：1、已解决；2、需升级系统；3、需升级APP")
    private Integer dealResult;
    @ApiModelProperty(value = "处理时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dealTime;
    @ApiModelProperty(value = "处理人")
    private Long dealUserId;
    @ApiModelProperty(value = "是否发送短信（0：没发送，1：发送）")
    private Integer isMsg;
}
