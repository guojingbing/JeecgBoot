package org.jeecg.modules.iagent.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nls.client.protocol.tts.FlowingSpeechSynthesizer;
import com.alibaba.nls.client.protocol.tts.FlowingSpeechSynthesizerListener;
import com.alibaba.nls.client.protocol.tts.FlowingSpeechSynthesizerResponse;
import com.alibaba.xingchen.model.ChatResult;
import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.iagent.nls.common.AliyunNLSTokenUtil;
import org.jeecg.modules.iagent.nls.llm.LLMOperater;
import org.jeecg.modules.iagent.nls.llm.config.AliyunLLMConfig;
import org.jeecg.modules.iagent.nls.tts.IAliyunTTSSpeechSynthesizer;
import org.jeecg.modules.iagent.nls.tts.config.AliyunTTSConfig;
import org.jeecg.modules.zxecg.oapi.service.IZxecgExternalAPICallService;
import org.jeecg.modules.zxecg.vo.ZxecgUserReportVo;
import org.jeecg.modules.zxecg.vo.ZxecgUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 智能体接口
 * @Author: Kingpin
 * @Date: 2024-12-04 14:25:33
 **/
@Api(tags = "智能体操作")
@RestController
@RequestMapping("/iagent")
@Slf4j
public class IagentController {
    @Autowired
    AliyunLLMConfig llmConfig;
    @Autowired
    AliyunTTSConfig ttsConfig;
    @Resource(name="xingchenLLMOperaterImpl")
    LLMOperater llm;
    @Resource
    IAliyunTTSSpeechSynthesizer ttsSpeechSynthesizer;
    final ExecutorService nonBlockingService = Executors.newCachedThreadPool();
    @Autowired
    AliyunNLSTokenUtil tokenUtil;

    @Autowired
    IZxecgExternalAPICallService apiCallService;

