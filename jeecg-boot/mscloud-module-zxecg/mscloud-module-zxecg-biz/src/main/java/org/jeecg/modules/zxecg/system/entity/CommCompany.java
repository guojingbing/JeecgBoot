package org.jeecg.modules.zxecg.system.entity;

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
 * @date 2025/9/15
 * @description
 */

@Data
@TableName("comm_company")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "comm_company对象", description = "机构管理表")
public class CommCompany implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long companyId;
    private String companyNo;
    private String companyName;
    private String fullName;
    @ApiModelProperty(value = "分公司类型1：分公司；2：总部")
    private Long companyType = 1L;
    private String corporation;
    private String address;
    private String contact;
    private String telNo;
    private String faxNumber;
    private Boolean isUse = false;
    private String lngLatVal;
    private String lngLatAddress;
    private String remark;
    private Long createUserId;
    private Timestamp createDate;
    private Long lastModifyUserId;
    private Timestamp lastModifyDate;
    private Long versionId;
    private String servRadius;
    private Timestamp effectiveDate;
    @ApiModelProperty(value = "功能权限集编号")
    private Long fpsId;
    private Boolean isCooper = false;
    private Long levelId;
    private Long areaId;
    private String province;
    private String city;
    private String district;
    private String street;
    private Boolean isAnaCompany=false;
    private Boolean isAuditCompany=false;
    @ApiModelProperty(value = "1、标准报告；2、咨询报告；3、院内报告")
    private Integer repTemplate=1;
//    @ApiModelProperty(value = "定制系统logo地址")
//    private String logoAddress;
//    @ApiModelProperty(value = "定制系统名称")
//    private String customSysName;
    @ApiModelProperty(value = "电子签章文件路径")
    private String signaturePath;
    @ApiModelProperty(value = "大屏标题")
    private String biTitle;
    @ApiModelProperty(value = "大屏地图显示层级 0全国1省2市3地区")
    private Integer biMapLevel;
    @ApiModelProperty(value = "报告预处理")
    private Boolean needRepHelp=false;
}
