package org.jeecg.modules.iagent.nls.llm.impl;

import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionChunk;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import io.reactivex.Flowable;
import org.jeecg.common.util.SysRedisUtil;
import org.jeecg.modules.iagent.nls.llm.LLMOperater;
import org.jeecg.modules.iagent.nls.llm.config.AliyunLLMConfig;
import org.jeecg.modules.iagent.nls.llm.config.DoubaoLLMConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 通义星尘LLM
 * @Author: Kingpin
 * @Date: 2024-12-09 13:38:52
 **/
@Service
public class DoubaoLLMOperaterImpl implements LLMOperater {
    @Autowired
    DoubaoLLMConfig config;
    @Autowired
    private SysRedisUtil sysRedisUtil;

    @Override
    public String getAnswerSync(String question) throws Exception {
//        long st1=System.currentTimeMillis();
//        String sseType="disable";
//        ApiClient apiClient = config.getApiClient(sseType);
//        ChatApiSub api = new ChatApiSub(apiClient);
//        System.out.println("初始化API耗时："+(System.currentTimeMillis()-st1));
//        if(sseType.equalsIgnoreCase("disable")){
//            ResultDTOChatResult result = api.chat(buildChatReqParams("1234","小明","你今年多大?",false));
//            System.out.println(result.getData());
//            long st2=System.currentTimeMillis();
//            System.out.println("非流式输出响应耗时："+(st2-st1));
//        }
        return null;
    }

    @Override
    public Flowable getAnswerAsync(String userId,String userName,String question,boolean incrementalOutput) throws Exception {
        String apiKey = config.getApiKey()==null?"7ef00bf7-cf18-4284-af27-4ee5f1c8669c":config.getApiKey();
        ArkService service = ArkService.builder()
                .apiKey(apiKey)
                .baseUrl(config.getUrl()==null?"https://ark.cn-beijing.volces.com/api/v3":config.getUrl())
                .build();
        Flowable<BotChatCompletionChunk> flowable=service.streamBotChatCompletion(buildChatReqParams(userId,question))
                .doOnError(Throwable::printStackTrace);
        // shutdown service
//        service.shutdownExecutor();
        return flowable;
    }

    @Override
    public List<ChatMessage> getLLMChatHis(String userId) {
        //从缓存获取用户历史聊天记录
        String key=sysRedisUtil.getLLMDoubaoMessageHis(userId);
        List<ChatMessage> hisMsgList=(List<ChatMessage>)sysRedisUtil.get(key);
        return hisMsgList;
    }

    @Override
    public void setLLMChatHis(String userId, String role, String msg) {
        //从缓存获取用户历史聊天记录
        String key=sysRedisUtil.getLLMDoubaoMessageHis(userId);
        List<ChatMessage> hisMsgList=(List<ChatMessage>)sysRedisUtil.get(key);
        if(hisMsgList==null){
            hisMsgList=new ArrayList<>();
        }

        ChatMessageRole cmRole=null;
        if(role.equalsIgnoreCase(DOUBAO_ROLE_USER)){
            cmRole=ChatMessageRole.USER;
        }else if(role.equalsIgnoreCase(DOUBAO_ROLE_SYSTEM)){
            cmRole=ChatMessageRole.SYSTEM;
        } if(role.equalsIgnoreCase(DOUBAO_ROLE_ASSISTANT)){
            cmRole=ChatMessageRole.ASSISTANT;
        } if(role.equalsIgnoreCase(DOUBAO_ROLE_FUNCTION)){
            cmRole=ChatMessageRole.FUNCTION;
        }if(role.equalsIgnoreCase(DOUBAO_ROLE_TOOL)){
            cmRole=ChatMessageRole.TOOL;
        }

        ChatMessage chatMessage = ChatMessage.builder().role(cmRole).content(msg).build();

        hisMsgList.add(chatMessage);
        int chatMemTimes=100;
        if(hisMsgList.size()>chatMemTimes){
            sysRedisUtil.set(key,new ArrayList<>(hisMsgList.subList(hisMsgList.size()-chatMemTimes,hisMsgList.size())));
        }else{
            sysRedisUtil.set(key,hisMsgList);
        }
    }

    private BotChatCompletionRequest buildChatReqParams(String userId,String question) {
        final List<ChatMessage> streamMessages = new ArrayList<>();
//        final ChatMessage streamSystemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你是豆包，是由字节跳动开发的 AI 人工智能助手").build();
//        streamMessages.add(streamSystemMessage);

        //从缓存获取用户历史聊天记录
        List<ChatMessage> hisMsgList=this.getLLMChatHis(userId);
        if(!CollectionUtils.isEmpty(hisMsgList)){
            for(ChatMessage msg:hisMsgList){
                streamMessages.add(msg);
            }
        }

        final ChatMessage streamUserMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(question).build();
        streamMessages.add(streamUserMessage);

        //新聊天加入缓存
        this.setLLMChatHis(userId,"user",question);

        return BotChatCompletionRequest.builder()
                .botId(config.getCharacterId()==null?"bot-20241215140240-fttjs":config.getCharacterId())
                .messages(streamMessages)
                .build();

    }
}
