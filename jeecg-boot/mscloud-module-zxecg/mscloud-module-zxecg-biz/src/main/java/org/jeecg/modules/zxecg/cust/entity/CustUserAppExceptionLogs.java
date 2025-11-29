package org.jeecg.modules.zxecg.cust.entity;

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
 * @date 2025/8/27
 */

@Data
@TableName("cust_user_app_exception_logs")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_app_exception_logs对象", description = "采样率异常")
public class CustUserAppExceptionLogs implements Serializable {
    private static final long serialVersionUID = 2758076449438252092L;
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long logId;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "测量记录id")
    private Long ecgId;
    @ApiModelProperty(value = "设备编号")
    private String machSn;
    @ApiModelProperty(value = "异常类型")
    private Integer expType;
    @ApiModelProperty(value = "app上传的异常发生时间戳")
    private Long expTime;
    @ApiModelProperty(value = "持续时长")
    private Long duration;
    @ApiModelProperty(value = "异常描述")
    private String expDesc;
    @ApiModelProperty(value = "对应的测量记录开始索引点")
    private Long startIndex;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上传时间")
    private Timestamp uploadTime;
}
