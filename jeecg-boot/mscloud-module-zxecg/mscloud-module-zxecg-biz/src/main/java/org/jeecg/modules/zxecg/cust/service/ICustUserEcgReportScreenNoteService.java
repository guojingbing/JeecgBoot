package org.jeecg.modules.zxecg.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.zxecg.cust.dto.CustUserEcgReportScreenNoteDTO;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportScreenNote;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/3
 */


public interface ICustUserEcgReportScreenNoteService extends IService<CustUserEcgReportScreenNote> {
    void noteSubmit( CustUserEcgReportScreenNoteDTO screenNoteDTO, Long loginUserId);
}
