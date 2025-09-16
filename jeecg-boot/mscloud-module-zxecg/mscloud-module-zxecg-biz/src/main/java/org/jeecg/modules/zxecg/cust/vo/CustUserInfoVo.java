package org.jeecg.modules.zxecg.cust.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/21
 * @description 用户信息
 */

@Data
public class CustUserInfoVo {
    private Long userId;
    private String userNo;
    private String userName;
    private String userTelRegion;
    private String userTel;
    private String email;
    private Integer userGender;
    private Integer age;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private Double height;
    private Double weight;
    private Long areaId;
    private String address;
    private String userRegion;
    private String userCity;
    private String userProvince;
    private String userCountry;
    private String userAvatar;
    private Integer userStatus;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp regTime;
    private Integer status;
    private String medical;
    private String symptom;
    private String habits;
    private Integer vipLevel;
    private Integer userType;
    private String deviceFlag;

}
