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
 * @date 2025/8/22
 */

@Data
@TableName("cust_user_mach")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_mach对象", description = "用户设备绑定信息")
public class CustUserMach implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long bindingId;
    @ApiModelProperty(value = "用户Id")
    private Long userId;
    @ApiModelProperty(value = "设备编号")
    private String machSn;
    @ApiModelProperty(value = "设备类型")
    private Integer machType;
    @ApiModelProperty(value = "设备mac")
    private String machMac;
    @ApiModelProperty(value = "uuid")
    private String uuid;
    @ApiModelProperty(value = "手机唯一识别字段")
    private Integer deviceFlag;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "绑定时间")
    private Timestamp bindingTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "解绑时间")
    private Timestamp unbindTime;
    @ApiModelProperty(value = "绑定状态")
    private Integer bindingStatus;
    @ApiModelProperty(value = "机构id")
    private Long companyId;
    @ApiModelProperty(value = "部门id")
    private Long deptId;


}
