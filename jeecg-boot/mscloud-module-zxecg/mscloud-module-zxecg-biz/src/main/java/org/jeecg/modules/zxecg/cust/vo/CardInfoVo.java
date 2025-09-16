package org.jeecg.modules.zxecg.cust.vo;

import lombok.Data;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/26
 * @description 卡券信息
 */

@Data
public class CardInfoVo {
    private Long cardId;
    private String cardNo;
    private Long catId;
    private Long auditCompanyId;
    private String agentName;
    private String cardTypeName;
    private String repCompanyName;
    private String rechargeStatus;


}
