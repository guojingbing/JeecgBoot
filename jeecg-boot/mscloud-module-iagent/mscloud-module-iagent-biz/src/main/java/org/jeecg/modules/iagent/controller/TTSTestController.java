package org.jeecg.modules.iagent.controller;

import com.alibaba.nls.client.protocol.tts.SpeechSynthesizerListener;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizerResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.iagent.nls.llm.LLMOperater;
import org.jeecg.modules.iagent.nls.llm.config.AliyunLLMConfig;
import org.jeecg.modules.iagent.nls.tts.IAliyunTTSSpeechSynthesizer;
import org.jeecg.modules.iagent.nls.tts.config.AliyunTTSConfig;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 文本转语音处理
 * @Author: Kingpin
 * @Date: 2024-12-04 14:25:33
 **/
@Api(tags = "TTS示例")
@RestController
@RequestMapping("/iagenttest")
@Slf4j
public class TTSTestController {
    @Autowired
    AliyunLLMConfig llmConfig;
    @Autowired
    AliyunTTSConfig ttsConfig;
    @Resource(name="xingchenLLMOperaterImpl")
    LLMOperater llm;
    @Resource
    IAliyunTTSSpeechSynthesizer speech;

    @GetMapping(value = "/test/asyncout/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<?>> stream() {
        Flux<String> stream = Flux.interval(Duration.ofMillis(1000)).map(seq -> "Event " + seq) .log();
        Publisher publisher=Flux.range(1,10);
//        Flux<Object> stream = new Flux<Object>() {
//            @Override
//            public void subscribe(CoreSubscriber<? super Object> coreSubscriber) {
//
//            }
//        };
        return ResponseEntity.ok().body(stream);
    }

    @GetMapping("/test/asyncout/emitter")
    @ResponseBody
    public ResponseBodyEmitter asyncOut() {
        // 创建一个 ResponseBodyEmitter 实例
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        // 模拟一个耗时任务
        new Thread(() -> {
            try {
                speech.process("欢迎使用阿里巴巴智能语音合成服务，您可以说北京明天天气怎么样啊，计算首包语音流的延迟，收到第一包语音流时，即可以进行语音播放，以提升响应速度（特别是实时交互场景下），调用onComplete时表示所有TTS数据已接收完成，因此为整个合成数据的延迟。该延迟可能较大，不一定满足实时场景",new SpeechSynthesizerListener() {
                    private boolean firstRecvBinary = true;
                    //语音合成结束
                    @Override
                    public void onComplete(SpeechSynthesizerResponse response) {
                        //调用onComplete时表示所有TTS数据已接收完成，因此为整个合成数据的延迟。该延迟可能较大，不一定满足实时场景。
                        emitter.complete(); // 任务完成，关闭连接
                    }
                    //语音合成的语音二进制数据
                    @Override
                    public void onMessage(ByteBuffer message) {
                        try {
                            if(firstRecvBinary) {
                                //计算首包语音流的延迟，收到第一包语音流时，即可以进行语音播放，以提升响应速度（特别是实时交互场景下）。
                                firstRecvBinary = false;
                            }
                            byte[] bytesArray = new byte[message.remaining()];
                            message.get(bytesArray, 0, bytesArray.length);
                            // 向客户端发送进度
                            emitter.send(bytesArray);
                            System.out.println("received:"+bytesArray.length);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFail(SpeechSynthesizerResponse response){
                        //task_id是调用方和服务端通信的唯一标识，当遇到问题时需要提供task_id以便排查。
                        emitter.completeWithError(new Exception("发生异常"+response.getStatusText())); // 出现异常时通知客户端
                    }
                });
//                speech.shutdown();
            } catch (Exception e) {
                emitter.completeWithError(e); // 出现异常时通知客户端
            }
        }).start();

        return emitter; // 返回 Emitter
    }

    @GetMapping("/test/asyncout/srb")
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> asyncOutStream() {
        StreamingResponseBody stream = out -> {
            String message = "streamingResponse";
            for (int i = 0; i < 1000; i++) {
                try {
                    out.write(((message + i) + "\r\n").getBytes());
                    out.write("\r\n".getBytes());
                    System.out.println(((message + i) + "\r\n").getBytes());
                    //调用一次flush就会像前端写入一次数据
                    out.flush();
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(stream);
    }
}
