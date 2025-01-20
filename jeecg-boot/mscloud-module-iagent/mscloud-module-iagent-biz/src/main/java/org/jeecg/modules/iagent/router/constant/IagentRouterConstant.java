package org.jeecg.modules.iagent.router.constant;

/**
 * @Description: 智能体路由常量定义
 * @Author: Kingpin
 * @Date: 2024-12-17 18:39:31
 **/
public class IagentRouterConstant {
    //智能体问答，回答获取路由，从LLM获取
    public static String QAROUTER_LLM="LLM";
    //智能体问答，回答获取路由，从知识库获取
    public static String QAROUTER_KB="KB";
    //智能体问答，回答获取路由，从API获取
    public static String QAROUTER_API="API";
    //智能体问答，回答获取路由，从API获取报告信息
    public static String QAROUTER_API_REPORT="REP";
    //必须全部满足
    public static String[] API_ROUTER_REPORT_QUERY_KEYWORDS=new String[]{
//            "查",
            "我","报告"
    };
}
