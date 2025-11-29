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
 * @date 2025/8/29
 */

@Data
@TableName("zx_kb_data_ecg")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "zx_kb_data_ecg对象", description = "测量记录入库表")
public class ZxKbDataEcg implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long dataId;
    @ApiModelProperty(value = "测量记录id")
    private Long ecgId;
    @ApiModelProperty(value = "入库时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dataTime;
    @ApiModelProperty(value = "操作人")
    private Long createUserId;
    @ApiModelProperty(value = "备注")
    private String remark;
}
