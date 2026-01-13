package org.jeecg.modules.lttl.base.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
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
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 物流网点表
 * @Author: jeecg-boot
 * @Date:   2025-12-26
 * @Version: V1.0
 */
@Data
@TableName("lttl_base_station")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="物流网点表")
public class LttlBaseStation implements Serializable {
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
	/**网点代码*/
	@Excel(name = "网点代码", width = 15)
    @Schema(description = "网点代码")
    private String stationCode;
	/**网点名称*/
	@Excel(name = "网点名称", width = 15)
    @Schema(description = "网点名称")
    private String stationName;
	/**地区*/
    @Excel(name = "地区", width = 15,exportConvert=true,importConvert = true )
    @Schema(description = "地区")
    private String areaCode;

    public String convertisAreaCode() {
        return SpringContextUtils.getBean(ProvinceCityArea.class).getText(areaCode);
    }

    public void convertsetAreaCode(String text) {
        this.areaCode = SpringContextUtils.getBean(ProvinceCityArea.class).getCode(text);
    }
	/**详细地址*/
	@Excel(name = "详细地址", width = 15)
    @Schema(description = "详细地址")
    private String address;
	/**经纬度*/
	@Excel(name = "经纬度", width = 15)
    @Schema(description = "经纬度")
    private String latLng;
	/**联系人*/
	@Excel(name = "联系人", width = 15)
    @Schema(description = "联系人")
    private String contactName;
	/**联系电话*/
	@Excel(name = "联系电话", width = 15)
    @Schema(description = "联系电话")
    private String contactTel;
	/**默认到站*/
	@Excel(name = "默认到站", width = 15, dictTable = "lttl_base_station", dicText = "station_name", dicCode = "id")
	@Dict(dictTable = "lttl_base_station", dicText = "station_name", dicCode = "id")
    @Schema(description = "默认到站")
    private String defaultDestStation;
	/**默认中转站*/
	@Excel(name = "默认中转站", width = 15, dictTable = "lttl_base_station", dicText = "station_name", dicCode = "id")
	@Dict(dictTable = "lttl_base_station", dicText = "station_name", dicCode = "id")
    @Schema(description = "默认中转站")
    private String defaultTransferStation;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @Schema(description = "备注")
    private String remark;
}
