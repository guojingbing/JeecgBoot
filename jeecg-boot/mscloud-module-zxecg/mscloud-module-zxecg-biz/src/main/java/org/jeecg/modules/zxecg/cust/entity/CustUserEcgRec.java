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
 */

@Data
@TableName("cust_user_ecg_rec")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_rec对象", description = "监测记录")
public class CustUserEcgRec implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long ecgId;
    @ApiModelProperty(value = "记录单号")
    private String ecgNo;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    private String appEcgId;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "测量记录开始时间")
    private Timestamp ecgStartTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "测量记录结束时间")
    private Timestamp ecgEndTime;
    @ApiModelProperty(value = "测量记录结类型")
    private Long ecgType;
    @ApiModelProperty(value = "文件数量")
    private Integer fileNum;
    @ApiModelProperty(value = "上传状态")
    private Integer dataFileStatus;
    @ApiModelProperty(value = "异常数量")
    private Integer abnormalNum;
    @ApiModelProperty(value = "设备编号")
    private String machSn;
    private Long attachmentId;
    private Integer versionId;
    private Integer src;
    private Long appGenTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上传时间")
    private Timestamp uploadTime;
    @ApiModelProperty(value = "佩戴方式")
    private Integer wearWay;
    @ApiModelProperty(value = "文件路径")
    private String dataFilePath;
    @ApiModelProperty(value = "文件大小")
    private Long fileSize;
    @ApiModelProperty(value = "文件加密")
    private String fileMd5;
    @ApiModelProperty(value = "文件名")
    private String fileName;
    @ApiModelProperty(value = "app算法版本")
    private String appAlgVer;
    @ApiModelProperty(value = "当前APP版本号")
    private String appVer;
    @ApiModelProperty(value = "分析状态")
    private Integer anaStatus;
    @ApiModelProperty(value = "分段类型")
    private Integer segmentType;
    @ApiModelProperty(value = "分段原因")
    private String segmentReason;
    @ApiModelProperty(value = "机构id")
    private Long companyId;
    @ApiModelProperty(value = "增加分析数据app版本号")
    private String anaAppVer;
    @ApiModelProperty(value = "手机型号")
    private String phoneModel;
    @ApiModelProperty(value = "版本")
    private String phoneOsVer;
    @ApiModelProperty(value = "滤波算法")
    private Integer waveFilter;
    @ApiModelProperty(value = "1、原始数据，null或0、检波后的数据")
    private Integer annoIndexBased;
    @ApiModelProperty(value = "文件存储根目录索引")
    private Integer rootPathIndex;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后备份时间")
    private Timestamp lastBackupTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后同步时间")
    private Timestamp lastSyncTime;
    @ApiModelProperty(value = "后台检波算法版本号")
    private String bdacAlgVer;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后编辑时间")
    private Timestamp lastEditTime;
    @ApiModelProperty(value = "有效时长，秒")
    private Integer validDuration;
    @ApiModelProperty(value = "更新事件标记")
    private Integer reanaStatus;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "hbase备份时间")
    private Timestamp hbaseBakTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "备份截止测量记录时间")
    private Timestamp bakDeadline;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "hbase是否清除")
    private Integer hbaseCleaned;
    @ApiModelProperty(value = "部门id")
    private Long deptId;
    @ApiModelProperty(value = "手机imei")
    private String imei;
    @ApiModelProperty(value = "0或null:bin;1：TXT格式")
    private Integer fdataTxt;
    @ApiModelProperty(value = "心搏数量")
    private Long beatNum;
    @ApiModelProperty(value = "导联数量")
    private Integer leadNum;
    @ApiModelProperty(value = "时区")
    private String timeZone;
    @ApiModelProperty(value = "跟数据库时区（当前东八区）的时区差，例如：东6区''-02:00'")
    private String timeZoneDiff;
    @ApiModelProperty(value = "地区")
    private String locale;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后更新事件算法执行时间")
    private Timestamp lastUpeventTime;
}
