package org.jeecg.modules.zxecg.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgRec;

import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/29
 */


public interface CustUserEcgRecMapper extends BaseMapper<CustUserEcgRec> {
    List<Map> loadListPaging(Page<Map> pageList, Long loginUserId, Map<String, Object> likeMap, String column, String order);

    List<CustUserEcgRec> getEcgListByRepId(Long repId);

    Map<String, Object> getDurationByEcgIds(List<Long> ecgIdList);
}
