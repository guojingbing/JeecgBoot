package org.jeecg.modules.zxecg.cust.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 * @description 用户设备绑定信息
 */

@Data
public class CustUserMachVo {
    private Long bindingId;
    private Long userId;
    private String machSn;
    private Integer machType;
    private String machMac;
    private String uuid;
    private Integer deviceFlag;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp bindingTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp unbindTime;
    private Integer bindingStatus;
    private Long companyId;
    private Long deptId;
    private String userName;
    private String userTel;
    private Integer status;
    private Integer userStatus;
}