    @GetMapping("/test/llm")
    @ResponseBody
    public ResponseBodyEmitter doGetLLMAudioAsync(@RequestParam(name="text", required = false) String text) {
        long st=System.currentTimeMillis();
        if(StringUtils.isBlank(text)){
            text="房扑房颤";
        }
        // 创建一个 ResponseBodyEmitter 实例
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        FlowingSpeechSynthesizer flowingSpeechSynthesizer= null;
        try {
            flowingSpeechSynthesizer = ttsSpeechSynthesizer.getFlowingSpeechSynthesizer(new FlowingSpeechSynthesizerListener() {
//                File f=new File("G:\\flowingTts.wav");
//                FileOutputStream fout = new FileOutputStream(f);
                private boolean firstRecvBinary = true;
                //流式文本语音合成开始
                public void onSynthesisStart(FlowingSpeechSynthesizerResponse response) {
//                    System.out.println("name: " + response.getName() + ", status: " + response.getStatus());
                    System.out.println("流式文本语音合成开始>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                }
                //服务端检测到了一句话的开始
                public void onSentenceBegin(FlowingSpeechSynthesizerResponse response) {
//                    System.out.println("name: " + response.getName() + ", status: " + response.getStatus());
                    System.out.println("服务端检测到了一句话的开始>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                }
                //服务端检测到了一句话的结束，获得这句话的起止位置和所有时间戳
                public void onSentenceEnd(FlowingSpeechSynthesizerResponse response) {
//                    System.out.println("name: " + response.getName() + ", status: " + response.getStatus() + ", subtitles: " + response.getObject("subtitles"));
                    System.out.println("服务端检测到了一句话的结束>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                }
                //流式文本语音合成结束
                @Override
                public void onSynthesisComplete(FlowingSpeechSynthesizerResponse response) {
                    // 调用onSynthesisComplete时，表示所有TTS数据已经接收完成，所有文本都已经合成音频并返回。
//                    System.out.println("name: " + response.getName() + ", status: " + response.getStatus());
                    System.out.println("流式文本语音合成结束>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                    emitter.complete(); // 任务完成，关闭连接
                }
                //收到语音合成的语音二进制数据
                @Override
                public void onAudioData(ByteBuffer message) {
                    if(firstRecvBinary) {
                        // 此处计算首包语音流的延迟，收到第一包语音流时，即可以进行语音播放，以提升响应速度（特别是实时交互场景下）。
                        firstRecvBinary = false;
                        System.out.println("音频首包返回耗时>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                    }
                    byte[] bytesArray = new byte[message.remaining()];
                    message.get(bytesArray, 0, bytesArray.length);
                    // 向客户端发送音频流
                    try {
                        emitter.send(bytesArray);
//                        fout.write(bytesArray);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //收到语音合成的增量音频时间戳
                @Override
                public void onSentenceSynthesis(FlowingSpeechSynthesizerResponse response) {
                    //                System.out.println("name: " + response.getName() + ", status: " + response.getStatus() + ", subtitles: " + response.getObject("subtitles"));
                }
                @Override
                public void onFail(FlowingSpeechSynthesizerResponse response){
                    // task_id是调用方和服务端通信的唯一标识，当遇到问题时，需要提供此task_id以便排查。
                    log.error(
                            "session_id: " + getFlowingSpeechSynthesizer().getCurrentSessionId() +
                                    ", task_id: " + response.getTaskId() +
                                    //状态码
                                    ", status: " + response.getStatus() +
                                    //错误信息
                                    ", status_text: " + response.getStatusText());
                    emitter.complete(); // 关闭连接
                }
            });
            FlowingSpeechSynthesizer finalFlowingSpeechSynthesizer = flowingSpeechSynthesizer;

            //调用通义星尘LLM
            Flowable<ChatResult> response = llm.getAnswerAsync("user1234","小明",text,true);
            response.subscribe(new DisposableSubscriber<ChatResult>() {
                @Override
                public void onNext(ChatResult chatResult) {
                    System.out.println("flowable onNext");
                    System.out.println("LLM返回耗时>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                    String content=chatResult.getChoices().get(0).getMessages().get(0).getContent().trim();
                    System.out.println(content);
                    if (StringUtils.isNotBlank(content)) {
                        //发送到流式音频合成
                        finalFlowingSpeechSynthesizer.send(content);
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("flowable onError"+throwable.getMessage());
                    try {
                        //通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
                        finalFlowingSpeechSynthesizer.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        finalFlowingSpeechSynthesizer.close();
                    }
                }

                @Override
                public void onComplete() {
                    System.out.println("flowable onComplete");
                    try {
                        //通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
                        finalFlowingSpeechSynthesizer.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        finalFlowingSpeechSynthesizer.close();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emitter; // 返回 Emitter
    }

    @PostMapping("/test/llm/sse")
    @ResponseBody
    public SseEmitter handleLLMSse(@RequestBody JSONObject bodyJson) {
        SseEmitter emitter = new SseEmitter();
        long st=System.currentTimeMillis();
        String question = bodyJson.getString("question");
        if(StringUtils.isBlank(question)){
            question="房扑房颤";
        }

        String finalText = question;
        nonBlockingService.execute(() -> {
            FlowingSpeechSynthesizer flowingSpeechSynthesizer= null;
            final int[] index = {0};
            try {
                flowingSpeechSynthesizer = ttsSpeechSynthesizer.getFlowingSpeechSynthesizer(new FlowingSpeechSynthesizerListener() {
                    //                File f=new File("G:\\flowingTts.wav");
//                FileOutputStream fout = new FileOutputStream(f);
                    private boolean firstRecvBinary = true;
                    //流式文本语音合成开始
                    public void onSynthesisStart(FlowingSpeechSynthesizerResponse response) {
//                    System.out.println("name: " + response.getName() + ", status: " + response.getStatus());
                        System.out.println("流式文本语音合成开始>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                    }
                    //服务端检测到了一句话的开始
                    public void onSentenceBegin(FlowingSpeechSynthesizerResponse response) {
//                    System.out.println("name: " + response.getName() + ", status: " + response.getStatus());
                        System.out.println("服务端检测到了一句话的开始>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                    }
                    //服务端检测到了一句话的结束，获得这句话的起止位置和所有时间戳
                    public void onSentenceEnd(FlowingSpeechSynthesizerResponse response) {
//                    System.out.println("name: " + response.getName() + ", status: " + response.getStatus() + ", subtitles: " + response.getObject("subtitles"));
                        System.out.println("服务端检测到了一句话的结束>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                    }
                    //流式文本语音合成结束
                    @Override
                    public void onSynthesisComplete(FlowingSpeechSynthesizerResponse response) {
                        // 调用onSynthesisComplete时，表示所有TTS数据已经接收完成，所有文本都已经合成音频并返回。
//                    System.out.println("name: " + response.getName() + ", status: " + response.getStatus());
                        System.out.println("流式文本语音合成结束>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                        JSONObject object = new JSONObject();
                        object.put("id", IdUtil.fastUUID());
                        object.put("seq", index[0]);
                        object.put("end", 1);
                        SseEmitter.SseEventBuilder event = SseEmitter.event()
                                .data(object, MediaType.APPLICATION_JSON)
                                .id(String.valueOf(index[0]))
                                .name("event-"+index[0]);
                        try {
                            emitter.send(event);
                            emitter.complete(); // 关闭连接
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        emitter.complete(); // 任务完成，关闭连接
                    }
                    //收到语音合成的语音二进制数据
                    @Override
                    public void onAudioData(ByteBuffer message) {
                        if(firstRecvBinary) {
                            // 此处计算首包语音流的延迟，收到第一包语音流时，即可以进行语音播放，以提升响应速度（特别是实时交互场景下）。
                            firstRecvBinary = false;
                            System.out.println("音频首包返回耗时>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                        }
                        byte[] bytesArray = new byte[message.remaining()];
                        message.get(bytesArray, 0, bytesArray.length);
                        index[0]++;
                        JSONObject object = new JSONObject();
                        object.put("id", IdUtil.fastUUID());
                        object.put("audioData", bytesArray);
                        SseEmitter.SseEventBuilder event = SseEmitter.event()
                                .data(object, MediaType.APPLICATION_JSON)
                                .id(String.valueOf(index[0]))
                                .name("event-"+index[0]);
                        // 向客户端发送音频流
                        try {
                            emitter.send(event);
//                            emitter.send(bytesArray);
//                        fout.write(bytesArray);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //收到语音合成的增量音频时间戳
                    @Override
                    public void onSentenceSynthesis(FlowingSpeechSynthesizerResponse response) {
                        //                System.out.println("name: " + response.getName() + ", status: " + response.getStatus() + ", subtitles: " + response.getObject("subtitles"));
                    }
                    @Override
                    public void onFail(FlowingSpeechSynthesizerResponse response){
                        // task_id是调用方和服务端通信的唯一标识，当遇到问题时，需要提供此task_id以便排查。
                        log.error(
                                "session_id: " + getFlowingSpeechSynthesizer().getCurrentSessionId() +
                                        ", task_id: " + response.getTaskId() +
                                        //状态码
                                        ", status: " + response.getStatus() +
                                        //错误信息
                                        ", status_text: " + response.getStatusText());
                        JSONObject object = new JSONObject();
                        object.put("id", IdUtil.fastUUID());
                        object.put("seq", index[0]);
                        object.put("end", 1);
                        SseEmitter.SseEventBuilder event = SseEmitter.event()
                                .data(object, MediaType.APPLICATION_JSON)
                                .id(String.valueOf(index[0]))
                                .name("event-"+index[0]);
                        try {
                            emitter.send(event);
                            emitter.complete(); // 关闭连接
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                FlowingSpeechSynthesizer finalFlowingSpeechSynthesizer = flowingSpeechSynthesizer;

                //调用通义星尘LLM
                Flowable<ChatResult> response = llm.getAnswerAsync("user1234","小明", finalText,true);
                response.subscribe(new DisposableSubscriber<ChatResult>() {
                    @Override
                    public void onNext(ChatResult chatResult) {
                        System.out.println("flowable onNext");
                        System.out.println("LLM返回耗时>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                        String content=chatResult.getChoices().get(0).getMessages().get(0).getContent().trim();
                        System.out.println(content);
                        if (StringUtils.isNotBlank(content)) {
                            //发送到流式音频合成
                            finalFlowingSpeechSynthesizer.send(content);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("flowable onError"+throwable.getMessage());
                        try {
                            //通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
                            finalFlowingSpeechSynthesizer.stop();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            finalFlowingSpeechSynthesizer.close();
                        }
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("flowable onComplete");
                        try {
                            //通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
                            finalFlowingSpeechSynthesizer.stop();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            finalFlowingSpeechSynthesizer.close();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    @GetMapping("/test/sse")
    @ResponseBody
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter();
        String[] split = ("金融业也被波及。多国石油天然气、电力、股票交易19日、当天难以正常展开。" +
                "伦敦证券交易所等、重要金融市场的监管新闻服务、交易系统出现问题，信息无法及时流通。" +
                "在系统故障期间，" +
                "一些银行、金融机构甚至不得不采用手工记账，" +
                "极大地降低了、工作效率。" +
                "英国、德国、南非和新西兰都有银行、客户在交易中遇到问题。").split("[。、,]");
        nonBlockingService.execute(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    JSONObject object = new JSONObject();
                    object.put("id", IdUtil.fastUUID());
                    object.put("content", split[i]);
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .data(object.toJSONString(), MediaType.TEXT_PLAIN)
                            .id(String.valueOf(i))
                            .name(""+i);
                    emitter.send(event);
                    Thread.sleep(1000); // 模拟延迟
                }
                emitter.complete();
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    @GetMapping("/test/nls/token")
    @ResponseBody
    public Result<?> getNLSToken() {
        String appkey=tokenUtil.getDefaultNlsAppKey();
        String token=tokenUtil.getToken();
        Map remap=new HashMap<>();
        remap.put("appkey",appkey);
        remap.put("token",token);
        return Result.ok(remap);
    }

    @GetMapping("/test/ecg/u/info")
    @ResponseBody
    public Result<?> getEcgUserInfo(@RequestParam(name="userId", required = false) String userId, @RequestParam(name="repDate", required = false) String repDate) {
        List<ZxecgUserVo> friends=apiCallService.getUserFriends(userId);
        Map remap=new HashMap<>();
        remap.put("friends",friends);

        Map<String,ZxecgUserReportVo> repMap=apiCallService.getUserRepInfo(userId,null);
        remap.put("repInfo",repMap);

        ZxecgUserVo userInfo=apiCallService.getUserInfo(userId);
        remap.put("userInfo",userInfo);

        return Result.ok(remap);
    }
}
