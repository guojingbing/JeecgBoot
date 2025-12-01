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
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/1
 */

@Data
@TableName("cust_user_ecg_report_statistics")
@Accessors
@ApiModel(value = "cust_user_ecg_report_statistics对象", description = "报告结论")
public class CustUserEcgReportStatistics implements Serializable {
    @TableId(type = IdType.NONE)
    @ApiModelProperty(value = "报告id")
    private long repId;
    @ApiModelProperty(value = "平均心率")
    private Integer avgBpm;
    @ApiModelProperty(value = "最快心率")
    private Integer maxBpm;
    @ApiModelProperty(value = "最慢心率")
    private Integer minBpm;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最快心率时间")
    private Timestamp maxBpmDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最慢心率时间")
    private Timestamp minBpmDate;
    @ApiModelProperty(value = "总时长")
    private Integer duration;
    @ApiModelProperty(value = "有效时长")
    private Integer validDuration;
    @ApiModelProperty(value = "心搏数")
    private Integer beatNum;
    @ApiModelProperty(value = "室上性单发")
    private Integer sveIsolated;
    @ApiModelProperty(value = "室上性成对")
    private Integer sveCouplet;
    @ApiModelProperty(value = "室上性二联律")
    private Integer sveLvbe;
    @ApiModelProperty(value = "室上性二联律心搏数")
    private Integer sveLvbeBeat;
    @ApiModelProperty(value = "室上性三联律")
    private Integer sveLvte;
    @ApiModelProperty(value = "房早未下传")
    private Integer sveSa;
    @ApiModelProperty(value = "室上速")
    private Integer svtNum;
    @ApiModelProperty(value = "室上性三联律心搏数")
    private Integer sveLvteBeat;
    @ApiModelProperty(value = "室性单发")
    private Integer veIsolated;
    @ApiModelProperty(value = "室性成对")
    private Integer veCouplet;
    @ApiModelProperty(value = "室性二联律")
    private Integer veLvbe;
    @ApiModelProperty(value = "室性二联律心搏数")
    private Integer veLvbeBeat;
    @ApiModelProperty(value = "室性三联律")
    private Integer veLvte;
    @ApiModelProperty(value = "室性三联律心搏数")
    private Integer veLvteBeat;
    @ApiModelProperty(value = "Ront")
    private Integer veRont;
    @ApiModelProperty(value = "室速")
    private Integer vtNum;
    @ApiModelProperty(value = "室速心搏数")
    private Integer vtBeat;
    @ApiModelProperty(value = "室性心搏数")
    private Integer veBeat;
    @ApiModelProperty(value = "室上性心搏数")
    private Integer sveBeat;
    @ApiModelProperty(value = "室上速心搏数")
    private Integer svtBeat;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "房颤最长一段时间")
    private Timestamp afLongestDate;
    @ApiModelProperty(value = "房颤最长一段时长")
    private Integer afLongestTimes;
    @ApiModelProperty(value = "房颤最长一段平均心率")
    private Integer afLongestBpm;
    @ApiModelProperty(value = "房颤最快平均心率")
    private Integer afMaxAvgBpm;
    @ApiModelProperty(value = "房颤平均心率")
    private Integer afAvgBpm;
    @ApiModelProperty(value = "房颤心率范围")
    private String afBpmRange;
    @ApiModelProperty(value = "房颤总心搏数")
    private Integer afBeat;
    @ApiModelProperty(value = "房颤总时长")
    private Integer afTimes;
    @ApiModelProperty(value = "房颤总次数")
    private Integer afNum;
    @ApiModelProperty(value = "房颤负荷占比")
    private BigDecimal afPercent;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "室颤最长一段时间")
    private Timestamp vfLongestDate;
    @ApiModelProperty(value = "室颤最长一段时长")
    private Integer vfLongestTimes;
    @ApiModelProperty(value = "室颤最长一段平均心率")
    private Integer vfLongestBpm;
    @ApiModelProperty(value = "室颤最快平均心率")
    private Integer vfMaxAvgBpm;
    @ApiModelProperty(value = "室颤平均心率")
    private Integer vfAvgBpm;
    @ApiModelProperty(value = "室颤心率范围")
    private String vfBpmRange;
    @ApiModelProperty(value = "室颤总心搏数")
    private Integer vfBeat;
    @ApiModelProperty(value = "室颤总时长")
    private Integer vfTimes;
    @ApiModelProperty(value = "室颤总次数")
    private Integer vfNum;
    @ApiModelProperty(value = "室颤负荷百分比")
    private BigDecimal vfPercent;
    @ApiModelProperty(value = "室上速总时长")
    private Integer svtTimes;
    @ApiModelProperty(value = "室上速心率范围")
    private String svtBpmRange;
    @ApiModelProperty(value = "室上速平均心率")
    private Integer svtAvgBpm;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "室上速平均心率最快发生时间")
    private Timestamp svtMaxAvgBpmDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "室上速最长一段时间")
    private Timestamp svtLongestDate;
    @ApiModelProperty(value = "室上速最长一段时长")
    private Integer svtLongestTimes;
    @ApiModelProperty(value = "室上速负荷占比")
    private BigDecimal svtPercent;
    @ApiModelProperty(value = "室速总时长")
    private Integer vtTimes;
    @ApiModelProperty(value = "室速心率范围")
    private String vtBpmRange;
    @ApiModelProperty(value = "室速平均心率")
    private Integer vtAvgBpm;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "室速平均心率最快发生时间")
    private Timestamp vtMaxAvgBpmDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "室速最长一段时间")
    private Timestamp vtLongestDate;
    @ApiModelProperty(value = "室速最长一段时长")
    private Integer vtLongestTimes;
    @ApiModelProperty(value = "室速负荷占比")
    private BigDecimal vtPercent;
    @ApiModelProperty(value = "长RR间期总次数")
    private Integer pauseNum;
    @ApiModelProperty(value = "长RR间期最长一阵时长")
    private Double pauseLongestTime;
    @ApiModelProperty(value = "阻滞总次数")
    private Integer blockNum;
    @ApiModelProperty(value = "阻滞最长一阵时长")
    private Double blockLongestTime;
    @ApiModelProperty(value = "心动过速总时长")
    private Integer tcTimes;
    @ApiModelProperty(value = "心动过速总次数")
    private Integer tcNum;
    @ApiModelProperty(value = "心动过速心率范围")
    private String tcBpmRange;
    @ApiModelProperty(value = "心动过速平均心率")
    private Integer tcAvgBpm;
    @ApiModelProperty(value = "心动过缓总时长")
    private Integer bcTimes;
    @ApiModelProperty(value = "心动过缓总次数")
    private Integer bcNum;
    @ApiModelProperty(value = "心动过缓心率范围")
    private String bcBpmRange;
    @ApiModelProperty(value = "心动过缓平均心率")
    private Integer bcAvgBpm;
    @ApiModelProperty(value = "室性逸搏单发")
    private Integer vebSingle;
    @ApiModelProperty(value = "室性逸搏联律")
    private Integer vebSeg;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "长RR间期最长一阵发生时间")
    private Timestamp pauseLongestDate;
    @ApiModelProperty(value = "室性逸搏心搏")
    private Integer vebBeat;
    @ApiModelProperty(value = "房性逸搏单发")
    private Integer svebSingle;
    @ApiModelProperty(value = "房性逸搏联律")
    private Integer svebSeg;
    @ApiModelProperty(value = "房性逸搏心搏")
    private Integer svebBeat;
    @ApiModelProperty(value = "交界性逸搏单发")
    private Integer jebSingle;
    @ApiModelProperty(value = "交界性逸搏联律")
    private Integer jebSeg;
    @ApiModelProperty(value = "交界性逸搏心搏")
    private Integer jebBeat;
    @ApiModelProperty(value = "心房起搏单发")
    private Integer apSingle;
    @ApiModelProperty(value = "心房起搏联律")
    private Integer apSeg;
    @ApiModelProperty(value = "心房起搏心搏")
    private Integer apBeat;
    @ApiModelProperty(value = "心室起搏单发")
    private Integer vpSingle;
    @ApiModelProperty(value = "心室起搏联律")
    private Integer vpSeg;
    @ApiModelProperty(value = "心室起搏心搏")
    private Integer vpBeat;
    @ApiModelProperty(value = "房室起搏单发")
    private Integer avpSingle;
    @ApiModelProperty(value = "房室起搏联律")
    private Integer avpSeg;
    @ApiModelProperty(value = "房室起搏心搏")
    private Integer avpBeat;
    @ApiModelProperty(value = "统计结论")
    private String anaConclusion;
    @ApiModelProperty(value = "医师意见")
    private String doctorConclusion;
    @ApiModelProperty(value = "结论医师")
    private Long doctorUserId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "报告日期")
    private Timestamp confirmTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上次结论时间")
    private Timestamp lastAnaConclusionTime;
    @ApiModelProperty(value = "统计结论模板")
    private String anaConclusionTemplate;
    @ApiModelProperty(value = "人工结论标志")
    private Integer custAnaConclusion;
    @ApiModelProperty(value = "结论修改标志")
    private Integer modifyId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上次统计数据更新时间")
    private Timestamp lastStatisticsTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "创建人编号")
    private Long createUserId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后修改时间")
    private Timestamp lastModifyTime;
    @ApiModelProperty(value = "最后修改人")
    private Long lastModifyUserId;
    @ApiModelProperty(value = "打印项目")
    private String reportItems;
    @ApiModelProperty(value = "排除打印的项目")
    private String excludeItems;
    @ApiModelProperty(value = "自动结论是否换行")
    private Integer conclusionWrap = 0;
}
