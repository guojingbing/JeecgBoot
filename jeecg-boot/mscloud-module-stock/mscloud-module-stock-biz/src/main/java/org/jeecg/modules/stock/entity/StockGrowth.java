package org.jeecg.modules.stock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 季频成长能力
 * @Description  
 * @Author  Kingpin
 * @Date 2020-08-07 14:00:42 
 */
@Data
@TableName("stock_growth")
public class StockGrowth implements Serializable {
	private static final long serialVersionUID =  5796740097295395448L;

	@TableId(type = IdType.AUTO)
	private Long id;

	@Excel(name = "证券代码", width = 20)
	private String code;

	/**
	 * 年度
	 */
	@Excel(name = "年度", width = 10)
	private Integer year;

	/**
	 * 季度
	 */
	@Excel(name = "季度", width = 10)
	private Integer quarter;

	/**
	 * 公司发布财报的日期	
	 */
	@Excel(name = "发布日期", width = 20, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date pubDate;

	/**
	 * 财报统计的季度的最后一天, 比如2017-03-31, 2017-06-30	
	 */
	@Excel(name = "财报截止日期", width = 20, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date statDate;

	@Excel(name = "净资产同比增长率", width = 10)
	private Double yoyEquity;

	@Excel(name = "总资产同比增长率", width = 10)
	private Double yoyAsset;

	@Excel(name = "净利润同比增长率", width = 10)
	private Double yoyNi;

	@Excel(name = "基本每股收益同比增长率", width = 10)
	private Double yoyEpsBasic;

	@Excel(name = "归属母公司股东净利润同比增长率", width = 10)
	private Double yoyPni;
}
