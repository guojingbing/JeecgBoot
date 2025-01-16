package org.jeecg.modules.iagent.nls.common.config;

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
public class AliyunNLSConfig {
    @Value("${nls.appKey:#{null}}")
    private String appKey;
    @Value("${nls.accessKeyId:#{null}}")
    private String accessKeyId;
    @Value("${nls.accessKeySecret:#{null}}")
    private String accessKeySecret;
    @Value("${nls.url:#{null}}")
    private String url;
}
