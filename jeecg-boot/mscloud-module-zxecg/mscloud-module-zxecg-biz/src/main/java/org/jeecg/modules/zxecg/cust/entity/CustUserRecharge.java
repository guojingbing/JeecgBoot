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
 * @date 2025/8/26
 */

@Data
@TableName("cust_user_recharge")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_recharge对象", description = "用户卡券兑换表")
public class CustUserRecharge implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long rechargeId;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "卡券号")
    private String cardNo;
    @ApiModelProperty(value = "兑换码")
    private String rechargeCode;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "兑换时间")
    private Timestamp rechargeTime;
    @ApiModelProperty(value = "1、兑换成功；2、兑换码错误；3、其他失败")
    private Integer rechargeStatus;
    private String statusReason;
}
