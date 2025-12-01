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
 * @date 2025/9/10
 */

@Data
@TableName("cust_user_ecg_report_result_change")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_report_result_change对象", description = "自动分析结果表")
public class CustUserEcgReportResultChange implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private long resId;
    @ApiModelProperty(value = "报告id")
    private Long repId;
    @ApiModelProperty(value = "测量记录id")
    private Long ecgId;
    @ApiModelProperty(value = "事件类型")
    private Integer typeId;
    @ApiModelProperty(value = "发生时间")
    private Long abnTime;
    @ApiModelProperty(value = "1增加2删除")
    private Integer operId;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "创建人")
    private long createUserId;


}
