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
 * @Description: 证券基本信息
 * @Author:
 * @Date:   2020-03-07
 * @Version: V1.0
 */
@Data
@TableName("stock_info")
public class StockInfo implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	/**证券代码*/
	@TableId(type = IdType.INPUT)
	@Excel(name = "证券代码", width = 15)
    private String code;
	/**证券名称*/
	@Excel(name = "证券名称", width = 50)
    private String codeName;
	/**证券类型*/
	@Excel(name = "证券类型", width = 4)
    private Short type;
	/**上市状态*/
	@Excel(name = "上市状态", width = 4)
    private Short stockStatus;
	/**上市日期*/
	@Excel(name = "上市日期", width = 20, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date ipoDate;
	/**退市日期*/
	@Excel(name = "退市日期", width = 20, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date outDate;

	@Excel(name = "滚动近一年最高收盘价", width = 11)
	private Double yearHigh;

	@Excel(name = "滚动近一年最低收盘价", width = 11)
	private Double yearLow;

	@Excel(name = "滚动近一年平均收盘价", width = 11)
	private Double yearAvg;

	@Excel(name = "滚动近一年中位收盘价", width = 11)
	private Double yearMid;

	@Excel(name = "最新盈利能力记录编号", width = 11)
	private Long latestPrId;
	@Excel(name = "最新盈利能力发布日期", width = 20, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date prPubDate;

	@Excel(name = "最新业绩快报记录编号", width = 11)
	private Long latestPeId;
	@Excel(name = "最新业绩快报发布日期", width = 20, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date pePubDate;

	@Excel(name = "最新业绩预告记录编号", width = 11)
	private Long latestPfId;
	@Excel(name = "最新业绩预告发布日期", width = 20, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date pfPubDate;
}
