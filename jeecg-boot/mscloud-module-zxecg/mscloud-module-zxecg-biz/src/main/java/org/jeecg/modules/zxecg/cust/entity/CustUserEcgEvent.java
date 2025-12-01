package org.jeecg.modules.zxecg.cust.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/12
 */
@Data
@TableName("cust_user_ecg_event")
@Accessors
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_event对象", description = "用户事件表")
public class CustUserEcgEvent implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long eventRecId;
    @ApiModelProperty(value = "测量记录id")
    private Long ecgId;
    @ApiModelProperty(value = "事件位置")
    private Long eventIndex;
    @ApiModelProperty(value = "事件发生时间")
    private Timestamp eventTime;
    @ApiModelProperty(value = "描述")
    private String eventDesc;
    @ApiModelProperty(value = "时长")
    private Integer duration;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "不适应症")
    private Long poseId;
}
