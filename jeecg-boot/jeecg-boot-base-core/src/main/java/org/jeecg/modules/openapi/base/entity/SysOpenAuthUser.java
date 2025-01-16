package org.jeecg.modules.openapi.base.entity;

import org.jeecg.modules.openapi.base.enums.SysOpenAuthUserTypeEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 三方授权机构管理
 * @Author: jeecg-boot
 * @Date: 2022-01-05
 * @Version: V1.0
 */
@Data
@TableName("sys_open_auth_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "sys_open_auth_user对象", description = "三方授权机构管理")
public class SysOpenAuthUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 机构代码
     */
    @Excel(name = "机构代码", width = 15)
    @ApiModelProperty(value = "机构代码")
    private String corpCode;
    /**
     * 机构名称
     */
    @Excel(name = "机构名称", width = 15)
    @ApiModelProperty(value = "机构名称")
    private String corpName;
    /**
     * 机构全称
     */
    @Excel(name = "机构全称", width = 15)
    @ApiModelProperty(value = "机构全称")
    private String corpFullName;
    /**
     * 类型
     */
    @Excel(name = "类型", width = 15, dicCode = "busi_open_auth_type")
    @Dict(dicCode = "busi_open_auth_type")
    @ApiModelProperty(value = "类型")
    private Integer type;
    /**
     * 地址
     */
    @Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 简介
     */
    @Excel(name = "简介", width = 15)
    @ApiModelProperty(value = "简介")
    private String corpDesc;
    /**
     * 授权key
     */
    @Excel(name = "授权key", width = 15)
    @ApiModelProperty(value = "授权key")
    private String apiKey;
    /**
     * 授权秘钥
     */
    @Excel(name = "授权秘钥", width = 15)
    @ApiModelProperty(value = "授权秘钥")
    private String apiSecret;
    /**
     * 限流频率
     */
    @Excel(name = "限流频率", width = 15)
    @ApiModelProperty(value = "限流频率")
    private Integer reqFreq;
    @Excel(name = "接口访问根路径", width = 15)
    @ApiModelProperty(value = "接口访问根路径")
    private String apiRootUrl;
    @Excel(name = "IP白名单", width = 15)
    @ApiModelProperty(value = "IP白名单")
    private String ipRange;
    /**
     * 授权状态
     */
    @Excel(name = "授权状态", width = 15, dicCode = "sys_open_oauth_user_status")
    @Dict(dicCode = "sys_open_oauth_user_status")
    @ApiModelProperty(value = "授权状态")
    private Integer authStatus;
    /**
     * 调用判图软件API key
     */
    @Excel(name = "调用判图软件API key", width = 15)
    @ApiModelProperty(value = "调用判图软件API key")
    private String callApiKey;
    /**
     * 调用判图软件API secret
     */
    @Excel(name = "调用判图软件API secret", width = 15)
    @ApiModelProperty(value = "调用判图软件API secret")
    private String callApiSecret;
    /**
     * 是否启用接口对接
     */
    @Excel(name = "是否启用接口对接", width = 15)
    @ApiModelProperty(value = "是否启用接口对接")
    private Boolean isApiUse;
    /**
     * 判图软件对应的机构编号
     */
    @Excel(name = "判图软件机构编号", width = 15)
    @ApiModelProperty(value = "判图软件机构编号")
    private Integer ecgCompanyId;

    /**
     * 平台部门主键
     */
    @Excel(name = "平台部门主键", width = 15)
    @Dict(dictTable = "sys_depart", dicText = "full_depart_name", dicCode = "id")
    @ApiModelProperty(value = "平台部门主键")
    private String rhcDeptId;

    public Result checkParam(SysOpenAuthUser sysOpenAuthUser) {
        if (StringUtils.isBlank(sysOpenAuthUser.getCorpCode())) {
            return Result.error("请填写机构代码！");
        }
        if (StringUtils.isBlank(sysOpenAuthUser.getCorpName())) {
            return Result.error("请填写机构名称！");
        }
        if (null == sysOpenAuthUser.getType()) {
            return Result.error("请选择机构类型！");
        }
        if (sysOpenAuthUser.getIsApiUse()) {
            if (StringUtils.isBlank(sysOpenAuthUser.getApiRootUrl())) {
                return Result.error("请填写接口地址！");
            }
            if (StringUtils.isBlank(sysOpenAuthUser.getCallApiKey())) {
                return Result.error("请填写接口调用KEY！");
            }
            if (StringUtils.isBlank(sysOpenAuthUser.getCallApiSecret())) {
                return Result.error("请填写接口调用秘钥！");
            }
        }
        if (SysOpenAuthUserTypeEnum.HOSP.getCode() == sysOpenAuthUser.getType()
                && StringUtils.isBlank(sysOpenAuthUser.getRhcDeptId())) {
            return Result.error("请设置平台部门！");
        }
        return null;
    }
}
