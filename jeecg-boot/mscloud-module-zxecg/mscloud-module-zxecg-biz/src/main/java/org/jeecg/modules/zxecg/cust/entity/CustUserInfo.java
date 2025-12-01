package org.jeecg.modules.zxecg.cust.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/21
 */

@Data
@TableName("cust_userinfo")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_userinfo对象", description = "用户信息")
public class CustUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long userId;
    @ApiModelProperty(value = "用户账号")
    private String userNo;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "用户号码区号")
    private String userTelRegion;
    @ApiModelProperty(value = "用户号码")
    private String userTel;
    @ApiModelProperty(value = "用户邮箱")
    private String email;
    @ApiModelProperty(value = "性别")
    private Integer userGender;
    @ApiModelProperty(value = "出生日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    @ApiModelProperty(value = "身高")
    private Double height;
    @ApiModelProperty(value = "体重")
    private Double weight;
    @ApiModelProperty(value = "地区Id")
    private Long areaId;
    @ApiModelProperty(value = "住址")
    private String address;
    @ApiModelProperty(value = "所属城市")
    private String userCity;
    @ApiModelProperty(value = "所属省份")
    private String userProvince;
    @ApiModelProperty(value = "所属国家")
    private String userCountry;
    @ApiModelProperty(value = "用户头像url")
    private String userAvatar;

    private Long exerciseAmountId;

    private Long smokingDegreeId;
    @ApiModelProperty(value = "总积分")
    private Integer fullPoint;
    @ApiModelProperty(value = "剩余积分")
    private Integer remainPoint;
    @ApiModelProperty(value = "用户状态（1:正常，0：禁用）")
    private Integer userStatus;
    @ApiModelProperty(value = "注册时选择购买意向")
    private Long machineType;
    @ApiModelProperty(value = "注册时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp regTime;
    @ApiModelProperty(value = "注册来源")
    private Integer regSrc;
    @ApiModelProperty(value = "最后一次签到时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lastSignDate;
    @ApiModelProperty(value = "会员状态（0：禁用，1：正常，2：禁言）")
    private Integer status;

    private Integer version;

    private String ilness;
    @ApiModelProperty(value = "病史")
    private String medical;
    @ApiModelProperty(value = "不适症状")
    private String symptom;
    @ApiModelProperty(value = "生活习惯")
    private String habits;

    private Integer sellerId;

    private Integer agentId;
    @ApiModelProperty(value = "会员等级")
    private Integer vipLevel;
    @ApiModelProperty(value = "1、普通用户；2、医疗版用户")
    private Integer userType;
    @ApiModelProperty(value = "医院科室编号")
    private Integer hospDeptId;
    @ApiModelProperty(value = "科室名称")
    private String hospDeptName;
    @ApiModelProperty(value = "住院号")
    private String hospNumber;
    @ApiModelProperty(value = "床位号")
    private String hospSectionNumber;
    @ApiModelProperty(value = "手机唯一识别字段")
//    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String deviceFlag;
    @ApiModelProperty(value = "最后修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp lastModifyDate;

}
