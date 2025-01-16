package org.jeecg.modules.cust.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  
 * @Author  Kingpin
 * @Date 2023-12-21 19:52:23 
 */
@Data
@TableName("sys_comm_attach_item")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "通用附件子表", description = "通用附件子表")
public class SysCommAttachItem  implements Serializable {
	private static final long serialVersionUID =  4784449912406499319L;

	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private String id;

	@ApiModelProperty(value = "主表主键")
	private String attachId;

	@ApiModelProperty(value = "文件相对路径")
	private String filePath;

	@ApiModelProperty(value = "文件名")
	private String fileName;

	@ApiModelProperty(value = "原始文件名称")
	private String origFileName;

	@ApiModelProperty(value = "排序")
	private Integer orderNum;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "创建人")
	private String createBy;

}
