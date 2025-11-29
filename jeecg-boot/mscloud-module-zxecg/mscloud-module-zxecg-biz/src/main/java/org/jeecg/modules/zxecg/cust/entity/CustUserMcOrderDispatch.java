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
 * @date 2025/9/9
 */

@Data
@TableName("cust_user_mc_order_dispatch")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_mc_order_dispatch对象", description = "分析解读列表")
public class CustUserMcOrderDispatch implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long dispId;
    @ApiModelProperty(value = "派单号")
    private String dispNo;
    @ApiModelProperty(value = "订单id")
    private Long orderId;
    @ApiModelProperty(value = "派单类型")
    private Integer dispTypeId;
    @ApiModelProperty(value = "派单状态")
    private String dispStatus;
    @ApiModelProperty(value = "医生id")
    private Long doctorId;
    @ApiModelProperty(value = "接单时间")
    private Timestamp receiveTime;
    @ApiModelProperty(value = "结论")
    private String mcConclusion;
    @ApiModelProperty(value = "状态原因id")
    private Integer statusReasonId;
    @ApiModelProperty(value = "最后修改时间")
    private Timestamp lastModifyDate;
    @ApiModelProperty(value = "最后修改人")
    private Long lastModifyUserId;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;
    @ApiModelProperty(value = "创建人")
    private Long createUserId;
    @ApiModelProperty(value = "机构id")
    private Long companyId;
    @ApiModelProperty(value = "开始时间")
    private Timestamp fillinTime;
}
