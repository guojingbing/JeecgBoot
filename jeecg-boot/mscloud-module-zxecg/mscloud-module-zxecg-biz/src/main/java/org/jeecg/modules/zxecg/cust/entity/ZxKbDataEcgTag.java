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

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/29
 */

@Data
@TableName("zx_kb_data_ecg_tag")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "zx_kb_data_ecg_tag对象", description = "测量记录入库事件标签表")
public class ZxKbDataEcgTag implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long tagId;
    @ApiModelProperty(value = "入库id")
    private Long dataId;
    @ApiModelProperty(value = "测量记录id")
    private Integer dataTypeId;

}
