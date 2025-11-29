package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportFragStyle;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportFragStyleMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportFragStyleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/11
 */

@Service
public class CustUserEcgReportFragStyleServiceImpl extends ServiceImpl<CustUserEcgReportFragStyleMapper, CustUserEcgReportFragStyle> implements ICustUserEcgReportFragStyleService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void fragStyle(Long repId, Integer categoryId, Integer style) {
        CustUserEcgReportFragStyle fragStyle = this.baseMapper.getByRepIdAndCatId(repId, categoryId);
        if (null == fragStyle) {
            CustUserEcgReportFragStyle newFragStyle = new CustUserEcgReportFragStyle();
            newFragStyle.setRepId(repId);
            newFragStyle.setCategoryId(categoryId);
            newFragStyle.setStyle(style);
            this.save(newFragStyle);
        } else {
            fragStyle.setStyle(style);
            this.updateById(fragStyle);
        }
    }
}
