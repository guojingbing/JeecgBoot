package org.jeecg.modules.iagent.nls.llm.config;

import com.alibaba.xingchen.ApiClient;
import com.alibaba.xingchen.auth.HttpBearerAuth;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云通义星尘LLM配置
 */
@Slf4j
@Configuration
@Data
public class DoubaoLLMConfig {
    @Value("${nls.llm.doubao.apiKey:#{null}}")
    private String apiKey;
    @Value("${nls.llm.doubao.url:#{null}}")
    private String url;
    @Value("${nls.llm.doubao.characterId:#{null}}")
    private String characterId;
    @Value("${nls.llm.doubao.characterName:#{null}}")
    private String characterName;

    public ApiClient getApiClient(String sseType){
        ApiClient apiClient=new ApiClient();
        apiClient.setBasePath(url);
        apiClient.addDefaultHeader("X-DashScope-SSE", sseType); // 开启SSE输出
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth authorization = (HttpBearerAuth) apiClient.getAuthentication("Authorization");
        authorization.setBearerToken(apiKey);
        return apiClient;
    }
}
