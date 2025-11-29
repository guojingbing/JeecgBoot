package org.jeecg.modules.zxecg.cust.dto;

import lombok.Data;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 */

@Data
public class CustUserInfoDTO {
    private Long userId;
    private String userName;
    private String userTel;
    private Integer userStatus;
}
