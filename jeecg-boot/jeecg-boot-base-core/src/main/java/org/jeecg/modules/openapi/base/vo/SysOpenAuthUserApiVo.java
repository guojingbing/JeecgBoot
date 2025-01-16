package org.jeecg.modules.openapi.base.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description: 接口地址配置表
 * @Author: jeecg-boot
 * @Date:   2022-01-05
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysOpenAuthUserApiVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**授权机构主键*/
    @ApiModelProperty(value = "授权机构主键")
    private String id;
	/**根路径*/
    @ApiModelProperty(value = "根路径")
    private String apiRootUrl;
	/**接口代码*/
    @ApiModelProperty(value = "接口代码")
    private List<Map> apiList;

}
