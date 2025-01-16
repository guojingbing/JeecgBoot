package org.jeecg.modules.cust.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.cust.common.mapper.MpCommonMapper;
import org.jeecg.modules.cust.common.service.IMpCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 小程序通用业务
 */
@Service
public class MpCommonServiceImpl extends ServiceImpl<MpCommonMapper, String> implements IMpCommonService {
    @Autowired
    private MpCommonMapper mapper;

    @Override
    public List<Map<String, Object>> getDictItemsByDictCode(String dictCode, String itemCode){
        List<Map<String, Object>> list = mapper.selectDictItemsByDictCode(dictCode);
        if(StringUtils.isNotBlank(itemCode)){
            List<Map<String, Object>> subList=list.stream().filter(o->o.get("item_code")!=null&&o.get("item_code").toString().equalsIgnoreCase(itemCode)).collect(Collectors.toList());
            return subList;
        }
        return list;
    }
}
