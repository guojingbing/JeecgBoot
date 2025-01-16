package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 行政区域代码表
 * <p>
 */
@Data
@TableName("sys_comm_area")
public class SysCommArea implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
    @TableId(type = IdType.AUTO)
    private Long areaCode;
	/**区域名称*/
	@Excel(name="区域名称",width=15)
	private String areaName;
	/**级别1-5,省市县镇村*/
	@Excel(name="级别1-5,省市县镇村",width=15)
	private Integer level;
	/**父级区划代码*/
	@Excel(name="父级区划代码",width=15)
	private Long pcode;
	/**城乡分类：
	 111 表示主城区
	 112 表示城乡结合区
	 121 表示镇中心区
	 122 表示镇乡结合区
	 123 表示特殊区域
	 210 表示乡中心区
	 220 表示村庄*/
	@Excel(name="城乡分类",width=15)
	private Integer category;
}
