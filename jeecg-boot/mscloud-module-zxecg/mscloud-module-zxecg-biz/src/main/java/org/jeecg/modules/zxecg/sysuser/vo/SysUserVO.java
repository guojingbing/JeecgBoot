package org.jeecg.modules.zxecg.sysuser.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SysUserVO {
    private Long userId;
    private String userNo;
    private String userName;
    private String phone;
    private Integer userTypeId;
    private Long companyId;
    private String companyName;
    private Long deptId;
    private String deptName;
    private String userAvatarAddr;
    private String userSignPic;
    private Integer psdModified;
    private String password;
    private boolean isUse;
}
