package org.jeecg.modules.zxecg.cust.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.zxecg.cust.dto.CustUserEcgReportScreenNoteDTO;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReport;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportScreenNote;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportScreenNoteMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportScreenNoteService;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportService;
import org.jeecg.modules.zxecg.system.service.ICommBaseCodeService;
import org.jeecg.modules.zxecg.system.vo.CommBaseCodeDetailVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/3
 */

@Service
public class CustUserEcgReportScreenNoteServiceImpl extends ServiceImpl<CustUserEcgReportScreenNoteMapper, CustUserEcgReportScreenNote> implements ICustUserEcgReportScreenNoteService {
    @Resource
    ICommBaseCodeService baseCodeService;
    @Resource
    ICustUserEcgReportService reportService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void noteSubmit(CustUserEcgReportScreenNoteDTO screenNoteDTO, Long loginUserId) {
        Long noteId = screenNoteDTO.getNoteId();
        CustUserEcgReportScreenNote note;
        if (null == noteId) {
            note = new CustUserEcgReportScreenNote();
        } else {
            note = this.getById(noteId);
        }
        Long repId = screenNoteDTO.getRepId();
        Long templateId = screenNoteDTO.getTemplateId();
        String templateDesc = screenNoteDTO.getTemplateDesc();
        String screenNote = screenNoteDTO.getNote();
        if (null == templateId) {
            templateDesc = StringUtils.EMPTY;
            screenNote = StringUtils.EMPTY;
        } else {
            if (StringUtils.isBlank(templateDesc)) {
                Map<Long, CommBaseCodeDetailVO> details = baseCodeService.getCodeDetailsByTypeNo("KB101");
                if (null != details && !details.isEmpty()) {
                    CommBaseCodeDetailVO commBaseCodeDetailVO = details.get(templateId);
                    if (null != commBaseCodeDetailVO) {
                        templateDesc = commBaseCodeDetailVO.getRemark();
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(templateDesc) && templateDesc.contains("$")) {
            CustUserEcgReport report = reportService.getById(repId);
            templateDesc = templateDesc.replace("$", DateUtil.format(report.getReportDate(), "yyyy-MM-dd"));
        }
        note.setRepId(repId);
        note.setTemplateId(templateId);
        note.setTemplateDesc(templateDesc);
        note.setNote(screenNote);
        note.setLastModifyUserId(loginUserId);
        note.setLastModifyDate(new Timestamp(System.currentTimeMillis()));
        this.saveOrUpdate(note);
    }
}
