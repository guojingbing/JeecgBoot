package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportScreenEvent;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgReportScreenEventMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportScreenEventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/2
 */

@Service
public class CustUserEcgReportScreenEventServiceImpl extends ServiceImpl<CustUserEcgReportScreenEventMapper, Map> implements ICustUserEcgReportScreenEventService {
    @Override
    public Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order) {
        List<Map> maps = this.baseMapper.loadListPaging(pageList, loginUserId, likeMap, column, order);
        //todo 继续处理数据
        return pageList.setRecords(maps);
    }

    @Override
    public Page<Map> loadNoteListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order) {
        List<Map> maps = this.baseMapper.loadNoteListPaging(pageList, loginUserId, likeMap, column, order);
        //todo 继续处理数据
        return pageList.setRecords(maps);
    }
}
