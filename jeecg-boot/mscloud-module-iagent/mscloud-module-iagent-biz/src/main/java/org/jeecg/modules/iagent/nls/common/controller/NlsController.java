package org.jeecg.modules.iagent.nls.common.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.iagent.nls.common.AliyunNLSTokenUtil;
import org.jeecg.modules.iagent.nls.llm.LLMOperater;
import org.jeecg.modules.iagent.nls.llm.config.AliyunLLMConfig;
import org.jeecg.modules.iagent.nls.tts.IAliyunTTSSpeechSynthesizer;
import org.jeecg.modules.iagent.nls.tts.config.AliyunTTSConfig;
import org.jeecg.modules.zxecg.oapi.service.IZxecgExternalAPICallService;
import org.jeecg.modules.zxecg.vo.ZxecgUserReportVo;
import org.jeecg.modules.zxecg.vo.ZxecgUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 智能体接口
 * @Author: Kingpin
 * @Date: 2024-12-04 14:25:33
 **/
@Api(tags = "nls操作")
@RestController
@RequestMapping("/oapi/nls")
@Slf4j
public class NlsController {
    @Autowired
    AliyunNLSTokenUtil tokenUtil;

//    @GetMapping("/token")
//    @ResponseBody
//    public Result<?> getNLSToken() {
//        String appkey=tokenUtil.getDefaultNlsAppKey();
//        String token=tokenUtil.getToken();
//        Map remap=new HashMap<>();
//        remap.put("appkey",appkey);
//        remap.put("token",token);
//        return Result.ok(remap);
//    }
}
