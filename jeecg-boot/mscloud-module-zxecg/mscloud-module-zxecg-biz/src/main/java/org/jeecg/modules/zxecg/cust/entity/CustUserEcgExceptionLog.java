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
 * @date 2025/8/25
 */

@Data
@TableName("cust_user_ecg_exception_log")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_exception_log对象", description = "APP日志记录")
public class CustUserEcgExceptionLog implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long logId;
    @ApiModelProperty(value = "用户Id")
    private Long userId;
    @ApiModelProperty(value = "业务流程阶段")
    private Integer businessStep;
    @ApiModelProperty(value = "日志标记")
    private Integer logFlag;
    @ApiModelProperty(value = "日志内容")
    private String content;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "日志日期")
    private Timestamp logTime;
    @ApiModelProperty(value = "下载地址")
    private String logFileAddr;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
}
