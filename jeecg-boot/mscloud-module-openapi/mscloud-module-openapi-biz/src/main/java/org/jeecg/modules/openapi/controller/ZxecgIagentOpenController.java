package org.jeecg.modules.openapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionChunk;
import com.volcengine.ark.runtime.service.ArkService;
import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.iagent.nls.common.AliyunNLSTokenUtil;
import org.jeecg.modules.iagent.nls.llm.LLMOperater;
import org.jeecg.modules.iagent.nls.llm.config.AliyunLLMConfig;
import org.jeecg.modules.iagent.nls.tts.IAliyunTTSSpeechSynthesizer;
import org.jeecg.modules.iagent.nls.tts.config.AliyunTTSConfig;
import org.jeecg.modules.zxecg.oapi.service.IZxecgExternalAPICallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 正心心电工作站智能体操作接口
 * @Author: Kingpin
 * @Date: 2024-12-04 14:25:33
 **/
@Api(tags = "正心心电工作站智能体操作")
@RestController
@RequestMapping("/oapi/iagent/zxecg")
@Slf4j
public class ZxecgIagentOpenController {
    @Autowired
    AliyunLLMConfig llmConfig;
    @Autowired
    AliyunTTSConfig ttsConfig;
    @Resource(name="doubaoLLMOperaterImpl")
    LLMOperater llm;
    @Resource
    IAliyunTTSSpeechSynthesizer ttsSpeechSynthesizer;
    final ExecutorService nonBlockingService = Executors.newCachedThreadPool();
    @Autowired
    AliyunNLSTokenUtil tokenUtil;
    @Autowired
    IZxecgExternalAPICallService apiCallService;

    /**
     * 心电工作站LLM获取报告解读建议
     * @param bodyJson
     * @return
     */
    @ApiOperation(value="心电工作站LLM获取报告解读建议接口", notes = "心电工作站LLM获取报告解读建议")
    @PostMapping("/llm/qa")
    @ResponseBody
    public SseEmitter doElansenLLM(@RequestBody JSONObject bodyJson) {
        SseEmitter emitter = new SseEmitter(120000L);
        String question = bodyJson.getString("question");
        String userFlag=bodyJson.getString("userFlag");
        String groupFlag=bodyJson.getString("groupFlag");

        nonBlockingService.execute(() -> {
            final int[] index = {0};
            ArkService service=null;
            try {
                //调用LLM
                Map llmResult = llm.getEcgDiagAnswerAsync(userFlag,userFlag,question,true,0,groupFlag,null);
                Flowable<BotChatCompletionChunk> response=llmResult.get("flowable")==null?null:(Flowable<BotChatCompletionChunk>)llmResult.get("flowable");
                service=llmResult.get("service")==null?null:(ArkService)llmResult.get("service");
                ArkService finalService=service;

                response.subscribe(new DisposableSubscriber<BotChatCompletionChunk>() {
                    @Override
                    public void onNext(BotChatCompletionChunk chatResult) {
                        String content=chatResult.getChoices().get(0).getMessage().getContent().toString();
                        index[0]++;
                        JSONObject object = new JSONObject();
                        object.put("seq", index[0]);
                        object.put("content", content);
                        SseEmitter.SseEventBuilder event = SseEmitter.event()
                                .data(object.toJSONString(), MediaType.APPLICATION_JSON)
                                .id(String.valueOf(index[0]))
                                .name("event-"+index[0]);
                        try {
                            emitter.send(event);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
                        try{
                            emitter.completeWithError(throwable);
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            if(finalService!=null){
                                finalService.shutdownExecutor();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        //通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
                        try{
                            emitter.complete();
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            if(finalService!=null){
                                finalService.shutdownExecutor();
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                emitter.completeWithError(e);
            }finally {
                if(service!=null){
                    service.shutdownExecutor();
                }
            }
        });

        return emitter;
    }

    @ApiOperation(value="心电工作站图片识别接口", notes = "心电工作站图片识别")
    @PostMapping("/llm/ocr")
    @ResponseBody
    public SseEmitter doElansenLLMOcr(@RequestBody JSONObject bodyJson) {
        SseEmitter emitter = new SseEmitter(120000L);
        String question = bodyJson.getString("question");
        String userFlag=bodyJson.getString("userFlag");
        String groupFlag=bodyJson.getString("groupFlag");
        String imageUrl=bodyJson.getString("imageUrl");

        nonBlockingService.execute(() -> {
            final int[] index = {0};
            ArkService service=null;
            try {
                //调用LLM
                Map llmResult = llm.getAnswerMultiPartsAsync(userFlag,userFlag,question,imageUrl);
                Flowable<BotChatCompletionChunk> response=llmResult.get("flowable")==null?null:(Flowable<BotChatCompletionChunk>)llmResult.get("flowable");
                service=llmResult.get("service")==null?null:(ArkService)llmResult.get("service");
                ArkService finalService=service;

                response.subscribe(new DisposableSubscriber<BotChatCompletionChunk>() {
                    @Override
                    public void onNext(BotChatCompletionChunk chatResult) {
                        String content=chatResult.getChoices().get(0).getMessage().getContent().toString();
                        index[0]++;
                        JSONObject object = new JSONObject();
                        object.put("seq", index[0]);
                        object.put("content", content);
                        SseEmitter.SseEventBuilder event = SseEmitter.event()
                                .data(object.toJSONString(), MediaType.APPLICATION_JSON)
                                .id(String.valueOf(index[0]))
                                .name("event-"+index[0]);
                        try {
                            emitter.send(event);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
                        try{
                            emitter.completeWithError(throwable);
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            if(finalService!=null){
                                finalService.shutdownExecutor();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        //通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
                        try{
                            emitter.complete();
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            if(finalService!=null){
                                finalService.shutdownExecutor();
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                emitter.completeWithError(e);
            }finally {
                if(service!=null){
                    service.shutdownExecutor();
                }
            }
        });

        return emitter;
    }
}
