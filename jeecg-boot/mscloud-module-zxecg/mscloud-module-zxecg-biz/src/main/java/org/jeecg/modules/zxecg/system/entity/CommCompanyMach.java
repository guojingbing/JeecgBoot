package org.jeecg.modules.zxecg.system.entity;

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
 * @description 设备管理
 */

@Data
@TableName("comm_company_mach")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "comm_company_mach对象", description = "设备管理表")
public class CommCompanyMach implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer machId;
    @ApiModelProperty(value = "机构id")
    private Integer companyId;
    @ApiModelProperty(value = "部门id")
    private Integer deptId;
    @ApiModelProperty(value = "设备编号")
    private String machSn;
    @ApiModelProperty(value = "设备类型")
    private Integer machType;
    @ApiModelProperty(value = "开始日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp startDate;
    @ApiModelProperty(value = "结束日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp endDate;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
    @ApiModelProperty(value = "设备状态 1、启用中；2、已禁用")
    private Integer machStatus;
    @ApiModelProperty(value = "状态修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp statusTime;
    @ApiModelProperty(value = "操作用户id")
    private Long operUserId;
    @ApiModelProperty(value = "营销管理系统代理商编号")
    private Long dmsDealerId;
    @ApiModelProperty(value = "营销管理系统分销渠道编号")
    private Long dmsDeptId;
    @ApiModelProperty(value = "1、人工结束；2：设备回收；3、设备禁用")
    private Integer endReason;

}
