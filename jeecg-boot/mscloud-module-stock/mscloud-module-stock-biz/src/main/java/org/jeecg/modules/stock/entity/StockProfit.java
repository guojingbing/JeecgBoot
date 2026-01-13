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
 * 季频盈利能力
 * @Description  
 * @Author  Kingpin
 * @Date 2020-08-07 14:00:42 
 */
@Data
@TableName("stock_profit")
public class StockProfit  implements Serializable {
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

	/**
	 * 净资产收益率(平均)(%)
	 */
	@Excel(name = "净资产收益率", width = 10)
	private Double roeAvg;

	/**
	 * 销售净利率(%)	
	 */
	@Excel(name = "净资产收益率", width = 10)
	private Double npMargin;

	/**
	 * 销售毛利率(%)	
	 */
	@Excel(name = "净资产收益率", width = 10)
	private Double gpMargin;

	/**
	 * 净利润(元)	
	 */
	@Excel(name = "净利润", width = 10)
	private Double netProfit;

	/**
	 * 每股收益	
	 */
	@Excel(name = "每股收益", width = 10)
	private Double epsTtm;

	/**
	 * 主营营业收入(元)	
	 */
	@Excel(name = "主营营业收入", width = 10)
	private Double mbRevenue;

	/**
	 * 总股本	
	 */
	@Excel(name = "总股本", width = 10)
	private Double totalShare;

	/**
	 * 流通股本	
	 */
	@Excel(name = "流通股本", width = 10)
	private Double liqaShare;

}
