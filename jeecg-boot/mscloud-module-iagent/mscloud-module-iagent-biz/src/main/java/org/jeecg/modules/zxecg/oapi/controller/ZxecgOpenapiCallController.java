package org.jeecg.modules.zxecg.oapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.openapi.base.mapper.SysOpenAuthUserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "开放平台RHC业务相关接口")
@RestController
@RequestMapping("/oapi/rhc")
@Slf4j
public class ZxecgOpenapiCallController {
    @Resource
    private SysOpenAuthUserMapper sysOpenAuthUserMapper;
    @Value("${openapi.prefixUrl}")
    private String pdfReUrl;

    @ApiOperation(value = "用户报告地址", notes = "根据手机号和日期查询用户最近的报告下载地址,参数格式：" +
            "{" +
            "    \"phone\":\"17853589091\",\n" +
            "    \"date\":\"20220109\"\n" +
            "}"
    )
    @AutoLog(value = "用户报告地址", logType = 91)
    @PostMapping(value = "/u/rep")
    public Result<?> doGetRepUrl(@RequestBody String body, HttpServletRequest req) {
//        JSONObject params = JSONObject.parseObject(body);
//        String phone = params.getString("phone");
//        String date = params.getString("date");
//        if (oConvertUtils.isEmpty(phone) || oConvertUtils.isEmpty(date)) {
//            return Result.error("参数错误！");
//        }
//        //查询用户信息
//        LambdaQueryWrapper<BusiCustUser> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(BusiCustUser::getPhone, phone);
//        BusiCustUser user = userService.getOne(queryWrapper);
//        if (user == null) {
//            return Result.error("患者信息不存在，请检查手机号！");
//        }
//        //根据用户id查询报告地址信息
//        Date qDate = DateUtil.parse(date, "yyyyMMdd");
//        Map repInfo = dispService.selectReportByUserId(user.getId(), qDate);
//        if (null == repInfo) {
//            return Result.error("患者暂无报告信息！");
//        }
//        String pdfUrl = (String) repInfo.get("pdfUrl");
//        if (StringUtils.isNotBlank(pdfUrl)) {
//            String baseUrl = pdfReUrl + req.getContextPath();
//            repInfo.put("pdfUrl", baseUrl + pdfUrl);
//            return Result.OK(repInfo);
//        } else {
//            return Result.error("患者暂无报告信息！");
//        }
        return Result.OK();
    }
}