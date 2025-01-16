package org.jeecg.modules.iagent.nls.llm.impl;

import com.alibaba.xingchen.ApiClient;
import com.alibaba.xingchen.api.ChatApiSub;
import com.alibaba.xingchen.model.*;
import com.alibaba.xingchen.model.ext.chat.ChatContext;
import io.reactivex.Flowable;
import org.jeecg.modules.iagent.nls.llm.LLMOperater;
import org.jeecg.modules.iagent.nls.llm.config.AliyunLLMConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 通义星尘LLM
 * @Author: Kingpin
 * @Date: 2024-12-09 13:38:52
 **/
@Service
public class XingchenLLMOperaterImpl implements LLMOperater {
    @Autowired
    AliyunLLMConfig config;

    @Override
    public String getAnswerSync(String question) throws Exception {
        long st1=System.currentTimeMillis();
        String sseType="disable";
        ApiClient apiClient = config.getApiClient(sseType);
        ChatApiSub api = new ChatApiSub(apiClient);
        System.out.println("初始化API耗时："+(System.currentTimeMillis()-st1));
        if(sseType.equalsIgnoreCase("disable")){
            ResultDTOChatResult result = api.chat(buildChatReqParams("1234","小明","你今年多大?",false));
            System.out.println(result.getData());
            long st2=System.currentTimeMillis();
            System.out.println("非流式输出响应耗时："+(st2-st1));
        }
        return null;
    }

    @Override
    public Flowable getAnswerAsync(String userId,String userName,String question,boolean incrementalOutput) throws Exception {
        String sseType="enable";
        ApiClient apiClient = config.getApiClient(sseType);
        ChatApiSub api = new ChatApiSub(apiClient);
        Flowable<ChatResult> response = api.streamOut(buildChatReqParams(userId,userName,question,incrementalOutput));
        return response;
    }

    @Override
    public List<Object> getLLMChatHis(String userId) {
        return null;
    }

    @Override
    public void setLLMChatHis(String userId, String role, String msg) {

    }

    private ChatReqParams buildChatReqParams(String userId,String userName,String question,boolean incrementalOutput) {
        return ChatReqParams.builder()
                .botProfile(
                        CharacterKey.builder()
                                // 星尘预制角色
                                .characterId(config.getCharacterId())
                                .name(config.getCharacterName())
                                .build()
                )
                .modelParameters(
                        ModelParameters.builder()
                                .seed(1683806810L)
                                .incrementalOutput(incrementalOutput)
                                .build()
                )
                .userProfile(
                        UserProfile.builder()
                                .userId(userId)
                                .build()
                )
                .messages(
                        Arrays.asList(
                                // 注意，自定义角色 prompt，用户问题需放到messages最后一条
                                Message.builder()
                                        .name(userName)
                                        .content(question)
                                        .role("user")
                                        .build()
                        )
                )
                .context(
                        ChatContext.builder()
                                .useChatHistory(true) // 使用平台对话历史，messages 只能包含用户提问消息，不能包含其他信息
                                .build()
                )
                .build();
    }
}
