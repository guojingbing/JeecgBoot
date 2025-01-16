package org.jeecg.modules.iagent.nls.asr.impl;

import com.alibaba.nls.client.protocol.InputFormatEnum;
import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.asr.*;
import org.jeecg.common.util.SysRedisUtil;
import org.jeecg.modules.iagent.nls.asr.IAliyunSpeechRecognizer;
import org.jeecg.modules.iagent.nls.common.AliyunNLSTokenUtil;
import org.jeecg.modules.iagent.nls.tts.config.AliyunTTSConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2024-12-10 17:04:07
 **/
@Service
public class AliyunSpeechRecognizerImpl implements IAliyunSpeechRecognizer {
    private static final Logger logger = LoggerFactory.getLogger(AliyunSpeechRecognizerImpl.class);
    NlsClient client;
    //一句话识别
    SpeechRecognizer recognizer = null;
    //实时语音识别
    SpeechTranscriber transcriber = null;
    @Autowired
    AliyunTTSConfig aliyunTTSConfig;
    @Autowired
    AliyunNLSTokenUtil tokenUtil;
    @Autowired
    private SysRedisUtil sysRedisUtil;
    private String nlsToken;

    @Override
    public void init() {
        String url = aliyunTTSConfig.getUrl();
        nlsToken=tokenUtil.getToken();
        if(url.isEmpty()) {
            client = new NlsClient(nlsToken);
        }else {
            client = new NlsClient(url, nlsToken);
        }
    }

    @Override
    public boolean tokenExpired(){
        String key=sysRedisUtil.getAliyunNLSTokenCacheKey()+":"+nlsToken;
        Object value=sysRedisUtil.get(key);
        return value==null;
    }

