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
 * @date 2025/8/27
 */
@Data
@TableName("cust_user_ecg_rec_signal_weak")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_rec_signal_weak对象", description = "低振幅数据提醒表")
public class CustUserEcgRecSignalWeak implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long sigId;
    @ApiModelProperty(value = "测量记录Id")
    private Long ecgId;
    @ApiModelProperty(value = "开始索引")
    private Long startIndex;
    @ApiModelProperty(value = "开始时间戳")
    private Long startTimestamp;
    @ApiModelProperty(value = "结束时间戳")
    private Long endTimestamp;
    @ApiModelProperty(value = "结束索引")
    private Long endIndex;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;
}
