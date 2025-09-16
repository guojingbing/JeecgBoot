package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.entity.CustUserInfo;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgExceptionLogService;
import org.jeecg.modules.zxecg.cust.service.ICustUserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 */

@Slf4j
@Api(tags = "APP日志记录")
@RestController
@RequestMapping("/zxecg/applog")
public class CustUserEcgAppLogController {
    @Resource
    ICustUserEcgExceptionLogService logService;
    @Resource
    ICustUserInfoService userInfoService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "APP日志记录-分页列表查询")
    @ApiOperation(value = "APP日志记录-分页列表查询", notes = "APP日志记录-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(@RequestBody(required = false) JSONObject json,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        Page<Map> pageList = new Page<>(pageNo, pageSize);
        String column = req.getParameter("column");
        String order = req.getParameter("order");
        Map<String, Object> likeMap = new HashMap<>();
        if (null != json) {
            json.keySet().forEach(key -> {
                likeMap.put(key, json.get(key));
            });
        }
        pageList = logService.loadListPaging(pageList, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 日志请求
     *
     * @param json
     * @return
     */
    @AutoLog(value = "APP日志记录-日志请求")
    @ApiOperation(value = "APP日志记录-日志请求", notes = "APP日志记录-日志请求")
    @GetMapping(value = "/rep")
    public Result<?> reqAppLogDaily(@RequestBody JSONObject json) {
        String logDate = json.getString("logDate");
        if (StringUtils.isBlank(logDate)) {
            return Result.error("请填写日志日期");
        }
        String userNo = json.getString("userNo");
        if (StringUtils.isBlank(userNo)) {
            return Result.error("请填写用户账号或手机号");
        }
        CustUserInfo custUserInfo = userInfoService.getUserInfoByNoOrTel(userNo,userNo);
        if (null == custUserInfo) {
            return Result.error("用户不存在");
        }
        //todo
        return Result.OK();
    }
}
