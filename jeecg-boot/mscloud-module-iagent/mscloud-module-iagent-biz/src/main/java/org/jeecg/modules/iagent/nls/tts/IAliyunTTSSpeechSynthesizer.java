package org.jeecg.modules.iagent.nls.tts;

import com.alibaba.nls.client.protocol.tts.FlowingSpeechSynthesizer;
import com.alibaba.nls.client.protocol.tts.FlowingSpeechSynthesizerListener;
import com.alibaba.nls.client.protocol.tts.SpeechSynthesizerListener;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2024-12-05 09:10:34
 **/
public interface IAliyunTTSSpeechSynthesizer {
    /**
     * 执行语音合成
     * @param speechText 要生成语音的文本
     * @param speechSynthesizerListener 处理异步流式输出合成结果
     */
   void process(String speechText,SpeechSynthesizerListener speechSynthesizerListener);
    /**
     * 流式文本转语音
     * @param flowingSpeechSynthesizerListener
     * @return
     */
   FlowingSpeechSynthesizer getFlowingSpeechSynthesizer(FlowingSpeechSynthesizerListener flowingSpeechSynthesizerListener);
}
