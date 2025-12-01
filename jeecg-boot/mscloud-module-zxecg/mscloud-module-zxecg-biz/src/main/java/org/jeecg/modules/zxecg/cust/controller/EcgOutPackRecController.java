package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.service.IEcgOutPackRecDetailService;
import org.jeecg.modules.zxecg.cust.service.IEcgOutPackRecService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/27
 */

@Slf4j
@Api(tags = "ECG批量下载")
@RestController
@RequestMapping("/zxecg/pack")
public class EcgOutPackRecController {
    @Resource
    IEcgOutPackRecService outPackRecService;
    @Resource
    IEcgOutPackRecDetailService detailService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "ECG批量下载-分页列表查询")
    @ApiOperation(value = "ECG批量下载-分页列表查询", notes = "ECG批量下载-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(@RequestBody(required = false) JSONObject json,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        Page<Map> pageList = new Page<>(pageNo, pageSize);
        String column = req.getParameter("column");
        String order = req.getParameter("order");
        Map<String, Object> likeMap = new HashMap<>();
        if (null != json) {
            json.keySet().forEach(key -> {
                likeMap.put(key, json.get(key));
            });
        }
        pageList = outPackRecService.loadListPaging(pageList, loginUserId, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 下载列表
     *
     * @param
     * @param packId
     * @return
     */
    @AutoLog(value = "ECG批量下载-下载列表")
    @ApiOperation(value = "ECG批量下载-下载列表", notes = "ECG批量下载-下载列表")
    @GetMapping(value = "/details")
    public Result<?> getDetailList(@RequestParam Long packId) {
        List<Map<String, Object>> detailList = detailService.getListByPackId(packId);
        return Result.OK(detailList);
    }
}
