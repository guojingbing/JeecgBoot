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
}
