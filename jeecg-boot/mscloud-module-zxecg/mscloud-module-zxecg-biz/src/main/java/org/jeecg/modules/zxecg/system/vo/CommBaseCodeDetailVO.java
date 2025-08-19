package org.jeecg.modules.zxecg.system.vo;

import lombok.Data;

/**
 * 基础代码VO
 */
@Data
public class CommBaseCodeDetailVO {
    private Long codeId;
    private String typeNo;
    private String codeName;
    private Long orderNum;
    private String codeString;
    private Long codeValue;
    private String extraValue;
    private Boolean isUse;
    private Boolean isSystem;
    private String remark;
}
