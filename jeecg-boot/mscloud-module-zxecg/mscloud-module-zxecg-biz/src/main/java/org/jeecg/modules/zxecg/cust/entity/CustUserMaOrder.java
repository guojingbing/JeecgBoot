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
 * @date 2025/9/8
 */

@Data
@TableName("cust_user_ma_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ma_order对象", description = "分析订单列表")
public class CustUserMaOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long orderId;
    @ApiModelProperty(value = "生成规则：类别代码+当前日期+流水号")
    private String orderNo;
    @ApiModelProperty(value = "1、人工分析;2、智能筛查；9、报告签名审核生成订单")
    private Integer orderType;
    @ApiModelProperty(value = "订单描述")
    private String orderDesc;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "用户名")
    private String custUserName;
    @ApiModelProperty(value = "用户年龄")
    private Integer custUserAge;
    @ApiModelProperty(value = "用户身高")
    private Double custUserHeight;
    @ApiModelProperty(value = "用户体重")
    private Double custUserWeight;
    @ApiModelProperty(value = "开始时间")
    private Timestamp ecgStartTime;
    @ApiModelProperty(value = "结束时间")
    private Timestamp ecgEndTime;
    private Long validDuration;
    @ApiModelProperty(value = "单位：分")
    private Long orderFee;
    @ApiModelProperty(value = "创建人")
    private Long createUserId;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;
    @ApiModelProperty(value = "WAITPAYID:待生成支付单;NOTPAY:未支付；SUCCESS：支付成功；CLOSED：已关闭；FINISHED：已完成")
    private String orderStatus;
    @ApiModelProperty(value = "状态原因id")
    private Integer statusReasonId;
    @ApiModelProperty(value = "最后修改时间")
    private Timestamp lastModifyDate;
    @ApiModelProperty(value = "支付订单id")
    private Long payOrderId;
    @ApiModelProperty(value = "订单生效时间")
    private Timestamp orderEffected;
    @ApiModelProperty(value = "过期时间")
    private Timestamp orderExpire;
    @ApiModelProperty(value = "用户手机好")
    private String custUserTel;
    @ApiModelProperty(value = "用户性别")
    private Integer custUserGender;
    @ApiModelProperty(value = "机构id")
    private Long companyId;
    @ApiModelProperty(value = "医生id")
    private Long doctorId;
    private Long voucherId;
    @ApiModelProperty(value = "销售订单id")
    private Long saleOrderId;
    @ApiModelProperty(value = "审核机构id")
    private Long auditCompanyId;
    @ApiModelProperty(value = "审核状态")
    private Integer auditStatus;
    @ApiModelProperty(value = "审核用户状态")
    private Long auditUserId;
    @ApiModelProperty(value = "审核时间")
    private Timestamp auditTime;
    @ApiModelProperty(value = "描述")
    private String auditDesc;
}
