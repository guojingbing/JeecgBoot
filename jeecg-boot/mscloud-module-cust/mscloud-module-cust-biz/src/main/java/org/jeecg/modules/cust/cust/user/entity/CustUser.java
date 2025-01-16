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
 * @Description: 用户业务账号
 * @Author:
 * @Date:   2020-02-18
 * @Version: V1.0
 */
@Data
@TableName("cust_user")
public class CustUser implements Serializable {
    private static final long serialVersionUID = 1L;
    
	@TableId(type = IdType.ASSIGN_ID)
    private String id;
	@Excel(name = "用户账号", width = 15)
	private String userNo;
	@Excel(name = "用户名", width = 15)
	private String userName;
	@Excel(name = "密码", width = 15)
	private String password;
	@Excel(name = "密码加盐", width = 15)
	private String salt;
	@Excel(name = "手机号", width = 15)
    private String phoneNumber;
	@Excel(name = "头像地址", width = 15)
    private String avatar;
	@Excel(name = "省份" )
	private String province;
	@Excel(name = "城市" )
	private String city;
	@Excel(name = "区县" )
	private String district;
	@Excel(name = "信息是否完善", width = 15)
	private Integer infoCompleted;
	@Excel(name = "小程序appid", width = 15)
	private String appid;
	@Excel(name = "创建人", width = 15)
	private String createBy;
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@Excel(name = "更新人", width = 15)
	private String updateBy;
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	@Excel(name = "所属部门", width = 15)
	private String sysOrgCode;
}
