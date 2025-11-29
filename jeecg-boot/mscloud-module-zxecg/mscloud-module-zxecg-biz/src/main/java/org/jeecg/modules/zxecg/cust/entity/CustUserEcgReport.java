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
import java.util.Date;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/29
 */

@Data
@TableName("cust_user_ecg_report")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "cust_user_ecg_report对象", description = "心电报告")
public class CustUserEcgReport implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long repId;
    @ApiModelProperty(value = "报告单号")
    private String repNo;
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "报告开始时间")
    private Timestamp ecgStartTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "报告结束时间")
    private Timestamp ecgEndTime;
    @ApiModelProperty(value = "有效时长")
    private Long validDuration;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "报告日期")
    private Timestamp reportDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "生成时间")
    private Timestamp generateTime;
    @ApiModelProperty(value = "报告状态1：自动分析完成；2：人工分析完成")
    private Integer reportStatus;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "pdf生成时间")
    private Timestamp pdfTime;
    @ApiModelProperty(value = "结论")
    private String anaConclusion;
    @ApiModelProperty(value = "医师结论")
    private String doctorConclusion;
    @ApiModelProperty(value = "报告类型 1、日报；2、人工合并；3、订单合并")
    private Integer repType;
    @ApiModelProperty(value = "确认状态")
    private Integer confirmStatus;
    @ApiModelProperty(value = "pdf下载路径")
    private String pdfFilePath;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "确认时间")
    private Timestamp confirmTime;
    @ApiModelProperty(value = "医生id")
    private Integer doctorUserId;
    @ApiModelProperty(value = "医生id")
    private Integer abnNum;
    @ApiModelProperty(value = "报告类目")
    private String reportItems;
    @ApiModelProperty(value = "最后锁定编辑人")
    private Integer lockUserId;
    @ApiModelProperty(value = "机构id")
    private Long companyId;
    @ApiModelProperty(value = "住院号")
    private String hospitalNumber;
    @ApiModelProperty(value = "科室号")
    private String sectionNumber;
    @ApiModelProperty(value = "科室名称")
    private String deptName;
    @ApiModelProperty(value = "登记号")
    private String userRegFlag;
    @ApiModelProperty(value = "检查单号")
    private String hisBillNo;
    @ApiModelProperty(value = "模板最后处理截止ECG时间")
    private String matchSyncEcgTime;
    @ApiModelProperty(value = "模板最后编辑时间")
    private Timestamp lastMatchEditTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private Date birthDate;
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    @ApiModelProperty(value = "用户性别")
    private Integer userGender;
    @ApiModelProperty(value = "最后备份报告时间")
    private Timestamp lastBackupTime;
    @ApiModelProperty(value = "最后同步备份时间")
    private Timestamp lastSyncTime;
    @ApiModelProperty(value = "记录模板备份时对应的测量记录截止时间")
    private Timestamp matchDeadline;
    @ApiModelProperty(value = "hbase记录是否已经清除，1、清除；其他、未清除")
    private Integer hbaseCleaned;
    @ApiModelProperty(value = "部门id")
    private Long deptId;
    @ApiModelProperty(value = "数据库版本2:hbase;其他:mysql")
    private Integer dbVerId;
    @ApiModelProperty(value = "更新事件标记")
    private Integer reanaStatus;
    @ApiModelProperty(value = "记录时长（秒）")
    private Long ecgDuration;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "报告内容最后变更时间：更新事件、最快最慢心率人工指定")
    private Timestamp lastRepChangedTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后报告统计结论生成时间")
    private Timestamp lastAnaConclusionTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "报告留图最后更新时间")
    private Timestamp lastRepFragChangedTime;
    @ApiModelProperty(value = "自由留图模式")
    private Integer fragStyle;
    @ApiModelProperty(value = "RHC报告同步状态；1、已同步；0或null：未同步")
    private Integer ekgSyncStatus;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "RHC报告同步时间")
    private Timestamp ekgSyncTime;
    @ApiModelProperty(value = "三方授权，区域医疗机构对应的授权记录主键")
    private String openAuthId;
    @ApiModelProperty(value = "最后修改用户id")
    private Long lastModifyUserId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后修改时间")
    private Timestamp lastModifyDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "报告创建时间")
    private Timestamp createDate;
    @ApiModelProperty(value = "报告创建用户id")
    private long createUserId;


}
