package org.jeecg.modules.zxecg.cust.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @date 2025/9/1
 */

@Data
@TableName("cust_user_ecg_report_check_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_report_check_order对象", description = "心电遥测/动态报告")
public class CustUserEcgReportCheckOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "性别")
    private Integer gender;
    @ApiModelProperty(value = "申请科室id")
    private Long prescriberDeptId;
    @ApiModelProperty(value = "申请科室")
    private String prescriberDeptName;
    @ApiModelProperty(value = "设备归属机构")
    private Long companyId;
    @ApiModelProperty(value = "设备归属科室")
    private Long deptId;
    @ApiModelProperty(value = "开单医生编号")
    private Long preId;
    @ApiModelProperty(value = "开单医生姓名")
    private String prescriberName;
    @ApiModelProperty(value = "住院号")
    private String hospitalNumber;
    @ApiModelProperty(value = "病例号")
    private String caseNumber;
    @ApiModelProperty(value = "床位号")
    private String bedNumber;
    @ApiModelProperty(value = "检查单开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp orderStartTime;
    @ApiModelProperty(value = "检查单结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp orderEndTime;
    @ApiModelProperty(value = "实际结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp actEndTime;
    @ApiModelProperty(value = "1、遥测；2、动态")
    private Integer orderType;
    @ApiModelProperty(value = "确费状态(0:待确费，1：已确费)")
    private Integer feeConfirmStatus;
    @ApiModelProperty(value = "确费人")
    private Long feeConfirmUser;
    @ApiModelProperty(value = "确费时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp feeConfirmTime;
    @ApiModelProperty(value = "遥测通道记录编号")
    private Long remoteRecId;
    @ApiModelProperty(value = "遥测报告路径")
    private String remoteRepPath;
    @ApiModelProperty(value = "遥测报告生成时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp remoteRepTime;
    @ApiModelProperty(value = "关联动态报告Id")
    private Long holterRepId;
    @ApiModelProperty(value = "动态报告关联状态：0：待申请；10：待关联；20、已关联")
    private Integer holterRepStatus;
    @ApiModelProperty(value = "动态报告操作人")
    private Long holterRepOperUser;
    @ApiModelProperty(value = "操作时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp holterRepOperTime;
    @ApiModelProperty(value = "1、进行中；2、已结束")
    private Integer orderStatus;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //todo 字段名与数据库名不一致，是因为更新时 由于MMybatisInterceptor中自动给updateTime 字段赋值 类型是Date
    @TableField("update_time")
    private Timestamp operTime;
}
