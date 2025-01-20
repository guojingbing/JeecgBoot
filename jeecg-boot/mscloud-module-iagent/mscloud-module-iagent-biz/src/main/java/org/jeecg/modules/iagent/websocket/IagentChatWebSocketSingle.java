package org.jeecg.modules.iagent.websocket;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nls.client.protocol.SpeechReqProtocol;
import com.alibaba.nls.client.protocol.asr.SpeechRecognizerListener;
import com.alibaba.nls.client.protocol.asr.SpeechRecognizerResponse;
import com.alibaba.nls.client.protocol.tts.FlowingSpeechSynthesizer;
import com.alibaba.nls.client.protocol.tts.FlowingSpeechSynthesizerListener;
import com.alibaba.nls.client.protocol.tts.FlowingSpeechSynthesizerResponse;
import com.alibaba.xingchen.model.ChatResult;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionChunk;
import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.codec.Base64;
import org.jeecg.common.base.BaseMap;
import org.jeecg.common.constant.WebsocketConst;
import org.jeecg.common.modules.redis.client.JeecgRedisClient;
import org.jeecg.common.util.InstanceBeanUtils;
import org.jeecg.modules.iagent.nls.asr.IAliyunSpeechRecognizer;
import org.jeecg.modules.iagent.nls.asr.impl.AliyunSpeechRecognizerImpl;
import org.jeecg.modules.iagent.nls.llm.LLMOperater;
import org.jeecg.modules.iagent.nls.llm.impl.DoubaoLLMOperaterImpl;
import org.jeecg.modules.iagent.nls.tts.IAliyunTTSSpeechSynthesizer;
import org.jeecg.modules.iagent.nls.tts.impl.AliyunTTSSpeechSynthesizerImpl;
import org.jeecg.modules.iagent.router.constant.IagentRouterConstant;
import org.jeecg.modules.iagent.router.service.IIagentQARouter;
import org.jeecg.modules.iagent.router.service.impl.IagentQARouterImpl;
import org.jeecg.modules.iagent.websocket.vo.WebsocketMessage;
import org.jeecg.modules.zxecg.oapi.service.IZxecgExternalAPICallService;
import org.jeecg.modules.zxecg.oapi.service.impl.ZxecgExternalAPICallServiceImpl;
import org.jeecg.modules.zxecg.service.IZxecgService;
import org.jeecg.modules.zxecg.vo.ZxecgUserReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author scott
 * @Date 2019/11/29 9:41
 * @Description: 此注解相当于设置访问URL
 */
@Component
@Slf4j
@ServerEndpoint("/iagentWebsocket/{userId}")
public class IagentChatWebSocketSingle {
    /**线程安全Map*/
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, IAliyunSpeechRecognizer> recognizerPool = new ConcurrentHashMap<>();

    /**
     * Redis触发监听名字
     */
    public static final String REDIS_TOPIC_NAME = "iagentSocketHandler";

    //避免初次调用出现空指针的情况
    private static JeecgRedisClient jeecgRedisClient;

    private IIagentQARouter router;

    private IZxecgService zxecgService;

    private IZxecgExternalAPICallService apiCallService;

    @Autowired
    private void setJeecgRedisClient(JeecgRedisClient jeecgRedisClient){
        IagentChatWebSocketSingle.jeecgRedisClient = jeecgRedisClient;
    }

