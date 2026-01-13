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
 * @Description: 运单货物表
 * @Author: jeecg-boot
 * @Date:   2025-12-29
 * @Version: V1.0
 */
@Schema(description="运单货物表")
@Data
@TableName("lttl_waybill_goods")
public class LttlWaybillGoods implements Serializable {
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
	/**货物代码*/
	@Excel(name = "货物代码", width = 15)
    @Schema(description = "货物代码")
    private String goodsNo;
	/**品名*/
	@Excel(name = "品名", width = 15)
    @Schema(description = "品名")
    private String goodsName;
	/**包装*/
	@Excel(name = "包装", width = 15, dicCode = "lttl_package_type")
	@Dict(dicCode = "lttl_package_type")
    @Schema(description = "包装")
    private String goodsPkg;
	/**件数*/
	@Excel(name = "件数", width = 15)
    @Schema(description = "件数")
    private Integer goodsPcs;
	/**元/件*/
	@Excel(name = "元/件", width = 15)
    @Schema(description = "元/件")
    private java.math.BigDecimal unitPrice;
	/**重量(千克)*/
	@Excel(name = "重量(千克)", width = 15)
    @Schema(description = "重量(千克)")
    private java.math.BigDecimal goodsWeight;
	/**元/kg*/
	@Excel(name = "元/kg", width = 15)
    @Schema(description = "元/kg")
    private java.math.BigDecimal unitPriceWeight;
	/**体积(方)*/
	@Excel(name = "体积(方)", width = 15)
    @Schema(description = "体积(方)")
    private java.math.BigDecimal goodsVolume;
	/**元/方*/
	@Excel(name = "元/方", width = 15)
    @Schema(description = "元/方")
    private java.math.BigDecimal unitPriceVolume;
	/**规格*/
	@Excel(name = "规格", width = 15)
    @Schema(description = "规格")
    private String goodsSpecs;
	/**运费*/
	@Excel(name = "运费", width = 15)
    @Schema(description = "运费")
    private java.math.BigDecimal shippingFee;
	/**运费折扣*/
	@Excel(name = "运费折扣", width = 15)
    @Schema(description = "运费折扣")
    private java.math.BigDecimal shippingFeeDiscount;
	/**信息费*/
	@Excel(name = "信息费", width = 15)
    @Schema(description = "信息费")
    private java.math.BigDecimal infoFee;
	/**返佣方式*/
	@Excel(name = "返佣方式", width = 15)
    @Schema(description = "返佣方式")
    private String infoFeePaymentType;
	/**代收货款*/
	@Excel(name = "代收货款", width = 15)
    @Schema(description = "代收货款")
    private java.math.BigDecimal codAmount;
	/**送货费*/
	@Excel(name = "送货费", width = 15)
    @Schema(description = "送货费")
    private java.math.BigDecimal deliveryFee;
	/**保险费*/
	@Excel(name = "保险费", width = 15)
    @Schema(description = "保险费")
    private java.math.BigDecimal insuranceFee;
	/**接货费*/
	@Excel(name = "接货费", width = 15)
    @Schema(description = "接货费")
    private java.math.BigDecimal pickupFee;
	/**实际接货费*/
	@Excel(name = "实际接货费", width = 15)
    @Schema(description = "实际接货费")
    private java.math.BigDecimal actualPickupFee;
	/**装卸费*/
	@Excel(name = "装卸费", width = 15)
    @Schema(description = "装卸费")
    private java.math.BigDecimal luFee;
	/**其他费用*/
	@Excel(name = "其他费用", width = 15)
    @Schema(description = "其他费用")
    private java.math.BigDecimal otherFee;
	/**仓位*/
	@Excel(name = "仓位", width = 15)
    @Schema(description = "仓位")
    private String storingLocation;
	/**进仓费*/
	@Excel(name = "进仓费", width = 15)
    @Schema(description = "进仓费")
    private java.math.BigDecimal storingFee;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @Schema(description = "备注")
    private String remark;
}
