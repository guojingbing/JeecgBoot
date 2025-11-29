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
 * @date 2025/8/22
 */
@Data
@TableName("cust_user_ecg_short_term_event")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_short_term_event对象", description = "快速检测记录事件表")
public class CustUserEcgShortTermEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long eventId;
    @ApiModelProperty(value = "ecgId")
    private Long ecgId;
    @ApiModelProperty(value = "对应基础代码表中的CD802的code_id")
    private Integer eventCode;
    @ApiModelProperty(value = "严重等级（1：正常，2：异常，3：严重异常）")
    private Integer eventLevel;
    @ApiModelProperty(value = "对应基础代码表中的CD802的code_name")
    private String eventName;
    @ApiModelProperty(value = "是否确认（0或null:未确认，1：确认）")
    private Integer isConfirm;
    @ApiModelProperty(value = "来源（1：app,2:web）")
    private Integer src;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
    @ApiModelProperty(value = "创建人")
    private Long createId;
}
