package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReport;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportCheckOrder;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportCheckOrderFrag;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportCheckOrderFragService;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportCheckOrderService;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/1
 */

@Slf4j
@Api(tags = "动态/遥测报告")
@RestController
@RequestMapping("/zxecg/check")
public class CustUserEcgReportCheckOrderController {
    @Resource
    ICustUserEcgReportCheckOrderService checkOrderService;
    @Resource
    ICustUserEcgReportService reportService;
    @Resource
    ICustUserEcgReportCheckOrderFragService fragService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "动态/遥测报告-分页列表查询")
    @ApiOperation(value = "动态/遥测报告-分页列表查询", notes = "动态/遥测报告-分页列表查询")
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
        //根据该字段区分查询的是动态报告还是遥测报告
        Integer orderType = json.getInteger("orderType");
        likeMap.remove("orderType");
        pageList = checkOrderService.loadListPaging(pageList, loginUserId, orderType, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 删除
     *
     * @param json
     * @return
     */
    @AutoLog(value = "动态/遥测报告-删除")
    @ApiOperation(value = "动态/遥测报告-删除", notes = "动态/遥测报告-删除")
    @DeleteMapping(value = "/del")
    public Result<?> delOrder(@RequestBody JSONObject json) {
        Long id = json.getLong("id");
        if (null == id) {
            return Result.error("请选择要删除的检查单");
        }
        CustUserEcgReportCheckOrder checkOrder = checkOrderService.getById(id);
        if (null == checkOrder) {
            return Result.error("检查单不存在");
        }
        checkOrderService.removeById(id);
        return Result.OK("操作成功");
    }

    /**
     * 确费
     *
     * @param json
     * @return
     */
    @AutoLog(value = "遥测报告-确费")
    @ApiOperation(value = "遥测报告-确费", notes = "遥测报告-确费")
    @PostMapping(value = "/fee")
    public Result<?> confirmFee(@RequestBody JSONObject json) {
        Long id = json.getLong("id");
        if (null == id) {
            return Result.error("请选择要确费的检查单");
        }
        CustUserEcgReportCheckOrder checkOrder = checkOrderService.getById(id);
        if (null == checkOrder) {
            return Result.error("检查单不存在");
        }
        if (2 == checkOrder.getOrderType()) {
            return Result.error("该检查单类型不用进行确费操作");
        }
        if (0 != checkOrder.getFeeConfirmStatus()) {
            return Result.error("当前检查单已确费");
        }
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        checkOrder.setFeeConfirmStatus(1);
        checkOrder.setFeeConfirmUser(loginUserId);
        checkOrder.setFeeConfirmTime(new Timestamp(System.currentTimeMillis()));
        checkOrder.setOperTime(new Timestamp(System.currentTimeMillis()));
        checkOrderService.updateById(checkOrder);
        return Result.OK("操作成功");
    }

    /**
     * 加载可关联选择的报告
     *
     * @param json
     * @return
     */
    @AutoLog(value = "遥测报告-加载可关联选择的报告")
    @ApiOperation(value = "遥测报告-加载可关联选择的报告", notes = "遥测报告-加载可关联选择的报告")
    @PostMapping(value = "/connectList")
    public Result<?> connectRepList(@RequestBody JSONObject json) {
        Long userId = json.getLong("userId");
        Integer repType = json.getInteger("repType");
        if (null == userId || null == repType) {
            return Result.error("参数错误");
        }
        List<Map> list = reportService.connectRepList(userId, repType);
        return Result.OK(list);
    }

    /**
     * 关联报告
     *
     * @param json
     * @return
     */
    @AutoLog(value = "遥测报告-关联报告")
    @ApiOperation(value = "遥测报告-关联报告", notes = "遥测报告-关联报告")
    @PostMapping(value = "/connect")
    public Result<?> connectRep(@RequestBody JSONObject json) {
        Long id = json.getLong("id");
        Long repId = json.getLong("repId");
        if (null == id || null == repId) {
            return Result.error("参数错误");
        }
        CustUserEcgReportCheckOrder checkOrder = checkOrderService.getById(id);
        if (null == checkOrder) {
            return Result.error("检查单记录不存在");
        }
        CustUserEcgReport report = reportService.getById(repId);
        if (null == report) {
            return Result.error("报告不存在");
        }
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        checkOrder.setHolterRepOperUser(loginUserId);
        checkOrder.setHolterRepId(report.getRepId());
        checkOrder.setHolterRepStatus(20);
        checkOrder.setHolterRepOperTime(new Timestamp(System.currentTimeMillis()));
        checkOrderService.updateById(checkOrder);
        return Result.OK("操作成功");
    }

    /**
     * 片段標題修改
     *
     * @param json
     * @return
     */
    @AutoLog(value = "片段标题修改")
    @ApiOperation(value = "片段标题修改", notes = "片段标题修改")
    @PostMapping(value = "/frag/title")
    public Result<?> fragTitle(@RequestBody JSONObject json) {
        Long fragId = json.getLong("fragId");
        if (null == fragId) {
            return Result.error("参数错误");
        }
        CustUserEcgReportCheckOrderFrag checkOrderFrag = fragService.getById(fragId);
        if (null == checkOrderFrag) {
            return Result.error("片段不存在");
        }
        String fragTitle = json.getString("fragTitle");
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        checkOrderFrag.setLastModifyUserId(loginUserId);
        checkOrderFrag.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        checkOrderFrag.setFragTitle(fragTitle);
        fragService.updateById(checkOrderFrag);
        return Result.OK("操作成功");
    }
}
