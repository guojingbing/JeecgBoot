package org.jeecg.modules.iagent.nls.tts.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云TTS配置
 */
@Slf4j
@Configuration
@Data
public class AliyunTTSConfig {
    @Value("${nls.tts.appKey:#{null}}")
    private String appKey;
    @Value("${nls.tts.accessKeyId:#{null}}")
    private String accessKeyId;
    @Value("${nls.tts.accessKeySecret:#{null}}")
    private String accessKeySecret;
    @Value("${nls.tts.url:#{null}}")
    private String url;
    //音色
    @Value("${nls.tts.voice:#{'siyue'}}")
    private String voice;
    //音量，范围是0~100，默认是50。
    @Value("${nls.tts.volume:#{50}}")
    private Integer volume;
    //语调，范围是-500~500，可选，默认是0。
    @Value("${nls.tts.pitchRate:#{0}}")
    private Integer pitchRate;
    //语速，范围是-500~500，默认是0。
    @Value("${nls.tts.speechRate:#{0}}")
    private Integer speechRate;
}
