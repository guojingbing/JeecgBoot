package org.jeecg.modules.zxecg.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.modules.zxecg.system.entity.CommCompany;
import org.jeecg.modules.zxecg.system.mapper.CommCompanyMapper;
import org.jeecg.modules.zxecg.system.service.ICommCompanyService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/15
 * @description
 */

@Service
public class CommCompanyServiceImpl extends ServiceImpl<CommCompanyMapper, CommCompany> implements ICommCompanyService {
    @Override
    public List getTreeList(long loginUserId) {
        List<Map<String, Object>> companyList = this.baseMapper.getListByUserId(loginUserId);
        if (CollectionUtils.isEmpty(companyList)) {
            return null;
        }
        for (Map<String, Object> map : companyList) {
            Object cid = map.get("companyId");
            map.put("id", cid);
            map.put("text", map.get("companyName"));
            map.put("state", "closed");
        }
        //todo 未完成

        return companyList;
    }
}
