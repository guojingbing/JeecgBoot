package org.jeecg.modules.iagent.nls.llm;

import java.util.List;
import java.util.Map;

public interface LLMOperater {
    final static String DOUBAO_ROLE_USER="user";
    final static String DOUBAO_ROLE_SYSTEM="system";
    final static String DOUBAO_ROLE_ASSISTANT="assistant";
    final static String DOUBAO_ROLE_FUNCTION="function";
    final static String DOUBAO_ROLE_TOOL="tool";
    /**
     * 同步返回LLM查询结果
     * @param question
     * @return
     * @throws Exception
     */
    String getAnswerSync(String question) throws Exception;

    /**
     * 流式返回LLM查询结果
     * @param userId
     * @param userName
     * @param question
     * @param incrementalOutput 是否流式输出
     * @withHisNum 携带几轮上下文回话，从缓存中获取,custHisList为空时有效
     * @groupFlag 两级分组标记
     * @param custHisList 自定义上下文
     * @return rmap.put("flowable",flowable); rmap.put("service",service);
     * @throws Exception
     */
    Map<String,Object> getElansenLLMAnswerAsync(String userId, String userName, String question, boolean incrementalOutput,int withHisNum,String groupFlag,List<?> custHisList) throws Exception;

    /**
     * 流式返回LLM查询结果
     * @param userId
     * @param userName
     * @param question
     * @param incrementalOutput 是否流式输出
     * @withHisNum 携带几轮上下文回话，从缓存中获取,custHisList为空时有效
     * @groupFlag 两级分组标记
     * @param custHisList 自定义上下文
     * @return rmap.put("flowable",flowable); rmap.put("service",service);
     * @throws Exception
     */
    Map<String,Object> getAnswerAsync(String userId, String userName, String question, boolean incrementalOutput,int withHisNum,String groupFlag,List<?> custHisList) throws Exception;

    /**
     * 流式返回心电报告解读LLM查询结果
     * @param userId
     * @param userName
     * @param question
     * @param incrementalOutput 是否流式输出
     * @withHisNum 携带几轮上下文回话，从缓存中获取,custHisList为空时有效
     * @groupFlag 两级分组标记
     * @param custHisList 自定义上下文
     * @return rmap.put("flowable",flowable); rmap.put("service",service);
     * @throws Exception
     */
    Map<String,Object> getEcgDiagAnswerAsync(String userId, String userName, String question, boolean incrementalOutput,int withHisNum,String groupFlag,List<?> custHisList) throws Exception;

    /**
     * 流式返回图片理解LLM查询结果
     * @param userId
     * @param userName
     * @param question
     * @param imageUrl
     * @return
     * @throws Exception
     */
    Map<String,Object> getAnswerMultiPartsAsync(String userId, String userName, String question,String imageUrl) throws Exception;

    /**
     * 获取用户LLM聊天记录
     * @param userId
     * @param groupFlag 分组标记
     * @return
     * @throws Exception
     */
    List<?> getLLMChatHis(String userId,String groupFlag);

    /**
     * 缓存聊天记录
     * @param userId
     * @param groupFlag 两级分组标记
     * @param role
     * @param msg
     * @throws Exception
     */
    void setLLMChatHis(String userId,String groupFlag, String role, String msg);
}
