package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.service.ICustUserMcOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/9
 */

@Slf4j
@Api(tags = "分析解读")
@RestController
@RequestMapping("/zxecg/mc")
public class CustUserMcOrderController {
    @Resource
    ICustUserMcOrderService mcOrderService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "分析解读-分页列表查询")
    @ApiOperation(value = "分析解读-分页列表查询", notes = "分析解读-分页列表查询")
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
        Long userId = null;
        if (null != json && json.containsKey("userId")) {
            userId = json.getLong("userId");
            likeMap.remove("userId");
        }
        pageList = mcOrderService.loadListPaging(pageList, loginUserId, userId, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 解读
     *
     * @param json
     * @return
     */
    @AutoLog(value = "分析解读-解读")
    @ApiOperation(value = "分析解读-解读", notes = "分析解读-解读")
    @PostMapping(value = "/interpret")
    public Result<?> interpretRep(@RequestBody JSONObject json) {
        Long dispId = json.getLong("dispId");
        if (null == dispId) {
            return Result.error("请选择解读的订单");
        }
        String conclusion = json.getString("conclusion");
        if (StringUtils.isBlank(conclusion)) {
            return Result.error("请填写咨询总结");
        }
        if (conclusion.length() > 200) {
            return Result.error("咨询总结不能超过200个字");
        }
        Result<?> result = mcOrderService.interpretRep(dispId, conclusion);
        if (null != result) {
            return result;
        }
        return Result.OK("操作成功");
    }
}
