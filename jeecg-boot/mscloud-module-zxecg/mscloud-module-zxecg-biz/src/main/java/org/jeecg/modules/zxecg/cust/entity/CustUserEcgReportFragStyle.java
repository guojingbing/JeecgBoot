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

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/11
 */

@Data
@TableName("cust_user_ecg_report_frag_style")
@Accessors
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_report_frag_style对象", description = "留图样式表")
public class CustUserEcgReportFragStyle implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "报告id")
    private Long repId;
    @ApiModelProperty(value = "项目类别")
    private Integer categoryId;
    @ApiModelProperty(value = "留图样式")
    private Integer style;
}
