package org.jeecg.modules.zxecg.cust.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/9
 */

@Data
@TableName("cust_user_mc_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_mc_order对象", description = "分析解读列表")
public class CustUserMcOrder {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long orderId;
    @ApiModelProperty(value = "生成规则：类别代码+当前日期+流水号")
    private String orderNo;
    @ApiModelProperty(value = "1、人工分析;2、智能筛查；9、报告签名审核生成订单")
    private Integer orderTypeId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "订单描述")
    private String orderDesc;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "用户年龄")
    private Integer userAge;
    @ApiModelProperty(value = "性别")
    private Integer userGender;
    @ApiModelProperty(value = "用户身高")
    private Double userHeight;
    @ApiModelProperty(value = "用户体重")
    private Double userWeight;
    @ApiModelProperty(value = "单位：分")
    private Long orderFee;
    private Long consultingFee;
    private Long analysisFee;
    @ApiModelProperty(value = "医生id")
    private Long doctorId;
    @ApiModelProperty(value = "接单方式：1、用户选择；2、医生抢单")
    private Integer receiveType;
    @ApiModelProperty(value = "接单时间")
    private Timestamp receiveTime;
    @ApiModelProperty(value = "创建人")
    private Long createUserId;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "WAITPAYID:待生成支付单;NOTPAY:未支付；SUCCESS：支付成功；CLOSED：已关闭；FINISHED：已完成")
    private String orderStatus;
    @ApiModelProperty(value = "状态原因id")
    private Integer statusReasonId;
    private String orderConclusion;
    @ApiModelProperty(value = "支付订单id")
    private Long payOrderId;
    @ApiModelProperty(value = "最后修改时间")
    private Timestamp lastModifyTime;
    @ApiModelProperty(value = "订单生效时间")
    private Timestamp orderEffected;
    @ApiModelProperty(value = "过期时间")
    private Timestamp orderExpire;
    @ApiModelProperty(value = "机构id")
    private Long companyId;
    @ApiModelProperty(value = "开始时间")
    private Timestamp fillinTime;

}
