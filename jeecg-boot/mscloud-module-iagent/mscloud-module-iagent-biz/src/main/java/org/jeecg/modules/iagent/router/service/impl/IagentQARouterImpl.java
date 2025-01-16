package org.jeecg.modules.iagent.router.service.impl;

import org.jeecg.modules.iagent.router.constant.IagentRouterConstant;
import org.jeecg.modules.iagent.router.service.IIagentQARouter;
import org.jeecg.modules.iagent.util.RegexUtils;
import org.jeecg.modules.zxecg.service.IZxecgService;
import org.jeecg.modules.zxecg.vo.ZxecgUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 智能体问答分析，确定触发自定义路由还是LLM
 * @Author: Kingpin
 * @Date: 2024-12-17 17:35:55
 **/
@Service
public class IagentQARouterImpl implements IIagentQARouter {
    @Autowired
    private IZxecgService zxecgService;

    @Override
    public Map getQaRouter(String question,String userId){
        Map map=new HashMap();
        map.put("router", IagentRouterConstant.QAROUTER_LLM);//默认LLM
        /*
         *API获取规则，同时满足包含API_ROUTER_REPORT_QUERY_KEYWORDS中的所有关键词
         *示例：
         “查”一下“我”12月25号的“报告”，提取关键词，“查”、“我”、“报告”。
         “查”寻一下“我”的亲友$张三$的$12月25号$的“报告”，提取关键词，“查”、“我”、“报告”。
         * 参数解析：
         * 我、我的亲友，解析用户
         * 12月25号解析报告日期
         */
        boolean apiRouter=true;
        for(String keyword:IagentRouterConstant.API_ROUTER_REPORT_QUERY_KEYWORDS){
            if(!question.contains(keyword)){
                apiRouter=false;
                break;
            }
        }
        if(apiRouter){
            map.put("router",IagentRouterConstant.QAROUTER_API);//匹配API路由规则
            map.put("apiType",IagentRouterConstant.QAROUTER_API_REPORT);//API类型
            //解析报告查询参数
            //解析用户姓名
            Map paramMap=new HashMap();

            //解析用户参数
            if(question.contains("亲友")){
                paramMap.put("repUserType",2);//报告用户类型：2、亲友；1、自己
                String repUserName=null;
                String repUserId=null;
                //从正心数据库查询出用户的所有亲友姓名
                //遍历所有亲友姓名判断问题是否包含
                List<ZxecgUserVo> userList=zxecgService.getUserFriends(userId);
                if(!CollectionUtils.isEmpty(userList)){
                    String questionPinyin=RegexUtils.convertToPinyin(question);
                    for(ZxecgUserVo user:userList){
                        String userName=user.getUserName();
                        String userNamePinyin=RegexUtils.convertToPinyin(userName);
                        if(question.contains(user.getUserName())||questionPinyin.contains(userNamePinyin)){
                            repUserName=user.getUserName();
                            repUserId=user.getUserId();
                            break;
                        }
                    }
                }
                paramMap.put("repUserId",repUserId);
                paramMap.put("repUserName",repUserName);
            }else{
                //从正心数据库查询用户姓名
                String userName=null;
                ZxecgUserVo userInfo=zxecgService.getUserInfo(userId);
                if(userInfo!=null){
                    userName=userInfo.getUserName();
                }
                paramMap.put("repUserType",1);
                paramMap.put("repUserId",userId);
                paramMap.put("repUserName",userName);
            }
            //解析日期参数
            String date=RegexUtils.parseDate(question);
            paramMap.put("repDate",date);
            map.put("params",paramMap);//参数
        }

        //TODO 知识库路由规则，待定
        return map;
    }
}
