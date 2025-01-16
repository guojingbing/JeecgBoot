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
 * @Description: 用户轨迹记录
 * @Author:
 * @Date:   2024-04-03
 * @Version: V1.0
 */
@Data
@TableName("cust_user_track")
public class CustUserTrack implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.AUTO)
    private Integer id;
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
	/**用户编号*/
	@Excel(name = "用户编号", width = 15)
    private String userId;
	/**定位纬度*/
	@Excel(name = "定位纬度", width = 15)
    private Double locLat;
	/**定位经度*/
	@Excel(name = "定位经度", width = 15)
    private Double locLng;
	/**地址*/
	@Excel(name = "地址", width = 15)
    private String locAddress;
	/**区域代码*/
	@Excel(name = "区域代码", width = 15)
	private String adcode;
	/**省份*/
	@Excel(name = "省份", width = 15)
	private String province;
	/**市*/
	@Excel(name = "市", width = 15)
	private String city;
	/**区县*/
	@Excel(name = "区县", width = 15)
	private String district;
	/**定位时间*/
	@Excel(name = "定位时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date locTime;
	/**定位来源*/
	@Excel(name = "定位来源", width = 15)
    private String src;
}
