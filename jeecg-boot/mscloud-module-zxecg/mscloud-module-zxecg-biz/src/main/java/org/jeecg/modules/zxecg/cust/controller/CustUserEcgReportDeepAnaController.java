package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportDeepAnaService;
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
@Api(tags = "深度分析报告")
@RestController
@RequestMapping("/zxecg/rep/deep")
public class CustUserEcgReportDeepAnaController {
    @Resource
    ICustUserEcgReportDeepAnaService deepAnaService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "深度分析报告-分页列表查询")
    @ApiOperation(value = "深度分析报告-分页列表查询", notes = "深度分析报告-分页列表查询")
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
        pageList = deepAnaService.loadListPaging(pageList, loginUserId, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 请求报告深度分析
     *
     * @return
     */
    @AutoLog(value = "深度分析报告-请求报告深度分析")
    @ApiOperation(value = "深度分析报告-请求报告深度分析", notes = "深度分析报告-请求报告深度分析")
    @PostMapping(value = "/add")
    public Result<?> addDeepAna(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (repId == null) {
            return Result.error("参数异常");
        }
        deepAnaService.addDeepAna(repId);
        return Result.OK("操作成功");
    }

    /**
     * 批量标记深度分析请求处理完成
     *
     * @return
     */
    @AutoLog(value = "深度分析报告-批量标记")
    @ApiOperation(value = "深度分析报告-批量标记", notes = "深度分析报告-批量标记")
    @PostMapping(value = "/finish")
    public Result<?> reportFinish(@RequestBody JSONObject json) {
        JSONArray repIds = json.containsKey("repIds") ? json.getJSONArray("repIds") : null;
        if (repIds == null || repIds.size() < 1) {
            return Result.error("参数异常");
        }
        List<Long> repIdList = JSONObject.parseArray(repIds.toJSONString(), Long.class);
        deepAnaService.reportFinish(repIdList);
        return Result.OK("操作成功");
    }

}
