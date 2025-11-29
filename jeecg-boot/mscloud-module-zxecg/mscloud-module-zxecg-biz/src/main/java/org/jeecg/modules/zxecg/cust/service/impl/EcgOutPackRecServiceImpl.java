package org.jeecg.modules.zxecg.cust.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.EcgOutPackRec;
import org.jeecg.modules.zxecg.cust.mapper.EcgOutPackRecMapper;
import org.jeecg.modules.zxecg.cust.service.IEcgOutPackRecService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/27
 * @description ecg批量下载相关操作
 */

@Service
public class EcgOutPackRecServiceImpl extends ServiceImpl<EcgOutPackRecMapper, EcgOutPackRec> implements IEcgOutPackRecService {
    @Override
    public Page<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order) {
        return pageList.setRecords(this.baseMapper.loadListPaging(pageList, loginUserId, likeMap, column, order));
    }
}
