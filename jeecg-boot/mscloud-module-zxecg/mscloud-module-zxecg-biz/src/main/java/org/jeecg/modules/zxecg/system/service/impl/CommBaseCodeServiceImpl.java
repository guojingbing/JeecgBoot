package org.jeecg.modules.zxecg.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.system.mapper.CommBaseCodeMapper;
import org.jeecg.modules.zxecg.system.service.ICommBaseCodeService;
import org.jeecg.modules.zxecg.system.vo.CommBaseCodeDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试Service
 */
@Service
public class CommBaseCodeServiceImpl extends ServiceImpl<CommBaseCodeMapper, CommBaseCodeDetailVO> implements ICommBaseCodeService {
    @Autowired
    private CommBaseCodeMapper commBaseCodeMapper;

    @Override
    public Map<String, CommBaseCodeDetailVO> getCodeDetailsByCodeStrings(String typeNo, List<String> codeStrings) {
        List<CommBaseCodeDetailVO> list=commBaseCodeMapper.getCodeDetailsByCodeStrings(typeNo.toUpperCase(), codeStrings);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        Map<String, CommBaseCodeDetailVO> map=new HashMap<>();
        for(CommBaseCodeDetailVO vo:list){
            map.put(vo.getCodeString(), vo);
        }
        return map;
    }

    @Override
    public Map<String, CommBaseCodeDetailVO> getCodeDetailsByCodeNames(String typeNo, List<String> codeNames) {
        List<CommBaseCodeDetailVO> list=commBaseCodeMapper.getCodeDetailsByCodeNames(typeNo.toUpperCase(), codeNames);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        Map<String, CommBaseCodeDetailVO> map=new HashMap<>();
        for(CommBaseCodeDetailVO vo:list){
            map.put(vo.getCodeName(), vo);
        }
        return map;
    }
}
