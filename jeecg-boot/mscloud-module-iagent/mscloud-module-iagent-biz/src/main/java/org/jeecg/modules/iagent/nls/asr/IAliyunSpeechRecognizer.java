package org.jeecg.modules.iagent.nls.asr;

import com.alibaba.nls.client.protocol.asr.SpeechRecognizerListener;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberListener;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2024-12-10 17:04:07
 **/
public interface IAliyunSpeechRecognizer {
    /**
     * 初始化参数
     */
    void init();
    /**
     * nls token是否过期
     * @return
     */
    boolean tokenExpired();
    /**
     * 执行识别
     * @param audioStream
     * @param sampleRate
     * @param isBegin
     * @param isEnd
     * @param listener
     */
    void executeRecognize(byte[] audioStream, Integer sampleRate, boolean isBegin, boolean isEnd, SpeechRecognizerListener listener);
    /**
     * 实时语音识别
     * @param audioStream
     * @param sampleRate
     * @param isBegin
     * @param isEnd
     * @param listener
     */
    void executeRecognizeTranscriber(byte[] audioStream, Integer sampleRate, boolean isBegin, boolean isEnd, SpeechTranscriberListener listener);
    /**
     * 获取状态
     * @return
     */
    Object getState();

    Object getTranscriberState();
    /**
     * 关闭客户端连接
     */
    void shutdown();
}
