package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * <p>
 * 租户区域授权表
 * <p>
 */
@Data
@TableName("sys_tenant_area")
public class SysTenantArea implements Serializable {
    private static final long serialVersionUID = 1L;
	/**ID*/
    @TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**区域代码*/
	@Excel(name="区域代码",width=15)
    private Long areaCode;
	@Excel(name="租户",width=15)
	private Integer tenantId;
}
