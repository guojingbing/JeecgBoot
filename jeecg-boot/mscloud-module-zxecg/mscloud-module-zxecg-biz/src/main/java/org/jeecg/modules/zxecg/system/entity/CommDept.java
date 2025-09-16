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
@TableName("comm_dept")
@Accessors
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "comm_dept", description = "部门管理表")
public class CommDept implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long deptId;
    private Long companyId;
    private String deptNo;
    private String deptName;
    private Long deptType;
    private String address;
    private String contact;
    private String telNo;
    private String faxNumber;
    private Boolean isUse;
    private Boolean isSystem;
    private Long parentDeptId;
    private String nodePath;
    private String nodeFullName;
    private Boolean isLeaf;
    private Long orderNum;
    private String longLatVal;
    private String longLatAddress;
    private String remark;
    private Long createUserId;
    private Timestamp createDate;
    private Long lastModifyUserId;
    private Timestamp lastModifyDate;
    private Long versionId;
    private Long marketId;
    private String ekgDeptCode;
}