    private static SpeechRecognizerListener getRecognizerListener(int myOrder, String userParam) {
        SpeechRecognizerListener listener = new SpeechRecognizerListener() {
            //识别出中间结果。仅当setEnableIntermediateResult为true时，才会返回该消息。
            @Override
            public void onRecognitionResultChanged(SpeechRecognizerResponse response) {
                //getName是获取事件名称，getStatus是获取状态码，getRecognizedText是语音识别文本。
                System.out.println("name: " + response.getName() + ", status: " + response.getStatus() + ", result: " + response.getRecognizedText());
            }
            //识别完毕
            @Override
            public void onRecognitionCompleted(SpeechRecognizerResponse response) {
                //getName是获取事件名称，getStatus是获取状态码，getRecognizedText是语音识别文本。
                System.out.println("name: " + response.getName() + ", status: " + response.getStatus() + ", result: " + response.getRecognizedText());
            }
            @Override
            public void onStarted(SpeechRecognizerResponse response) {
                System.out.println("myOrder: " + myOrder + "; myParam: " + userParam + "; task_id: " + response.getTaskId());
            }
            @Override
            public void onFail(SpeechRecognizerResponse response) {
                //task_id是调用方和服务端通信的唯一标识，当遇到问题时，需要提供此task_id。
                System.out.println("task_id: " + response.getTaskId() + ", status: " + response.getStatus() + ", status_text: " + response.getStatusText());
            }
        };
        return listener;
    }

//    public void executeRecognizeStart(Integer sampleRate, SpeechRecognizerListener listener){
//        executeRecognize(null, sampleRate, true, false, listener);
//    }
    /**
     * 执行
     * @param audioStream 音频流
     * @param sampleRate 采样率
     * @param isBegin 是否开始
     * @param isEnd 是否最后一段
     * @param listener 异步处理监听
     */
    @Override
    public void executeRecognize(byte[] audioStream, Integer sampleRate, boolean isBegin, boolean isEnd, SpeechRecognizerListener listener) {
        try {
            sampleRate=null==sampleRate?16000:sampleRate;
            if(isBegin){
//                init();
                //传递用户自定义参数
                recognizer = new SpeechRecognizer(client, listener);
                recognizer.setAppKey(aliyunTTSConfig.getAppKey());
                //设置音频编码格式。如果是OPUS文件，请设置为InputFormatEnum.OPUS。
                recognizer.setFormat(InputFormatEnum.PCM);
                //设置音频采样率
                if(sampleRate == 16000) {
                    recognizer.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
                } else if(sampleRate == 8000) {
                    recognizer.setSampleRate(SampleRateEnum.SAMPLE_RATE_8K);
                }
                //设置是否返回中间识别结果
                recognizer.setEnableIntermediateResult(true);
//                //设置是否打开语音检测（即vad）
//                recognizer.addCustomedParam("enable_voice_detection",true);//开启中间结果
//                recognizer.addCustomedParam("max_end_silence",2000);//最大结束静音时长，超出后服务端发送RecognitionCompleted事件结束本次识别

                recognizer.setEnableITN(true);
                //此方法将以上参数设置序列化为JSON发送给服务端，并等待服务端确认。
                recognizer.start();
            }
            if(null!=audioStream&&audioStream.length>0){
                recognizer.send(audioStream, audioStream.length);
            }
            if(isEnd){
                //通知服务端语音数据发送完毕，等待服务端处理完成。
                recognizer.stop();
                logger.error("recognizer stop");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        } finally {
            //关闭连接
            if (isEnd&&null != recognizer) {
                recognizer.close();
                client.shutdown();
            }
        }
    }

    public void executeRecognizeTranscriber(byte[] audioStream, Integer sampleRate, boolean isBegin, boolean isEnd, SpeechTranscriberListener listener) {
        try {
            if(isBegin) {
                sampleRate = null == sampleRate ? 16000 : sampleRate;
                //创建实例、建立连接。
                transcriber = new SpeechTranscriber(client, listener);
                transcriber.setAppKey(aliyunTTSConfig.getAppKey());
                //输入音频编码方式。
                transcriber.setFormat(InputFormatEnum.PCM);
                //设置音频采样率
                if (sampleRate == 16000) {
                    transcriber.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
                } else if (sampleRate == 8000) {
                    transcriber.setSampleRate(SampleRateEnum.SAMPLE_RATE_8K);
                }
                //是否返回中间识别结果。
                transcriber.setEnableIntermediateResult(true);
                //是否生成并返回标点符号。
                transcriber.setEnablePunctuation(true);
                //是否将返回结果规整化，比如将一百返回为100。
                transcriber.setEnableITN(true);

                //设置是否语义断句。
                transcriber.addCustomedParam("enable_semantic_sentence_detection", false);
                //开启语义断句后该设置无效，设置vad断句参数。默认值：800ms，有效值：200ms～6000ms。
                //transcriber.addCustomedParam("max_sentence_silence", 1000);
                //设置是否开启过滤语气词，即声音顺滑。
                //transcriber.addCustomedParam("disfluency",true);
                //设置是否开启词模式。
                //transcriber.addCustomedParam("enable_words",true);
                //设置vad噪音阈值参数，参数取值为-1～+1，如-0.9、-0.8、0.2、0.9。
                //取值越趋于-1，判定为语音的概率越大，亦即有可能更多噪声被当成语音被误识别。
                //取值越趋于+1，判定为噪音的越多，亦即有可能更多语音段被当成噪音被拒绝识别。
                //该参数属高级参数，调整需慎重和重点测试。
                //transcriber.addCustomedParam("speech_noise_threshold",0.3);
                //设置训练后的定制语言模型id。
                //transcriber.addCustomedParam("customization_id","你的定制语言模型id");
                //设置训练后的定制热词id。
                //transcriber.addCustomedParam("vocabulary_id","你的定制热词id");

                //此方法将以上参数设置序列化为JSON发送给服务端，并等待服务端确认。
                transcriber.start();
            }
            if(null!=audioStream&&audioStream.length>0){
                transcriber.send(audioStream, audioStream.length);
            }
            //通知服务端语音数据发送完毕，等待服务端处理完成。
            long now = System.currentTimeMillis();
            logger.info("ASR wait for complete");
            if(isEnd) {
                transcriber.stop();
            }
            logger.info("ASR latency : " + (System.currentTimeMillis() - now) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        } finally {
            if (isEnd&&null != transcriber) {
                transcriber.close();
            }
        }
    }

    @Override
    public Object getState(){
        return recognizer.getState();
    }

    @Override
    public Object getTranscriberState(){
        return transcriber.getState();
    }

    @Override
    public void shutdown() {
        client.shutdown();
        client = null;
    }
}
