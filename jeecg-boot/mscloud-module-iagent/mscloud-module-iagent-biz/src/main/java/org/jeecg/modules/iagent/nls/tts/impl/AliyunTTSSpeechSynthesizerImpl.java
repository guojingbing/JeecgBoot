package org.jeecg.modules.iagent.nls.tts.impl;

import com.alibaba.nls.client.AccessToken;
import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.OutputFormatEnum;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.tts.FlowingSpeechSynthesizer;
import com.alibaba.nls.client.protocol.tts.FlowingSpeechSynthesizerListener;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizerListener;
import org.jeecg.modules.iagent.nls.tts.IAliyunTTSSpeechSynthesizer;
import org.jeecg.modules.iagent.nls.tts.config.AliyunTTSConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2024-12-05 09:10:34
 **/
@Service
public class AliyunTTSSpeechSynthesizerImpl implements IAliyunTTSSpeechSynthesizer {
    private static final Logger logger = LoggerFactory.getLogger(AliyunTTSSpeechSynthesizerImpl.class);
    private static long startTime;
    private String appKey;
    private NlsClient client;
    @Autowired
    private AliyunTTSConfig aliyunTTSConfig;

//    public AliyunTTSSpeechSynthesizer() {
//        this.appKey = aliyunTTSConfig.getAppKey();
//        String accessKeyId = aliyunTTSConfig.getAccessKeyId();
//        String accessKeySecret = aliyunTTSConfig.getAccessKeySecret();
//        String url = aliyunTTSConfig.getUrl();
//
//        AccessToken accessToken = new AccessToken(accessKeyId, accessKeySecret);
//        try {
//            accessToken.apply();
//            if(url.isEmpty()) {
//                client = new NlsClient(accessToken.getToken());
//            }else {
//                client = new NlsClient(url, accessToken.getToken());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public void init(){
        this.appKey = aliyunTTSConfig.getAppKey();
        String accessKeyId = aliyunTTSConfig.getAccessKeyId();
        String accessKeySecret = aliyunTTSConfig.getAccessKeySecret();
        String url = aliyunTTSConfig.getUrl();

        AccessToken accessToken = new AccessToken(accessKeyId, accessKeySecret);
        try {
            accessToken.apply();
            if(url.isEmpty()) {
                client = new NlsClient(accessToken.getToken());
            }else {
                client = new NlsClient(url, accessToken.getToken());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public AliyunTTSSpeechSynthesizer(String appKey, String accessKeyId, String accessKeySecret, String url) {
//        this.appKey = appKey;
//        AccessToken accessToken = new AccessToken(accessKeyId, accessKeySecret);
//        try {
//            accessToken.apply();
//            System.out.println("get token: " + accessToken.getToken() + ", expire time: " + accessToken.getExpireTime());
//            if(url.isEmpty()) {
//                client = new NlsClient(accessToken.getToken());
//            }else {
//                client = new NlsClient(url, accessToken.getToken());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 执行语音合成
     * @param speechText 要生成语音的文本
     * @param speechSynthesizerListener 处理异步流式输出合成结果
     */
    public void process(String speechText,SpeechSynthesizerListener speechSynthesizerListener) {
        init();
        com.alibaba.nls.client.protocol.tts.SpeechSynthesizer synthesizer = null;
        try {
            //创建实例，建立连接。
            synthesizer = new com.alibaba.nls.client.protocol.tts.SpeechSynthesizer(client, speechSynthesizerListener);
            synthesizer.setAppKey(appKey);
            //设置返回音频的编码格式
            synthesizer.setFormat(OutputFormatEnum.WAV);
            //设置返回音频的采样率
            synthesizer.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
            //发音人
            synthesizer.setVoice("siyue");
            //语调，范围是-500~500，可选，默认是0。
            synthesizer.setPitchRate(0);
            //语速，范围是-500~500，默认是0。
            synthesizer.setSpeechRate(0);
            //设置用于语音合成的文本
            synthesizer.setText(speechText);
            //是否开启字幕功能（返回相应文本的时间戳），默认不开启，需要注意并非所有发音人都支持该参数。
            synthesizer.addCustomedParam("enable_subtitle", false);
            //此方法将以上参数设置序列化为JSON格式发送给服务端，并等待服务端确认。
            long start = System.currentTimeMillis();
            synthesizer.start();
            logger.info("tts start latency " + (System.currentTimeMillis() - start) + " ms");
            AliyunTTSSpeechSynthesizerImpl.startTime = System.currentTimeMillis();
            //等待语音合成结束
            synthesizer.waitForComplete();
            logger.info("tts stop latency " + (System.currentTimeMillis() - start) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            if (null != synthesizer) {
                synthesizer.close();
            }
        }
    }

    public FlowingSpeechSynthesizer getFlowingSpeechSynthesizer(FlowingSpeechSynthesizerListener flowingSpeechSynthesizerListener){
        init();
        FlowingSpeechSynthesizer synthesizer = null;
        try {
            //创建实例，建立连接。
            synthesizer = new FlowingSpeechSynthesizer(client, flowingSpeechSynthesizerListener);
            synthesizer.setAppKey(appKey);
            //设置返回音频的编码格式。
            synthesizer.setFormat(OutputFormatEnum.PCM);
            //设置返回音频的采样率。
            synthesizer.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
            //发音人。
            synthesizer.setVoice("siyue");
            //音量，范围是0~100，可选，默认50。
            synthesizer.setVolume(100);
            //语调，范围是-500~500，可选，默认是0。
            synthesizer.setPitchRate(0);
            //语速，范围是-500~500，默认是0。
            synthesizer.setSpeechRate(0);
            //此方法将以上参数设置序列化为JSON发送给服务端，并等待服务端确认。
            synthesizer.start();
            //设置连续两次发送文本的最小时间间隔（毫秒），如果当前调用send时距离上次调用时间小于此值，则会阻塞并等待直到满足条件再发送文本
            synthesizer.setMinSendIntervalMS(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return synthesizer;
    }
    /**
     * 关闭
     */
    public void shutdown() {
        client.shutdown();
    }

    public static void main(String[] args) throws Exception {
    }
}