    //==========【websocket接受、推送消息等方法 —— 具体服务节点推送ws消息】========================================================================================
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        try {
            sessionPool.put(userId, session);
            log.debug("【系统 WebSocket】有新的连接，总数为:" + sessionPool.size());
        } catch (Exception e) {
        }
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        try {
            sessionPool.remove(userId);
            log.debug("【系统 WebSocket】连接断开，总数为:" + sessionPool.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ws推送消息
     *
     * @param userId
     * @param message
     */
    public void pushMessage(String userId, String message) {
        for (Map.Entry<String, Session> item : sessionPool.entrySet()) {
            //userId key值= {用户id + "_"+ 登录token的md5串}
            //TODO vue2未改key新规则，暂时不影响逻辑
            if (item.getKey().contains(userId)) {
                Session session = item.getValue();
                try {
                    //update-begin-author:taoyan date:20211012 for: websocket报错 https://gitee.com/jeecg/jeecg-boot/issues/I4C0MU
                    synchronized (session){
                        log.debug("【系统 WebSocket】推送单人消息:" + message);
                        session.getBasicRemote().sendText(message);
                    }
                    //update-end-author:taoyan date:20211012 for: websocket报错 https://gitee.com/jeecg/jeecg-boot/issues/I4C0MU
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
    }

    /**
     * ws遍历群发消息
     */
    public void pushMessage(String message) {
        try {
            for (Map.Entry<String, Session> item : sessionPool.entrySet()) {
                try {
                    item.getValue().getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            log.debug("【系统 WebSocket】群发消息:" + message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * ws接受客户端消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam(value = "userId") String userId) {
        if(!"ping".equals(message) && !WebsocketConst.CMD_CHECK.equals(message)){
            JSONObject messageJson=JSONObject.parseObject(message);
            if(StringUtils.isNotBlank(messageJson.getString("source"))&&messageJson.getString("source").equalsIgnoreCase("chatAudio")){
                String taskId=messageJson.getString("taskId");
                String content=messageJson.getString("content");
                byte[] audioStream=Base64.decode(content);

                int flag=messageJson.getInteger("progress");
                boolean isBegin=flag==0?true:false;
                boolean isEnd=flag==2?true:false;
                IAliyunSpeechRecognizer recognizer=recognizerPool.get(userId);
                System.out.println(isBegin+">>>>>>>>>>>>>>>>>>>>>"+isEnd);
                if(null==recognizer){
                    recognizer= InstanceBeanUtils.getBean(AliyunSpeechRecognizerImpl.class);
                    isBegin=true;
                    recognizer.init();
                    recognizerPool.put(userId,recognizer);
                }else{
                    SpeechReqProtocol.State state=(SpeechReqProtocol.State)recognizer.getState();
                    boolean isExpired=recognizer.tokenExpired();
                    log.debug(isExpired+":从websocket缓存中获取IAliyunSpeechRecognizer,state:"+state);
                    if(isExpired||state==SpeechReqProtocol.State.STATE_FAIL){
                        log.debug("从websocket缓存中获取IAliyunSpeechRecognizer，但已失效，重新初始化:"+state);
                        recognizer= InstanceBeanUtils.getBean(AliyunSpeechRecognizerImpl.class);
                        isBegin=true;
                        recognizer.init();
                        recognizerPool.put(userId,recognizer);
                    }
                }
                recognizer.executeRecognize(audioStream, null, isBegin, isEnd, new SpeechRecognizerListener() {
                    @Override
                    public void onRecognitionResultChanged(SpeechRecognizerResponse speechRecognizerResponse) {
//                        log.debug(speechRecognizerResponse.getRecognizedText());
                        WebsocketMessage message=new WebsocketMessage(taskId,speechRecognizerResponse.getRecognizedText(),1,2);
                        pushMessage(userId,message.toString());
                    }

                    @Override
                    public void onRecognitionCompleted(SpeechRecognizerResponse speechRecognizerResponse) {
                        String text=speechRecognizerResponse.getRecognizedText();
                        log.error("onRecognitionCompleted>>>"+text);
                        WebsocketMessage message=new WebsocketMessage(taskId,text,2,2);
                        pushMessage(userId,message.toString());
                        //判断答案获取路由
                        router=InstanceBeanUtils.getBean(IagentQARouterImpl.class);
                        Map routerMap=router.getQaRouter(text,userId);

                        IAliyunTTSSpeechSynthesizer ttsSpeechSynthesizer=InstanceBeanUtils.getBean(AliyunTTSSpeechSynthesizerImpl.class);;
                        FlowingSpeechSynthesizer flowingSpeechSynthesizer= null;
                        try {
                            long st=System.currentTimeMillis();
                            flowingSpeechSynthesizer = ttsSpeechSynthesizer.getFlowingSpeechSynthesizer(new FlowingSpeechSynthesizerListener() {
                                private boolean firstRecvBinary = true;
                                //流式文本语音合成开始
                                public void onSynthesisStart(FlowingSpeechSynthesizerResponse response) {
                                    log.debug("流式文本语音合成开始>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                                }
                                //服务端检测到了一句话的开始
                                public void onSentenceBegin(FlowingSpeechSynthesizerResponse response) {
                                    log.debug("服务端检测到了一句话的开始>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                                }
                                //服务端检测到了一句话的结束，获得这句话的起止位置和所有时间戳
                                public void onSentenceEnd(FlowingSpeechSynthesizerResponse response) {
                                    log.debug("服务端检测到了一句话的结束>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                                }
                                //流式文本语音合成结束
                                @Override
                                public void onSynthesisComplete(FlowingSpeechSynthesizerResponse response) {
                                    // 调用onSynthesisComplete时，表示所有TTS数据已经接收完成，所有文本都已经合成音频并返回。
                                    log.debug("流式文本语音合成结束>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                                    // 向客户端发送音频流
                                    WebsocketMessage streamMessage=new WebsocketMessage(taskId,"语音返回结束",2,3);
                                    pushMessage(userId,streamMessage.toString());
                                }
                                //收到语音合成的语音二进制数据
                                @Override
                                public void onAudioData(ByteBuffer message) {
                                    if(firstRecvBinary) {
                                        // 此处计算首包语音流的延迟，收到第一包语音流时，即可以进行语音播放，以提升响应速度（特别是实时交互场景下）。
                                        firstRecvBinary = false;
                                        log.debug("音频首包返回耗时>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                                    }
                                    byte[] bytesArray = new byte[message.remaining()];
                                    message.get(bytesArray, 0, bytesArray.length);
                                    log.debug("音频返回耗时>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                                    // 向客户端发送音频流
                                    WebsocketMessage streamMessage=new WebsocketMessage(taskId,Base64.encodeToString(bytesArray),1,3);
                                    pushMessage(userId,streamMessage.toString());
                                }
                                //收到语音合成的增量音频时间戳
                                @Override
                                public void onSentenceSynthesis(FlowingSpeechSynthesizerResponse response) {
                                    //                log.debug("name: " + response.getName() + ", status: " + response.getStatus() + ", subtitles: " + response.getObject("subtitles"));
                                }
                                @Override
                                public void onFail(FlowingSpeechSynthesizerResponse response){
                                    // task_id是调用方和服务端通信的唯一标识，当遇到问题时，需要提供此task_id以便排查。
                                    log.debug(
                                            "session_id: " + getFlowingSpeechSynthesizer().getCurrentSessionId() +
                                                    ", task_id: " + response.getTaskId() +
                                                    //状态码
                                                    ", status: " + response.getStatus() +
                                                    //错误信息
                                                    ", status_text: " + response.getStatusText());
                                }
                            });
                            FlowingSpeechSynthesizer finalFlowingSpeechSynthesizer = flowingSpeechSynthesizer;

                            if(routerMap.get("router").equals(IagentRouterConstant.QAROUTER_LLM)){
                                LLMOperater llm=InstanceBeanUtils.getBean(DoubaoLLMOperaterImpl.class);
                                //调用LLM
                                Flowable<?> response = llm.getAnswerAsync(userId,userId,text,true);
                                final String[] llmAnswerContent = {""};
                                final long[] lastSentTime = {0};

                                response.subscribe(new DisposableSubscriber<Object>() {
                                    @Override
                                    public void onNext(Object chatResult) {
                                        log.debug("flowable onNext");
                                        log.debug("LLM返回耗时>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                                        String content=null;
                                        if(chatResult instanceof BotChatCompletionChunk){
                                            content=((BotChatCompletionChunk)chatResult).getChoices().get(0).getMessage().getContent().toString().trim();
                                        }else if(chatResult instanceof ChatResult){
                                            content=((ChatResult)chatResult).getChoices().get(0).getMessages().get(0).getContent().trim();
                                        }
                                        log.debug(content);
                                        if (StringUtils.isNotBlank(content)) {
                                            //避免TTS语音合成速度过快，控制TTS语音合成速度
                                            long now=System.currentTimeMillis();
//                                            log.error("now>>>"+now);
//                                            log.error("lastSentTime>>>"+lastSentTime[0]);
                                            if(lastSentTime[0]>0&&now-lastSentTime[0]<100){
                                                try {
//                                                    log.error("sleep>>>"+(100-(now-lastSentTime[0])));
                                                    Thread.sleep(100-(now-lastSentTime[0]));
                                                } catch (InterruptedException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                            //发送到流式音频合成
                                            finalFlowingSpeechSynthesizer.send(content);
                                            lastSentTime[0]=System.currentTimeMillis();
                                            // 向客户端发送LLM结果文本
                                            WebsocketMessage message=new WebsocketMessage(taskId,content,1,4);
                                            pushMessage(userId,message.toString());
                                            llmAnswerContent[0] +=content;
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        log.debug("flowable onError"+throwable.getMessage());
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
                                        log.debug("flowable onComplete");
                                        try {
                                            //通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
                                            finalFlowingSpeechSynthesizer.stop();
                                            // 向客户端发送LLM结果发送完成标记
                                            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>LLM结果文本发送完成");
                                            WebsocketMessage message=new WebsocketMessage(taskId,"LLM结果文本发送完成",2,4);
                                            pushMessage(userId,message.toString());
                                            llm.setLLMChatHis(userId,llm.DOUBAO_ROLE_ASSISTANT,llmAnswerContent[0]);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }finally {
                                            finalFlowingSpeechSynthesizer.close();
                                        }
                                    }
                                });
                            }else if(routerMap.get("router").equals(IagentRouterConstant.QAROUTER_API)){
                                //TODO 从正心心电数据库获取报告信息并组装文本交由TTS转换为语音返回
                                String msg=null;
                                String extra=null;
                                Map params=(Map)routerMap.get("params");
                                if(!params.isEmpty()){
                                    //查询自己还是亲友报告：1、自己；2、亲友
                                    Integer repUserType=(Integer)params.get("repUserType");
                                    String repUserId=(String)params.get("repUserId");
                                    String repUserName=(String)params.get("repUserName");
                                    String repDate=(String)params.get("repDate");
                                    if(StringUtils.isBlank(repUserName)){
                                        if(repUserType==1){
                                            msg="您好像还没有在心管家APP注册呢，请先注册使用哦。";
                                        }else{
                                            msg="您的亲友好像还没有在心管家APP注册呢，请先注册使用哦。";
                                        }
                                    }else{
                                        //从开放接口获取报告信息
                                        apiCallService=InstanceBeanUtils.getBean(ZxecgExternalAPICallServiceImpl.class);
                                        Map<String,ZxecgUserReportVo> repMap=apiCallService.getUserRepInfo(repUserId,repDate);

                                        ZxecgUserReportVo doctorRep=repMap.get("doctorRep");
                                        ZxecgUserReportVo autoRep=repMap.get("autoRep");

                                        if(doctorRep!=null){
                                            if(StringUtils.isNotBlank(doctorRep.getRepConclusion())){
                                                msg=""+doctorRep.getReportDate()+"的报告结论显示：\n";
                                                msg+=doctorRep.getRepConclusion();
                                            }
                                            extra=doctorRep.getPdfUrl();
                                        }else if(autoRep!=null){
                                            msg=autoRep.getRepConclusion();
                                        }else{
                                            if(repDate==null){
                                                repDate="";
                                            }
                                            if(repUserType.intValue()==1){
                                                msg="没有查询到您"+repDate+"的报告信息，请先佩戴正心智能心电仪，采集心电数据并申请报告哦。";
                                            }else{
                                                msg="没有查询到您亲友"+repUserName+repDate+"的报告信息，请先佩戴正心智能心电仪，采集心电数据并申请报告哦。";
                                            }
                                        }
                                    }
                                }else{
                                    msg="报告查询参数解析出现问题。";
                                }
                                if(StringUtils.isNotBlank(msg)){
                                    try {
                                        String[] textArr=msg.split("\n");
                                        //发送到流式音频合成
                                        for(String t :textArr) {
                                            log.debug(t);
                                            //发送流式文本数据。
                                            finalFlowingSpeechSynthesizer.send(t);
                                            // 向客户端发送LLM结果文本
                                            pushMessage(userId,new WebsocketMessage(taskId,t,1,4).toString());
                                            Thread.sleep(100);
                                        }
                                        //通知服务端流式文本数据发送完毕，阻塞等待服务端处理完成。
                                        finalFlowingSpeechSynthesizer.stop();
                                        Thread.sleep(100);
                                        // 向客户端发送LLM结果文本结束标记
                                        pushMessage(userId,new WebsocketMessage(taskId,"LLM结果文本发送完成",extra,2,4).toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }finally {
                                        finalFlowingSpeechSynthesizer.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //识别完成清理缓存
                            recognizerPool.remove(userId);
                        }
                    }

                    @Override
                    public void onStarted(SpeechRecognizerResponse speechRecognizerResponse) {
                    }

                    @Override
                    public void onFail(SpeechRecognizerResponse speechRecognizerResponse) {
                        log.error("onFail>>>"+speechRecognizerResponse.getStatus()+":" +speechRecognizerResponse.getStatusText());
                    }
                });
            }
        }else{
            log.error("【系统 WebSocket】收到客户端消息:" + message);
            //update-begin---author:wangshuai---date:2024-05-07---for:【issues/1161】前端websocket因心跳导致监听不起作用---
            this.sendMessage(userId, "ping");
            //update-end---author:wangshuai---date:2024-05-07---for:【issues/1161】前端websocket因心跳导致监听不起作用---
        }
        Session session=sessionPool.get(userId);
//        session.getUserProperties().put("recognizer",)
//        //------------------------------------------------------------------------------
//        JSONObject obj = new JSONObject();
//        //业务类型
//        obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_CHECK);
//        //消息内容
//        obj.put(WebsocketConst.MSG_TXT, "心跳响应");
//        this.pushMessage(userId, obj.toJSONString());
//        //------------------------------------------------------------------------------
    }
    /**
     * ws接受客户端消息
     */
    @OnMessage
    public void onMessage(ByteBuffer message, @PathParam(value = "userId") String userId) {
        log.error("【系统 WebSocket】收到客户端消息:" + message);
        String taskId=null;
        byte[] arr=message.array();
        byte flag=arr[0];
        boolean isBegin=false;
        boolean isEnd=false;
        if(flag==2){
            isEnd=true;
        }else if(flag==0){
            isBegin=true;
        }
        byte[] audioStream = new byte[arr.length-1];
        System.arraycopy(arr, 1, audioStream, 0, arr.length-1);
        IAliyunSpeechRecognizer recognizer=recognizerPool.get(userId);
        if(null==recognizer){
            recognizer= InstanceBeanUtils.getBean(AliyunSpeechRecognizerImpl.class);
            isBegin=true;
            recognizerPool.put(userId,recognizer);
        }
        recognizer.executeRecognize(audioStream, null, isBegin, isEnd, new SpeechRecognizerListener() {
            @Override
            public void onRecognitionResultChanged(SpeechRecognizerResponse speechRecognizerResponse) {
                log.debug(speechRecognizerResponse.getRecognizedText());
                WebsocketMessage message=new WebsocketMessage(taskId,speechRecognizerResponse.getRecognizedText(),1,2);
                pushMessage(userId,message.toString());
            }

            @Override
            public void onRecognitionCompleted(SpeechRecognizerResponse speechRecognizerResponse) {
                String text=speechRecognizerResponse.getRecognizedText();
                log.debug(speechRecognizerResponse.getRecognizedText());
                WebsocketMessage message=new WebsocketMessage(taskId,speechRecognizerResponse.getRecognizedText(),2,1);
                pushMessage(userId,message.toString());

                IAliyunTTSSpeechSynthesizer ttsSpeechSynthesizer=InstanceBeanUtils.getBean(AliyunTTSSpeechSynthesizerImpl.class);;
                FlowingSpeechSynthesizer flowingSpeechSynthesizer= null;
                try {
                    long st=System.currentTimeMillis();
                    flowingSpeechSynthesizer = ttsSpeechSynthesizer.getFlowingSpeechSynthesizer(new FlowingSpeechSynthesizerListener() {
                        //                File f=new File("G:\\flowingTts.wav");
//                FileOutputStream fout = new FileOutputStream(f);
                        private boolean firstRecvBinary = true;
                        //流式文本语音合成开始
                        public void onSynthesisStart(FlowingSpeechSynthesizerResponse response) {
//                    log.debug("name: " + response.getName() + ", status: " + response.getStatus());
                            log.debug("流式文本语音合成开始>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                        }
                        //服务端检测到了一句话的开始
                        public void onSentenceBegin(FlowingSpeechSynthesizerResponse response) {
//                    log.debug("name: " + response.getName() + ", status: " + response.getStatus());
                            log.debug("服务端检测到了一句话的开始>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                        }
                        //服务端检测到了一句话的结束，获得这句话的起止位置和所有时间戳
                        public void onSentenceEnd(FlowingSpeechSynthesizerResponse response) {
//                    log.debug("name: " + response.getName() + ", status: " + response.getStatus() + ", subtitles: " + response.getObject("subtitles"));
                            log.debug("服务端检测到了一句话的结束>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                        }
                        //流式文本语音合成结束
                        @Override
                        public void onSynthesisComplete(FlowingSpeechSynthesizerResponse response) {
                            // 调用onSynthesisComplete时，表示所有TTS数据已经接收完成，所有文本都已经合成音频并返回。
//                    log.debug("name: " + response.getName() + ", status: " + response.getStatus());
                            log.debug("流式文本语音合成结束>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                            // 向客户端发送音频流
                            WebsocketMessage streamMessage=new WebsocketMessage(taskId,"语音返回结束",2,3);
                            pushMessage(userId,streamMessage.toString());
                        }
                        //收到语音合成的语音二进制数据
                        @Override
                        public void onAudioData(ByteBuffer message) {
                            if(firstRecvBinary) {
                                // 此处计算首包语音流的延迟，收到第一包语音流时，即可以进行语音播放，以提升响应速度（特别是实时交互场景下）。
                                firstRecvBinary = false;
                                log.debug("音频首包返回耗时>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                            }
                            byte[] bytesArray = new byte[message.remaining()];
                            message.get(bytesArray, 0, bytesArray.length);
                            log.debug("音频返回耗时>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                            // 向客户端发送音频流
                            WebsocketMessage streamMessage=new WebsocketMessage(taskId,Base64.encodeToString(bytesArray),1,3);
                            pushMessage(userId,streamMessage.toString());
                        }
                        //收到语音合成的增量音频时间戳
                        @Override
                        public void onSentenceSynthesis(FlowingSpeechSynthesizerResponse response) {
                            //                log.debug("name: " + response.getName() + ", status: " + response.getStatus() + ", subtitles: " + response.getObject("subtitles"));
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
                        }
                    });
                    FlowingSpeechSynthesizer finalFlowingSpeechSynthesizer = flowingSpeechSynthesizer;

                    LLMOperater llm=InstanceBeanUtils.getBean(DoubaoLLMOperaterImpl.class);
                    //调用LLM
                    Flowable<?> response = llm.getAnswerAsync("user1234","小明",text,true);

                    response.subscribe(new DisposableSubscriber<Object>() {
                        @Override
                        public void onNext(Object chatResult) {
                            log.debug("flowable onNext");
                            log.debug("LLM返回耗时>>>>>>>>>>>>>>>>>>>>:" + (System.currentTimeMillis()-st));
                            String content=null;
                            if(chatResult instanceof BotChatCompletionChunk){
                                content=((BotChatCompletionChunk)chatResult).getChoices().get(0).getMessage().getContent().toString().trim();
                            }else if(chatResult instanceof ChatResult){
                                content=((ChatResult)chatResult).getChoices().get(0).getMessages().get(0).getContent().trim();
                            }
                            log.debug(content);
                            if (StringUtils.isNotBlank(content)) {
                                //发送到流式音频合成
                                finalFlowingSpeechSynthesizer.send(content);
                            }
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            log.debug("flowable onError"+throwable.getMessage());
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
                            log.debug("flowable onComplete");
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
            }

            @Override
            public void onStarted(SpeechRecognizerResponse speechRecognizerResponse) {

            }

            @Override
            public void onFail(SpeechRecognizerResponse speechRecognizerResponse) {

            }
        });
    }

    /**
     * 配置错误信息处理
     *
     * @param session
     * @param t
     */
    @OnError
    public void onError(Session session, Throwable t) {
        log.warn("【系统 WebSocket】消息出现错误");
        t.printStackTrace();
    }
    //==========【系统 WebSocket接受、推送消息等方法 —— 具体服务节点推送ws消息】========================================================================================
    

    //==========【采用redis发布订阅模式——推送消息】========================================================================================
    /**
     * 后台发送消息到redis
     *
     * @param message
     */
    public void sendMessage(String message) {
        //log.debug("【系统 WebSocket】广播消息:" + message);
        BaseMap baseMap = new BaseMap();
        baseMap.put("userId", "");
        baseMap.put("message", message);
        jeecgRedisClient.sendMessage(IagentChatWebSocketSingle.REDIS_TOPIC_NAME, baseMap);
    }

    /**
     * 此为单点消息 redis
     *
     * @param userId
     * @param message
     */
    public void sendMessage(String userId, String message) {
        BaseMap baseMap = new BaseMap();
        baseMap.put("userId", userId);
        baseMap.put("message", message);
        jeecgRedisClient.sendMessage(IagentChatWebSocketSingle.REDIS_TOPIC_NAME, baseMap);
    }

    /**
     * 此为单点消息(多人) redis
     *
     * @param userIds
     * @param message
     */
    public void sendMessage(String[] userIds, String message) {
        for (String userId : userIds) {
            sendMessage(userId, message);
        }
    }
    //=======【采用redis发布订阅模式——推送消息】==========================================================================================
    
}