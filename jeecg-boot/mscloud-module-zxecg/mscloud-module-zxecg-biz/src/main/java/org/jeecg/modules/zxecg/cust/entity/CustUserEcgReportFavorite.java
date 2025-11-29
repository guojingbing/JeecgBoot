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
 * @date 2025/8/29
 */

@Data
@TableName("cust_user_ecg_report_favorite")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_report_favorite对象", description = "报告收藏表")
public class CustUserEcgReportFavorite implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long favId;
    @ApiModelProperty(value = "报告id")
    private Long repId;
    @ApiModelProperty(value = "操作人id")
    private Long userId;
}
