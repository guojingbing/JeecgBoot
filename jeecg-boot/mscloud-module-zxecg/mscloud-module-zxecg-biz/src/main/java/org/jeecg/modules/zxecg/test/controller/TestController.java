package org.jeecg.modules.zxecg.test.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.zxecg.test.entity.TestInfo;
import org.jeecg.modules.zxecg.test.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Api(tags = "正心ECG医生登录")
@RestController
@RequestMapping("/zxecg")
public class TestController {
	@Autowired
	private ITestService testService;
    @Autowired
    private RedisUtil redisUtil;

    @Value(value = "${zxecg.sys.preUrl}")
    private String preUrl;

	@ApiOperation(value = "test", notes = "测试接")
//    @Operation(summary="登录接口")
    @RequestMapping(value = "/ignore/test/list", method = RequestMethod.POST)
	public Result<?> test(HttpServletRequest request){
		log.info(" ---我被调用了--- ");
        Result<JSONObject> result = new Result<JSONObject>();
        List<TestInfo> list=testService.getTestInfoList();

        JSONObject data = new JSONObject();
        data.put("list", list);
        result.setResult(data);
        return result;
	}
}
