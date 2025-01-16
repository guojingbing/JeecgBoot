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
 * @Date 2023-12-21 13:16:47 
 */
@Data
@TableName("sys_comm_attach")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "移动端通用附件表", description = "通用附件上传接口")
public class SysCommAttach  implements Serializable {
	private static final long serialVersionUID =  8259176522788038301L;

	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private String id;

	@ApiModelProperty(value = "关联业务" )
	private String bussi;

	@ApiModelProperty(value = "业务单据主键" )
	private String bussiDataId;

   	@ApiModelProperty(value = "备注" )
	private String remark;

	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String createBy;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;

	@ApiModelProperty(value = "修改人")
	private String updateBy;
}
