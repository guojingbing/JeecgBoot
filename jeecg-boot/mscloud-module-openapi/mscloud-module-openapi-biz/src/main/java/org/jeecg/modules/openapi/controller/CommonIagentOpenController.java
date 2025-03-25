package org.jeecg.modules.openapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.xingchen.model.ChatResult;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionChunk;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionChunk;
import com.volcengine.ark.runtime.service.ArkService;
import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.iagent.nls.common.AliyunNLSTokenUtil;
import org.jeecg.modules.iagent.nls.llm.LLMOperater;
import org.jeecg.modules.iagent.nls.llm.config.AliyunLLMConfig;
import org.jeecg.modules.iagent.nls.tts.IAliyunTTSSpeechSynthesizer;
import org.jeecg.modules.iagent.nls.tts.config.AliyunTTSConfig;
import org.jeecg.modules.zxecg.oapi.service.IZxecgExternalAPICallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 智能体接口
 * @Author: Kingpin
 * @Date: 2024-12-04 14:25:33
 **/
@Api(tags = "通用智能体操作")
@RestController
@RequestMapping("/oapi/iagent")
@Slf4j
public class CommonIagentOpenController {
    @Autowired
    AliyunNLSTokenUtil tokenUtil;

    @ApiOperation(value="获取阿里云nls token", notes = "前端sdk集成阿里云nls时使用，避免在前端存储ak敏感信息")
    @GetMapping("/nls/token")
    @ResponseBody
    public Result<?> getNLSToken() {
        String appkey=tokenUtil.getDefaultNlsAppKey();
        String token=tokenUtil.getToken();
        Map remap=new HashMap<>();
        remap.put("appkey",appkey);
        remap.put("token",token);
        return Result.ok(remap);
    }
}
