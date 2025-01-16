package org.jeecg.modules.iagent.nls.llm;

import io.reactivex.Flowable;

import java.util.List;

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
     * @param incrementalOutput
     * @return
     * @throws Exception
     */
    Flowable getAnswerAsync(String userId,String userName,String question,boolean incrementalOutput) throws Exception;

    /**
     * 获取用户LLM聊天记录
     * @param userId
     * @return
     * @throws Exception
     */
    List<?> getLLMChatHis(String userId);

    /**
     * 缓存聊天记录
     * @param userId
     * @param role
     * @param msg
     * @throws Exception
     */
    void setLLMChatHis(String userId, String role, String msg);
}
