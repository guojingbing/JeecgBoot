package org.jeecg.modules.zxecg.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/8
 */

@Data
@TableName("comm_company_rep_templ")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "comm_company_rep_templ对象", description = "报告打印模板表")
public class CommCompanyRepTempl {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long templId;
    private Long companyId;
    private String templStyle;
    private Boolean optBlankFill;
    private Boolean optShowBeat;
    private Boolean optShowSign;
    private String repTitle;
    private String repSubTitle;
    private String logoPath;
    private Long createUserId;
    private Timestamp createDate;
    private Long lastModifyUserId;
    private Timestamp lastModifyDate;
    private Long versionId;
    private String excludeItems;
    private String repFooter;
    private String extraTempl;
    private Integer hospFontSize;
    private String itemSeq;
    private Boolean isRemoteRep=false;
    private Boolean isHolterRep=false;
    private String mustRepItems;
    private String eventPrefix;
    private String remoteRepTitle;
    private String remoteRepSubTitle;
    private Integer remoteHolterDays;
    private Boolean optShowGrid;
    private String defaultUnprintItems;
    private Boolean scatterHideX=false;
    private Boolean scatterHideUnknow=false;
    private Boolean footerLeft;
    private Boolean blackHr;
}
