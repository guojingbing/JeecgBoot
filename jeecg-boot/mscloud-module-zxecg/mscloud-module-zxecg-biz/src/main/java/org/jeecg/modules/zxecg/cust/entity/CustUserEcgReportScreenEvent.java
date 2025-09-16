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
 * @date 2025/9/2
 */
@Data
@TableName("cust_user_ecg_report_screen_event")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_report_screen_event对象", description = "消费者筛查事件表")
public class CustUserEcgReportScreenEvent implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "报告id")
    private Long repId;
    @ApiModelProperty(value = "测量记录id")
    private Long ecgId;
    @ApiModelProperty(value = "事件代码")
    private Integer eventCode;
    @ApiModelProperty(value = "事件严重程度分类（1：高危，2：严重）")
    private Integer eventType;
    @ApiModelProperty(value = "事件名称")
    private String eventName;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "事件开始时间戳")
    private Long eventStartTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "事件结束时间戳")
    private Long eventEndTime;
    @ApiModelProperty(value = "事件心率")
    private Integer eventBpm;
    @ApiModelProperty(value = "确认事件代码")
    private Integer confirmEventCode;
    @ApiModelProperty(value = "确认事件名称")
    private String confirmEventName;
    @ApiModelProperty(value = "确认结果 1、确认；2、忽略；0或null：待确认")
    private Integer confirmResult;
    @ApiModelProperty(value = "确认人")
    private Long confirmUserId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "确认时间")
    private Timestamp confirmTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;
    @ApiModelProperty(value = "是否已读 1已读0未读")
    private Integer readId=0;
    @ApiModelProperty(value = "是否推送 1已推送 0未推送")
    private Integer pushId=0;

}
