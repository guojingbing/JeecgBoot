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
 * @Description: 证券复权因子
 * @Author:
 * @Date:   2020-03-07
 * @Version: V1.0
 */
@Data
@TableName("stock_adj_factor")
public class StockAdjFactor implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.AUTO)
	private Long id;
	/**证券代码*/
	@Excel(name = "证券代码", width = 15)
    private String code;

	@Excel(name = "交易日期", width = 20, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date tradeDate;

	@Excel(name = "复权因子", width = 11)
	private Double adjFactor;
}
