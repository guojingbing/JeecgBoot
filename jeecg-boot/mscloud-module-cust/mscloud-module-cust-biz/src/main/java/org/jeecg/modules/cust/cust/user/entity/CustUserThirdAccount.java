package org.jeecg.modules.cust.cust.user.entity;

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
 * @Description: 用户三方登录账号绑定
 * @Author:
 * @Date:   2020-02-18
 * @Version: V1.0
 */
@Data
@TableName("cust_user_third_account")
public class CustUserThirdAccount implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    private String id;
	@Excel(name = "用户编号", width = 15)
	private String userId;
	@Excel(name = "昵称", width = 15)
	private String nickName;
	@Excel(name = "头像", width = 15)
	private String avatar;
	@Excel(name = "第三方账号唯一识别", width = 15)
	private String thirdUserUuid;
	@Excel(name = "第三方账号", width = 15)
	private String thirdUserId;
	@Excel(name = "三方账号来源", width = 15)
	private String thirdType;
	@Excel(name = "绑定状态", width = 15)
	private Integer bindStatus;
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
}
