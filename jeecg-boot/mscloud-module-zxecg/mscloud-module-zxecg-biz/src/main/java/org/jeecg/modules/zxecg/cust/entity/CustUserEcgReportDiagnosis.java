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
@TableName("cust_user_ecg_report_diagnosis")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_report_diagnosis对象", description = "报告AI解读表")
public class CustUserEcgReportDiagnosis implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "报告id")
    private Long repId;
    @ApiModelProperty(value = "附件记录表主键")
    private Long attachId;
    @ApiModelProperty(value = "诊断类型：默认1、AI诊断")
    private Integer type;
    @ApiModelProperty(value = "诊断状态：默认0，提交诊断结论后修改为1")
    private Integer diagStatus;
    @ApiModelProperty(value = "输入描述信息")
    private String inDesc;
    @ApiModelProperty(value = "报告结论描述")
    private String repDesc;
    @ApiModelProperty(value = "输出诊断建议")
    private String outDesc;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作时间")
    private Timestamp operTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后修改时间")
    private Timestamp lastModifyDate;
}
