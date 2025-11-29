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
 * @date 2025/9/5
 */

@Data
@TableName("cust_user_ecg_report_frag")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_report_frag对象", description = "报告片段表")
public class CustUserEcgReportFrag implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long fragId;
    @ApiModelProperty(value = "报告id")
    private Long repId;
    @ApiModelProperty(value = "大类，例如：首页重点条图、心电事件-室早、心电事件-室上早、用户事件、其他典型条图等")
    private Integer categoryId;
    @ApiModelProperty(value = "明细分类，每个大类下面的子类")
    private Integer subCategoryId;
    @ApiModelProperty(value = "留图中心点时间戳")
    private Long fragCenterTime;
    @ApiModelProperty(value = "留图标题")
    private String fragTitle;
    @ApiModelProperty(value = "留图描述")
    private String fragDesc;
    @ApiModelProperty(value = "平均心率")
    private Integer avgBpm;
    @ApiModelProperty(value = "最快心率")
    private Integer maxBpm;
    @ApiModelProperty(value = "最慢心率")
    private Integer minBpm;
    @ApiModelProperty(value = "持续时长")
    private Double fragPeriod;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "创建人")
    private long createUserId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后修改时间")
    private Timestamp lastModifyTime;
    @ApiModelProperty(value = "修改人")
    private long lastModifyUserId;
    @ApiModelProperty(value = "测量记录id")
    private Long ecgId;
    @ApiModelProperty(value = "留图开始时间戳")
    private Long fragStartTime;
    @ApiModelProperty(value = "留图结束时间戳")
    private Long fragEndTime;
    @ApiModelProperty(value = "主键")
    private Integer beatNum;
    @ApiModelProperty(value = "是否人工留图")
    private Integer manulId;
    @ApiModelProperty(value = "排序号")
    private Integer orderNo;
    @ApiModelProperty(value = "是否反转 1反转")
    private Integer isReversed=0;
    @ApiModelProperty(value = "多导联数据是否只打印主导联")
    private Integer isMainLead=0;
}
