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
 * @date 2025/9/3
 */

@Data
@TableName("cust_user_ecg_report_screen_note")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_report_screen_note对象", description = "消费者筛查回访建议表")
public class CustUserEcgReportScreenNote implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;
    private Long repId;
    @ApiModelProperty(value = "对应KB101中的code_id")
    private Long templateId;
    @ApiModelProperty(value = "对应remark")
    private String templateDesc;
    @ApiModelProperty(value = "医生补充建议内容")
    private String note;
    @ApiModelProperty(value = "最后修改用户id")
    private Long lastModifyUserId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后修改时间")
    private Timestamp lastModifyDate;
}
