package org.jeecg.modules.iagent.nls.llm.impl;

import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionChunk;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionContentPart;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import io.reactivex.Flowable;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.util.SysRedisUtil;
import org.jeecg.modules.iagent.nls.llm.LLMOperater;
import org.jeecg.modules.iagent.nls.llm.config.DoubaoLLMConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String,Object> getElansenLLMAnswerAsync(String userId,String userName,String question,boolean incrementalOutput,int withHisNum,String groupFlag,List<?> custHisList) throws Exception {
        String apiKey = config.getApiKey()==null?"7ef00bf7-cf18-4284-af27-4ee5f1c8669c":config.getApiKey();
        ArkService service = ArkService.builder()
                .apiKey(apiKey)
                .baseUrl(config.getUrl()==null?"https://ark.cn-beijing.volces.com/api/v3":config.getUrl())
                .build();
        Flowable<?> flowable=null;
        if(StringUtils.isNotBlank(config.getElansenModelId())){
            flowable=service.streamChatCompletion(buildChatReqParams(config.getElansenModelId(),null,userId,groupFlag,question,withHisNum,custHisList))
                    .doOnError(Throwable::printStackTrace);
        }else{
            flowable=service.streamBotChatCompletion((BotChatCompletionRequest)buildChatReqParams(null,config.getElansenCharacterId(),userId,groupFlag,question,withHisNum,custHisList))
                    .doOnError(Throwable::printStackTrace);
        }

        Map rmap=new HashMap();
        rmap.put("flowable",flowable);
        rmap.put("service",service);
        return rmap;
    }

    @Override
    public Map<String,Object> getAnswerAsync(String userId,String userName,String question,boolean incrementalOutput,int withHisNum,String groupFlag,List<?> custHisList) throws Exception {
        String apiKey = config.getApiKey()==null?"7ef00bf7-cf18-4284-af27-4ee5f1c8669c":config.getApiKey();
        ArkService service = ArkService.builder()
                .apiKey(apiKey)
                .baseUrl(config.getUrl()==null?"https://ark.cn-beijing.volces.com/api/v3":config.getUrl())
                .build();

        Flowable<?> flowable=null;
        if(StringUtils.isNotBlank(config.getModelId())){
            flowable=service.streamChatCompletion(buildChatReqParams(config.getModelId(),null,userId,groupFlag,question,withHisNum,custHisList))
                    .doOnError(Throwable::printStackTrace);
        }else{
            flowable=service.streamBotChatCompletion((BotChatCompletionRequest)buildChatReqParams(null,config.getCharacterId(),userId,groupFlag,question,withHisNum,custHisList))
                    .doOnError(Throwable::printStackTrace);
        }
        Map rmap=new HashMap();
        rmap.put("flowable",flowable);
        rmap.put("service",service);
        return rmap;
    }

    @Override
    public Map<String,Object> getEcgDiagAnswerAsync(String userId,String userName,String question,boolean incrementalOutput,int withHisNum,String groupFlag,List<?> custHisList) throws Exception {
        String apiKey = config.getApiKey()==null?"7ef00bf7-cf18-4284-af27-4ee5f1c8669c":config.getApiKey();
        ArkService service = ArkService.builder()
                .apiKey(apiKey)
                .baseUrl(config.getUrl()==null?"https://ark.cn-beijing.volces.com/api/v3":config.getUrl())
                .build();

        Flowable<?> flowable=null;
        if(StringUtils.isNotBlank(config.getEcgModelId())){
            flowable=service.streamChatCompletion(buildChatReqParams(config.getEcgModelId(),null,userId,groupFlag,question,withHisNum,custHisList))
                    .doOnError(Throwable::printStackTrace);
        }else{
            flowable=service.streamBotChatCompletion((BotChatCompletionRequest)buildChatReqParams(null,config.getEcgCharacterId(),userId,groupFlag,question,withHisNum,custHisList))
                    .doOnError(Throwable::printStackTrace);
        }
        Map rmap=new HashMap();
        rmap.put("flowable",flowable);
        rmap.put("service",service);
        return rmap;
    }

    @Override
    public Map<String,Object> getAnswerMultiPartsAsync(String userId,String userName,String question,String imageUrl) throws Exception {
        String apiKey = config.getApiKey()==null?"7ef00bf7-cf18-4284-af27-4ee5f1c8669c":config.getApiKey();
        ArkService service = ArkService.builder()
                .apiKey(apiKey)
                .baseUrl(config.getUrl()==null?"https://ark.cn-beijing.volces.com/api/v3":config.getUrl())
                .build();
        Flowable<BotChatCompletionChunk> flowable=service.streamBotChatCompletion(buildMultiPartsChatReqParams(config.getElansenOcrModelId(),config.getElansenOcrCharacterId(),question,imageUrl))
                .doOnError(Throwable::printStackTrace);
        // shutdown service
//        service.shutdownExecutor();
        Map rmap=new HashMap();
        rmap.put("flowable",flowable);
        rmap.put("service",service);
        return rmap;
    }

    @Override
    public List<ChatMessage> getLLMChatHis(String userId,String groupFlag) {
        if(StringUtils.isBlank(groupFlag)){
            groupFlag="default";
        }
        //从缓存获取用户历史聊天记录
        String key=sysRedisUtil.getLLMDoubaoMessageHis(userId);
        Map<String,List<ChatMessage>> userHisMsgList=(Map<String,List<ChatMessage>>)sysRedisUtil.get(key);
        if(userHisMsgList!=null){
            return userHisMsgList.get(groupFlag);
        }
        return null;
    }

    @Override
    public void setLLMChatHis(String userId,String groupFlag, String role, String msg) {
        if(StringUtils.isBlank(groupFlag)){
            groupFlag="default";
        }
        //从缓存获取用户历史聊天记录
        String key=sysRedisUtil.getLLMDoubaoMessageHis(userId);
//        sysRedisUtil.del(key);
        Map<String,List<ChatMessage>> userHisMsgList=(Map<String,List<ChatMessage>>)sysRedisUtil.get(key);
        if(userHisMsgList==null){
            userHisMsgList=new HashMap<String,List<ChatMessage>>();
        }
        List<ChatMessage> hisMsgList=userHisMsgList.get(groupFlag);
        if(hisMsgList==null){
            hisMsgList=new ArrayList<ChatMessage>();
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
        //最多缓存聊天记录轮数
        int chatMemTimes=100;
        if(hisMsgList.size()>chatMemTimes){
            userHisMsgList.put(groupFlag,new ArrayList<>(hisMsgList.subList(hisMsgList.size()-chatMemTimes,hisMsgList.size())));
        }else{
            userHisMsgList.put(groupFlag,hisMsgList);
        }
        sysRedisUtil.set(key,userHisMsgList);
    }

    /**
     * 构建请求参数
     * @param modelId 指定模型id
     * @param botId 指定应用id
     * @param userId 用户标识
     * @param groupFlag 问题分组标识
     * @param question 问题
     * @param withHisNum 携带几轮上下文回话，从缓存中获取,custHisList为空时有效
     * @param custHisList 自定义上下文
     * @return
     */
    private ChatCompletionRequest buildChatReqParams(String modelId,String botId,String userId,String groupFlag,String question,int withHisNum,List<?> custHisList) {
        final List<ChatMessage> streamMessages = new ArrayList<>();
        if(CollectionUtils.isEmpty(custHisList)){
            //从缓存获取指定数量的用户历史聊天记录
            if(withHisNum>0){
                //从缓存获取用户历史聊天记录
                List<ChatMessage> hisMsgList=this.getLLMChatHis(userId,groupFlag);
                if(!CollectionUtils.isEmpty(hisMsgList)){
                    for(ChatMessage msg:hisMsgList){
                        if(streamMessages.size()>=withHisNum){
                         break;
                        }
                        streamMessages.add(msg);
                    }
                }
            }
        }else{
            //自定义历史聊天记录
            for(Object msg:custHisList){
                streamMessages.add((ChatMessage) msg);
            }
        }

        final ChatMessage streamUserMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(question).build();
        streamMessages.add(streamUserMessage);

        //新聊天加入缓存
        this.setLLMChatHis(userId,groupFlag,"user",question);

        if(StringUtils.isNotBlank(modelId)){
            return ChatCompletionRequest.builder()
                    .model(modelId)
                    .messages(streamMessages)
                    .build();
        }else{
            if(StringUtils.isBlank(botId)){
                botId=config.getCharacterId()==null?"bot-20241215140240-fttjs":config.getCharacterId();
            }
            return BotChatCompletionRequest.builder()
                    .botId(botId)
                    .messages(streamMessages)
                    .build();
        }
    }

    /**
     * 构建图片理解请求参数
     * @param modelId 指定模型id
     * @param botId 指定应用id
     * @param question 问题
     * @param imageUrl 图片连接
     * @return
     */
    private BotChatCompletionRequest buildMultiPartsChatReqParams(String modelId,String botId,String question,String imageUrl) {
        List<ChatMessage> streamMessages = new ArrayList<>();
        List<ChatCompletionContentPart> multiParts=new ArrayList<>();
        multiParts.add(ChatCompletionContentPart.builder().type("text").text(question).build());
        multiParts.add(ChatCompletionContentPart.builder().type("image_url").imageUrl(new ChatCompletionContentPart.ChatCompletionContentPartImageURL(imageUrl)).build());
        ChatMessage streamUserMessage = ChatMessage.builder().role(ChatMessageRole.USER).multiContent(multiParts).build();
        streamMessages.add(streamUserMessage);

        return BotChatCompletionRequest.builder().model(modelId).botId(botId).messages(streamMessages).build();
    }
}