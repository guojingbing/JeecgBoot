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
@TableName("cust_user_ma_order_audit_log")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ma_order_audit_log对象", description = "审核记录列表")
public class CustUserMaOrderAuditLog implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long logId;
    @ApiModelProperty(value = "订单id")
    private Long orderId;
    @ApiModelProperty(value = "操作")
    private Integer oper;
    @ApiModelProperty(value = "描述")
    private String operDesc;
    @ApiModelProperty(value = "操作人")
    private Long operUserId;
    @ApiModelProperty(value = "操作时间")
    private Timestamp operTime;
}
