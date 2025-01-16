package org.jeecg.modules.openapi.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 接口地址配置表
 * @Author: jeecg-boot
 * @Date:   2022-01-05
 * @Version: V1.0
 */
@Data
@TableName("sys_open_auth_user_api")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sys_open_auth_user_api对象", description="接口地址配置表")
public class SysOpenAuthUserApi implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**授权机构主键*/
    @ApiModelProperty(value = "授权机构主键")
    private String authId;
	/**接口代码*/
	@Excel(name = "接口代码", width = 15, dicCode = "sys_open_auth_user_api_code")
	@Dict(dicCode = "sys_open_auth_user_api_code")
    @ApiModelProperty(value = "接口代码")
    private Integer apiCode;

	/**地址*/
	@Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private String apiUri;
    @ApiModelProperty(value = "接口名称")
	@TableField(exist = false)
	private String apiName;
}
