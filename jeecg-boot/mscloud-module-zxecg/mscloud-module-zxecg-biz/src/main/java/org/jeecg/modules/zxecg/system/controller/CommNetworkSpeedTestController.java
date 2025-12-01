package org.jeecg.modules.zxecg.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.system.entity.CommNetworkSpeedTest;
import org.jeecg.modules.zxecg.system.service.ICommNetworkSpeedTestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 * @description 网络测速
 */

@Slf4j
@Api(tags = "网络测速")
@RestController
@RequestMapping("/zxecg/system/network")
public class CommNetworkSpeedTestController {
    @Resource
    ICommNetworkSpeedTestService networkSpeedTestService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "网络测速-分页列表查询")
    @ApiOperation(value = "网络测速-分页列表查询", notes = "网络测速-分页列表查询")
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
        pageList = networkSpeedTestService.loadListPaging(pageList, likeMap, column, order);
        return Result.OK(pageList);
    }

    @AutoLog(value = "网络测速-测速")
    @ApiOperation(value = "网络测速-测速", notes = "网络测速-测速")
    @GetMapping(value = "/test")
    public Result<?> respTest() {
        return Result.OK();
    }


    @AutoLog(value = "网络测速-测速保存")
    @ApiOperation(value = "网络测速-测速保存", notes = "网络测速-测速保存")
    @PostMapping(value = "/record")
    public Result<?> record(@RequestBody CommNetworkSpeedTest speedTest) {
        //todo 获取登录用户id
        Long loginId=null;
        speedTest.setTestTime(new Timestamp(System.currentTimeMillis()));
        speedTest.setTestUserId(loginId);
        networkSpeedTestService.record(speedTest);
        return Result.OK();
    }
}
