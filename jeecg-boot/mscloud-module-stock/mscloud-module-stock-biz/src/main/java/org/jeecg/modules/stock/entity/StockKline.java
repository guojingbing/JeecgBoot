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
 * 日频估值指标
 * @Description  
 * @Author  Kingpin
 * @Date 2020-08-07 14:00:42 
 */
@Data
@TableName("stock_kline")
public class StockKline implements Serializable {
	private static final long serialVersionUID =  5796740097295395448L;

	@TableId(type = IdType.AUTO)
	private Long id;

	@Excel(name = "证券代码", width = 20)
	private String code;

	/**
	 * 日期
	 */
	@Excel(name = "日期", width = 20, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date date;

	/**
	 * 今开盘价格
	 */
	@Excel(name = "今开盘价格", width = 11)
	private Double open;

	/**
	 * 最高价
	 */
	@Excel(name = "最高价", width = 11)
	private Double high;

	/**
	 * 最低价
	 */
	@Excel(name = "最低价", width = 11)
	private Double low;

	/**
	 * 今收盘价
	 */
	@Excel(name = "今收盘价", width = 11)
	private Double dayClose;

	/**
	 * 昨日收盘价
	 */
	@Excel(name = "昨日收盘价", width = 11)
	private Double preClose;

	/**
	 * 成交数量
	 */
	@Excel(name = "成交数量", width = 11)
	private Double volume;

	/**
	 * 成交金额
	 */
	@Excel(name = "成交金额", width = 11)
	private Double amount;

	/**
	 * 换手率
	 */
	@Excel(name = "换手率", width = 11)
	private Double turn;

	/**
	 * 涨跌幅
	 */
	@Excel(name = "涨跌幅", width = 11)
	private Double pctChg;

	/**
	 * 滚动市盈率
	 */
	@Excel(name = "滚动市盈率", width = 11)
	private Double peTtm;

	/**
	 * 滚动市销率
	 */
	@Excel(name = "滚动市销率", width = 11)
	private Double psTtm;

	/**
	 * 滚动市现率
	 */
	@Excel(name = "滚动市现率", width = 11)
	private Double pcfNcfTtm;

	/**
	 * 市净率
	 */
	@Excel(name = "市净率", width = 11)
	private Double pbMrq;

	/**
	 * 复权状态
	 */
	@Excel(name = "复权状态", width = 11)
	private Short adjustFlag;

	/**
	 * 交易状态
	 */
	@Excel(name = "交易状态", width = 11)
	private Short tradeStatus;

	/**
	 * 是否ST
	 */
	@Excel(name = "是否ST", width = 11)
	private Short isSt;

	/**
	 * K线类型
	 */
	@Excel(name = "K线类型", width = 10)
	private String frequency;

	@Excel(name = "5日线", width = 10)
	private Double vMa5;

	@Excel(name = "10日线", width = 10)
	private Double vMa10;

	@Excel(name = "20日线", width = 10)
	private Double vMa20;

	@Excel(name = "30日线", width = 10)
	private Double vMa30;

	@Excel(name = "60日线", width = 10)
	private Double vMa60;
}
