package org.jeecg.modules.openapi.oapi.controller;

import org.jeecg.modules.openapi.base.entity.SysOpenAuthUser;
import org.jeecg.modules.openapi.base.service.ISysOpenAuthUserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "开放平台通用接口")
@RestController
@RequestMapping("/oapi")
@Slf4j
public class OpenAPIController {
    @Autowired
    ISysOpenAuthUserService SOAUserSer;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "OPENAPI鉴权接口", notes = "API身份验证通过授权的apiKey和apiSecret获取token，token有效期30分钟，调用方需要自主实现token缓存策略。通过body传参，参数格式：\n" +
            "{\n" +
            "    \"accessKey\":\"111\",\n" +
            "    \"accessSecret\":\"111\"\n" +
            "}")
    @PostMapping(value = "/token")
    public Result<?> doPostVerification(@RequestBody String body, HttpServletRequest req) {
        Map map = new HashMap();
        JSONObject params = JSONObject.parseObject(body);
        String apiKey = params.getString("accessKey");
        String apiSecret = params.getString("accessSecret");
        SysOpenAuthUser soaUser = SOAUserSer.getOne(new LambdaQueryWrapper<SysOpenAuthUser>().eq(SysOpenAuthUser::getApiKey, apiKey).eq(SysOpenAuthUser::getApiSecret, apiSecret));
        if (soaUser == null) {
            return Result.error("身份验证失败，请确认输入的accessKey和accessSecret");
        }

        if (soaUser.getAuthStatus() == null || soaUser.getAuthStatus().intValue() != 1) {
            return Result.error("账号状态异常，身份验证失败");
        }
        String token = JwtUtil.getOAPITokenWithInfo(String.valueOf(soaUser.getId()), (int) JwtUtil.EXPIRE_TIME / 1000, redisUtil);
        map.put("token", token);
        map.put("expiredTime", JwtUtil.getTokenExpiredTime(token));
        String refreshToken = JwtUtil.getOAPIRefreshTokenWithInfo(String.valueOf(soaUser.getId()), (int) JwtUtil.REFRESH_EXPIRE_TIME / 1000, redisUtil);
        map.put("refreshToken", refreshToken);
        map.put("refreshExpiredTime", JwtUtil.getTokenExpiredTime(refreshToken));

        return Result.OK(map);
    }

    @ApiOperation(value = "OPENAPI鉴权接口", notes = "API调用身份授权验证并获取token，token有效期30分钟，调用方需要自主实现token缓存策略。通过body传参，参数格式：\n" +
            "{\n" +
            "    \"accessKey\":\"111\",\n" +
            "    \"timestamp\":1673942845000,\n" +
            "    \"sign\":\"xxxxxx\"\n" +
            "}\n生成sign值将accessKey、timestamp、accessSecret对应的字符串按此固定顺序拼接后，使用SHA256算法加密。\n" +
            "示例java代码格式:String sign = sha256(appkey+timestamp+mastersecret)")
    @PostMapping(value = "/auth")
    public Result<?> doPostAuth(@RequestBody String body, HttpServletRequest req) {
        Map map = new HashMap();
        JSONObject params = JSONObject.parseObject(body);

        String sign = params.getString("sign");
        String accessKey = params.getString("accessKey");
        String timestamp = params.getString("timestamp");

        SysOpenAuthUser soaUser = SOAUserSer.getOne(new LambdaQueryWrapper<SysOpenAuthUser>().eq(SysOpenAuthUser::getApiKey, accessKey));
        if (soaUser == null) {
            return Result.error("账号信息不存在，身份验证失败，请确认输入合法的accessKey");
        }

        String accessSecret=soaUser.getApiSecret();
        String signStr=accessKey+accessSecret+timestamp;
        String chkSign= DigestUtils.sha256Hex(signStr);

        if(!chkSign.equalsIgnoreCase(sign)){
            return Result.error("签名信息异常，身份验证失败，请确认签名");
        }

        if (soaUser.getAuthStatus() == null || soaUser.getAuthStatus().intValue() != 1) {
            return Result.error("账号状态异常，身份验证失败");
        }

        String token = JwtUtil.getOAPITokenWithInfo(String.valueOf(soaUser.getId()), (int) JwtUtil.EXPIRE_TIME / 1000, redisUtil);
        map.put("token", token);
        map.put("expiredTime", JwtUtil.getTokenExpiredTime(token));
        String refreshToken = JwtUtil.getOAPIRefreshTokenWithInfo(String.valueOf(soaUser.getId()), (int) JwtUtil.REFRESH_EXPIRE_TIME / 1000, redisUtil);
        map.put("refreshToken", refreshToken);
        map.put("refreshExpiredTime", JwtUtil.getTokenExpiredTime(refreshToken));

        return Result.OK(map);
    }

    @ApiOperation(value = "刷新token接口", notes = "当token过期是通过refreshToken来重新获取token信息")
    @PostMapping(value = "/refreshToken")
    public Result<?> refreshToken(@RequestBody String body, HttpServletRequest req) {
        Map map = new HashMap();
        JSONObject params = JSONObject.parseObject(body);
        String param = params.getString("refreshToken");
        if (StringUtils.isEmpty(param)) {
            return Result.error("参数错误");
        }
        if (JwtUtil.verfiyOpenapiRefreshTokenExp(param, redisUtil)) {
            throw new AuthenticationException("token已失效，请重新获取");
        }
        String userName = JwtUtil.getUsername(param);
        String token = JwtUtil.getOAPITokenWithInfo(userName, (int) JwtUtil.EXPIRE_TIME / 1000, redisUtil);
        map.put("token", token);
        map.put("expiredTime", JwtUtil.getTokenExpiredTime(token));
        String refreshToken = JwtUtil.getOAPIRefreshTokenWithInfo(userName, (int) JwtUtil.REFRESH_EXPIRE_TIME / 1000, redisUtil);
        map.put("refreshToken", refreshToken);
        map.put("refreshExpiredTime", JwtUtil.getTokenExpiredTime(refreshToken));
        return Result.OK(map);
    }
}