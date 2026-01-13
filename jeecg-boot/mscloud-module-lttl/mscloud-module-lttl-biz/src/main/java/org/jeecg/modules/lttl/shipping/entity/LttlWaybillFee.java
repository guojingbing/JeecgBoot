package org.jeecg.modules.lttl.shipping.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 运单费用表
 * @Author: jeecg-boot
 * @Date:   2025-12-29
 * @Version: V1.0
 */
@Schema(description="运单费用表")
@Data
@TableName("lttl_waybill_fee")
public class LttlWaybillFee implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private String id;
	/**创建人*/
    @Schema(description = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建日期")
    private Date createTime;
	/**更新人*/
    @Schema(description = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @Schema(description = "所属部门")
    private String sysOrgCode;
	/**运单外键*/
    @Schema(description = "运单外键")
    private String waybillId;
	/**费用编号*/
	@Excel(name = "费用编号", width = 15)
    @Schema(description = "费用编号")
    private String feeNo;
	/**费用类型*/
	@Excel(name = "费用类型", width = 15, dicCode = "lttl_fee_type")
	@Dict(dicCode = "lttl_fee_type")
    @Schema(description = "费用类型")
    private String feeTypeId;
	/**费用金额*/
	@Excel(name = "费用金额", width = 15)
    @Schema(description = "费用金额")
    private java.math.BigDecimal feeAmount;
	/**收支类型*/
	@Excel(name = "收支类型", width = 15, dicCode = "lttl_transaction_type")
	@Dict(dicCode = "lttl_transaction_type")
    @Schema(description = "收支类型")
    private String ieType;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @Schema(description = "备注")
    private String remark;
}
