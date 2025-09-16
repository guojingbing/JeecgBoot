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
@TableName("cust_user_ecg_report_check_order_frag")
@Accessors
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_report_check_order_frag对象", description = "遥测报告片段表")
public class CustUserEcgReportCheckOrderFrag implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long fragId;
    @ApiModelProperty(value = "遥测报告id")
    private Long orderId;
    @ApiModelProperty(value = "监测记录id")
    private Long ecgId;
    @ApiModelProperty(value = "留图中心点时间戳")
    private Long fragCenterTime;
    @ApiModelProperty(value = "留图标题")
    private String fragTitle;
    @ApiModelProperty(value = "留图描述")
    private String fragDesc;
    @ApiModelProperty(value = "平均心率")
    private Integer avgBpm;
    @ApiModelProperty(value = "是否反转 1反转")
    private Integer isReversed = 0;
    private Timestamp createTime;
    private long createUserId;
    private Timestamp lastModifyTime;
    private long lastModifyUserId;
}
