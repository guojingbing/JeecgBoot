package org.jeecg.modules.lttl.shipping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 运单主表
 * @Author: jeecg-boot
 * @Date:   2025-12-29
 * @Version: V1.0
 */
@Schema(description="运单主表")
@Data
@TableName("lttl_waybill")
public class LttlWaybill implements Serializable {
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
	/**运单号*/
	@Excel(name = "运单号", width = 15)
    @Schema(description = "运单号")
    private String billNo;
	/**厂家单号*/
	@Excel(name = "厂家单号", width = 15)
    @Schema(description = "厂家单号")
    private String externalBillNo;
	/**厂家名称*/
	@Excel(name = "厂家名称", width = 15)
    @Schema(description = "厂家名称")
    private String externalCorpName;
	/**托运时间*/
	@Excel(name = "托运时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "托运时间")
    private Date billTime;
	/**启运站名称*/
	@Excel(name = "启运站名称", width = 15)
    @Schema(description = "启运站名称")
    private String fromStation;
	/**启运站*/
	@Excel(name = "启运站", width = 15, dictTable = "lttl_base_station", dicText = "station_name", dicCode = "id")
    @Dict(dictTable = "lttl_base_station", dicText = "station_name", dicCode = "id")
    @Schema(description = "启运站")
    private String fromStationId;
	/**经由站名称*/
	@Excel(name = "经由站名称", width = 15)
    @Schema(description = "经由站名称")
    private String viaStation;
	/**经由站*/
	@Excel(name = "经由站", width = 15, dictTable = "lttl_base_station", dicText = "station_name", dicCode = "id")
    @Dict(dictTable = "lttl_base_station", dicText = "station_name", dicCode = "id")
    @Schema(description = "经由站")
    private String viaStationId;
	/**目的站名称*/
	@Excel(name = "目的站名称", width = 15)
    @Schema(description = "目的站名称")
    private String destStation;
	/**目的站*/
	@Excel(name = "目的站", width = 15, dictTable = "lttl_base_station", dicText = "station_name", dicCode = "id")
    @Dict(dictTable = "lttl_base_station", dicText = "station_name", dicCode = "id")
    @Schema(description = "目的站")
    private String destStationId;
	/**运输方式*/
	@Excel(name = "运输方式", width = 15, dicCode = "lttl_trans_way")
    @Dict(dicCode = "lttl_trans_way")
    @Schema(description = "运输方式")
    private String transWayId;
	/**接货方式*/
	@Excel(name = "接货方式", width = 15, dicCode = "lttl_pickup_way")
    @Dict(dicCode = "lttl_pickup_way")
    @Schema(description = "接货方式")
    private String pickupWayId;
	/**送货方式*/
	@Excel(name = "送货方式", width = 15, dicCode = "lttl_delivery_way")
    @Dict(dicCode = "lttl_delivery_way")
    @Schema(description = "送货方式")
    private String deliveryWayId;
	/**接货车编号*/
	@Excel(name = "接货车编号", width = 15)
    @Schema(description = "接货车编号")
    private String pickupVehicleId;
	/**接货车*/
	@Excel(name = "接货车", width = 15)
    @Schema(description = "接货车")
    private String pickupVehiclePlateNumber;
	/**接货司机*/
	@Excel(name = "接货司机", width = 15)
    @Schema(description = "接货司机")
    private String pickupDriverName;
	/**客户编号*/
	@Excel(name = "客户编号", width = 15)
    @Schema(description = "客户编号")
    private String corpId;
	/**发货人*/
	@Excel(name = "发货人", width = 15)
    @Schema(description = "发货人")
    private String consignorName;
	/**发货人编号*/
	@Excel(name = "发货人编号", width = 15)
    @Schema(description = "发货人编号")
    private String consignorId;
	/**发货地区*/
    @Excel(name = "发货地区", width = 15,exportConvert=true,importConvert = true )
    @Schema(description = "发货地区")
    private String consignorAreaCode;

    public String convertisConsignorAreaCode() {
        return SpringContextUtils.getBean(ProvinceCityArea.class).getText(consignorAreaCode);
    }

    public void convertsetConsignorAreaCode(String text) {
        this.consignorAreaCode = SpringContextUtils.getBean(ProvinceCityArea.class).getCode(text);
    }
	/**发货地址*/
	@Excel(name = "发货地址", width = 15)
    @Schema(description = "发货地址")
    private String consignorAddress;
	/**收货人*/
	@Excel(name = "收货人", width = 15)
    @Schema(description = "收货人")
    private String consigneeName;
	/**收货人编号*/
	@Excel(name = "收货人编号", width = 15)
    @Schema(description = "收货人编号")
    private String consigneeId;
	/**收货地区*/
    @Excel(name = "收货地区", width = 15,exportConvert=true,importConvert = true )
    @Schema(description = "收货地区")
    private String consigneeAreaCode;

    public String convertisConsigneeAreaCode() {
        return SpringContextUtils.getBean(ProvinceCityArea.class).getText(consigneeAreaCode);
    }

    public void convertsetConsigneeAreaCode(String text) {
        this.consigneeAreaCode = SpringContextUtils.getBean(ProvinceCityArea.class).getCode(text);
    }
	/**收货地址*/
	@Excel(name = "收货地址", width = 15)
    @Schema(description = "收货地址")
    private String consigneeAddress;
	/**付款方式*/
	@Excel(name = "付款方式", width = 15, dicCode = "lttl_payment_way")
    @Dict(dicCode = "lttl_payment_way")
    @Schema(description = "付款方式")
    private String paymentTypeId;
	/**回单要求*/
	@Excel(name = "回单要求", width = 15, dicCode = "lttl_receipt_type")
    @Dict(dicCode = "lttl_receipt_type")
    @Schema(description = "回单要求")
    private String receiptTypeId;
	/**时效（天）*/
	@Excel(name = "时效（天）", width = 15)
    @Schema(description = "时效（天）")
    private Integer timeLimit;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @Schema(description = "备注")
    private String remark;
}
