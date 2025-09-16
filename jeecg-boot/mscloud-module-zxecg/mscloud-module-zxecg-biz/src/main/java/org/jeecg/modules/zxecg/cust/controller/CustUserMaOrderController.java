package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.service.ICustUserMaOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/8
 */

@Slf4j
@Api(tags = "分析订单")
@RestController
@RequestMapping("/zxecg/ma")
public class CustUserMaOrderController {
    @Resource
    ICustUserMaOrderService maOrderService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "分析订单-分页列表查询")
    @ApiOperation(value = "分析订单-分页列表查询", notes = "分析订单-分页列表查询")
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
        Integer audit = !json.containsKey("audit") ? null : json.getInteger("audit");
        Integer orderType = json.getInteger("orderType");
        likeMap.remove("orderType");
        likeMap.remove("audit");
        pageList = maOrderService.loadListPaging(pageList, loginUserId, audit, orderType, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 通过/驳回
     *
     * @param json
     * @return
     */
    @AutoLog(value = "分析订单-通过/驳回")
    @ApiOperation(value = "分析订单-通过/驳回", notes = "分析订单-通过/驳回")
    @GetMapping(value = "/audit")
    public Result<?> auditOrder(@RequestBody JSONObject json) {
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        Long orderId = json.getLong("orderId");
        if (null == orderId) {
            return Result.error("请选择要操纵的订单");
        }
        Integer oper = json.getInteger("oper");
        String desc = json.getString("desc");
        Result<?> result = maOrderService.auditOrder(orderId, oper, desc, loginUserId);
        if (null != result) {
            return result;
        }
        return Result.OK("操作成功");
    }
}
