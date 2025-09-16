package org.jeecg.modules.zxecg.cust.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgShortTerm;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgShortTermEvent;
import org.jeecg.modules.zxecg.cust.mapper.CustUserEcgShortTermMapper;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgShortTermEventService;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgShortTermService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 * @description
 */

@Service
public class CustUserEcgShortTermServiceImpl extends ServiceImpl<CustUserEcgShortTermMapper, CustUserEcgShortTerm> implements ICustUserEcgShortTermService {
    @Resource
    ICustUserEcgShortTermEventService shortTermEventService;

    @Override
    public Page<Map> loadListPaging(Page<Map> page, List<Long> deptIdList, Map<String, Object> likeMap, String column, String order) {
        return page.setRecords(this.baseMapper.loadListPaging(page, deptIdList, likeMap, column, order));
    }

    @Override
    public void resultEdit(CustUserEcgShortTerm shortTerm, JSONArray eventList) {
        //获取登录用户id
        Long loginUserId = 13L;
        Long ecgId = shortTerm.getEcgId();
        //将原来的事件删除，再将所有事件添加进去
        shortTermEventService.deleteEventsByEcgId(ecgId);
        Collection list = new ArrayList();
        //保存事件表
        for (Object o : eventList) {
            JSONObject event = JSONObject.parseObject(JSON.toJSONString(o));
            CustUserEcgShortTermEvent termEvent = new CustUserEcgShortTermEvent();
            termEvent.setEcgId(ecgId);
            termEvent.setEventCode(event.getInteger("eventCode"));
            termEvent.setEventLevel(event.getInteger("eventLevel"));
            termEvent.setEventName(event.getString("eventName"));
            termEvent.setIsConfirm(0);
            Integer src = event.getInteger("src");
            termEvent.setSrc(src);
            if (2 == src) {
                termEvent.setCreateId(loginUserId);
                termEvent.setCreateTime(new Timestamp(System.currentTimeMillis()));
            } else {
                termEvent.setCreateTime(shortTerm.getCreateTime());
            }
            list.add(termEvent);
        }
        shortTermEventService.saveBatch(list);
        //获取上传的事件等级最大的作为筛查诊断结果
        List<Integer> levelList = eventList.stream().map(o -> JSONObject.parseObject(JSON.toJSONString(o)).getInteger("eventLevel")).distinct().collect(Collectors.toList());
        Integer max = Collections.max(levelList);
        shortTerm.setResult(max);
        shortTerm.setLastModifyDate(new Timestamp(System.currentTimeMillis()));
        shortTerm.setLastModifyUserId(loginUserId);
        this.baseMapper.updateById(shortTerm);
    }
}
