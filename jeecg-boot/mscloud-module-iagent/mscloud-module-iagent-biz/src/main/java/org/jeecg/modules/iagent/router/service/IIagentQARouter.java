package org.jeecg.modules.iagent.router.service;

import java.util.Map;

/**
 * @Description: 智能体问答分析，确定触发自定义路由还是LLM
 * @Author: Kingpin
 * @Date: 2024-12-17 17:35:55
 **/
public interface IIagentQARouter {
    /**
     * 判断问题触发的，答案路由，以及解析路由参数
     * @param question
     * @param userId
     * @return
     */
    Map getQaRouter(String question,String userId);
}
