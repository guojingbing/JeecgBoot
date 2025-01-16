package org.jeecg.modules.cust.common.entity;

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
 * @Description: 帮助文档表
 * @Author:
 * @Date:   2024-03-27
 * @Version: V1.0
 */
@Data
@TableName("sys_help_document")
public class SysHelpDocument implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    private String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
    private String sysOrgCode;
	/**类型*/
	@Excel(name = "类型", width = 15)
    private String docTypeId;
	/**名称*/
	@Excel(name = "名称", width = 15)
    private String docTypeName;
	/**代码*/
	@Excel(name = "代码", width = 15)
    private String docCode;
	/**版本*/
	@Excel(name = "版本", width = 15)
    private String docVersion;
	/**简介*/
	@Excel(name = "简介", width = 15)
    private String docSummary;
	/**内容*/
	@Excel(name = "内容", width = 15)
    private String content;
	/**状态*/
	@Excel(name = "状态", width = 15)
    private String docStatus;
	/**状态时间*/
	@Excel(name = "状态时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date docStatusTime;
}
