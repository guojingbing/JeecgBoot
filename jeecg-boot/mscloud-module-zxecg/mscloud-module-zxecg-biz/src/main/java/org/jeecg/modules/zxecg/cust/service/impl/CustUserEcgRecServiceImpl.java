package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgRec;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgRecMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgRecService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/29
 */

@Service
public class CustUserEcgRecServiceImpl extends ServiceImpl<CustUserEcgRecMapper, CustUserEcgRec> implements ICustUserEcgRecService {

    @Override
    public Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadListPaging(pageList, loginUserId, likeMap, column, order));
    }

    @Override
    public List<CustUserEcgRec> getEcgListByRepId(Long repId) {
        return this.baseMapper.getEcgListByRepId(repId);
    }


}
