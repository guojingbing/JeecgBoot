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
 * @Description: 微信用户
 * @Author:
 * @Date:   2020-02-18
 * @Version: V1.0
 */
@Data
@TableName("nshare_user")
public class WxUser implements Serializable {
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
	/**用户名*/
	@Excel(name = "用户名", width = 15)
    private String userName;
	/**真实姓名*/
	@Excel(name = "真实姓名", width = 15)
    private String realName;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
    private String phone;
	@Excel(name = "身份证号", width = 20)
	private String idNumber;
	@Excel(name = "出生日期", width = 16, format = "yyyy-MM-dd")
	private String birthDate;
	/**用户类型*/
	@Excel(name = "用户类型", width = 15)
    private String userType;
	/**性别*/
	@Excel(name = "性别", width = 15)
    private String gender;
	@Excel(name = "身高", width = 15)
	private Double height;
	@Excel(name = "体重", width = 15)
	private Double weight;
	/**区域*/
	@Excel(name = "区域", width = 15)
	private String district;
	/**城市*/
	@Excel(name = "城市", width = 15)
    private String city;
	/**省份*/
	@Excel(name = "省份", width = 15)
    private String province;
	/**国家*/
	@Excel(name = "国家", width = 15)
    private String country;
	@Excel(name = "详细地址", width = 64)
	private String address;
	/**头像地址*/
	@Excel(name = "头像地址", width = 15)
    private String avatarUrl;
	/**微信openid*/
	@Excel(name = "微信openid", width = 15)
    private String openid;
	/**unionid*/
	@Excel(name = "unionid", width = 15)
    private String unionid;
	/**用户编码*/
	@Excel(name = "用户编码", width = 15)
	private String userCode;
	/**是否使用*/
	@Excel(name = "是否使用", width = 15)
    private String isUse;
	@Excel(name = "外接用户编号", width = 15)
	private String fkUserId;
	@Excel(name = "appid", width = 15)
	private String appid;
	/**绑定状态*/
	@Excel(name = "绑定状态", width = 10)
	private String bindStatus;
}
