package org.jeecg.modules.iagent.nls.llm.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * DOUBAO大模型配置
 */
@Slf4j
@Configuration
@Data
public class DoubaoLLMConfig {
    @Value("${nls.llm.doubao.apiKey:#{null}}")
    private String apiKey;
    @Value("${nls.llm.doubao.url:#{null}}")
    private String url;
    @Value("${nls.llm.doubao.modelId:#{null}}")
    private String modelId;
    @Value("${nls.llm.doubao.characterId:#{null}}")
    private String characterId;
    @Value("${nls.llm.doubao.characterName:#{null}}")
    private String characterName;

    @Value("${nls.llm.doubao.ecg.characterId:#{null}}")
    private String ecgCharacterId;
    @Value("${nls.llm.doubao.ecg.modelId:#{null}}")
    private String ecgModelId;

    @Value("${nls.llm.doubao.elansen.characterId:#{null}}")
    private String elansenCharacterId;
    @Value("${nls.llm.doubao.elansen.modelId:#{null}}")
    private String elansenModelId;
    @Value("${nls.llm.doubao.elansen.ocr.characterId:#{null}}")
    private String elansenOcrCharacterId;
    @Value("${nls.llm.doubao.elansen.ocr.modelId:#{null}}")
    private String elansenOcrModelId;
}
