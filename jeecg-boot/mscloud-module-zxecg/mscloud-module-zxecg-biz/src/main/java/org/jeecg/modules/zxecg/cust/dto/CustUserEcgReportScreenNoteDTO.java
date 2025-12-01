package org.jeecg.modules.zxecg.cust.dto;

import lombok.Data;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/3
 */

@Data
public class CustUserEcgReportScreenNoteDTO {
    private Long repId;
    private Long noteId;
    private Long templateId;
    private String templateDesc;
    private String note;
}
