package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportFrag;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportFragService;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportFragStyleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/10
 */

@Slf4j
@Api(tags = "报告片段")
@RestController
@RequestMapping("/zxecg/frag")
public class CustUserEcgReportFragController {
    @Resource
    ICustUserEcgReportFragService reportFragService;
    @Resource
    ICustUserEcgReportFragStyleService fragStyleService;

    /**
     * 报告留图片段导联数据修改
     *
     * @param json
     * @return
     */
    @AutoLog(value = "报告留图片段导联数据修改")
    @ApiOperation(value = "报告留图片段导联数据修改", notes = "报告留图片段导联数据修改")
    @PostMapping(value = "/changeLead")
    public Result<?> fragChangeLead(@RequestBody JSONObject json) {
        Long fragId = json.getLong("fragId");
        if (null == fragId) {
            return Result.error("缺少片段Id");
        }
        CustUserEcgReportFrag reportFrag = reportFragService.getById(fragId);
        if (null == reportFrag) {
            return Result.error("片段不存在");
        }
        if (null == reportFrag.getIsMainLead() || reportFrag.getIsMainLead() == 0) {
            reportFrag.setIsMainLead(1);
        } else {
            reportFrag.setIsMainLead(0);
        }
        reportFragService.updateById(reportFrag);
        return Result.OK("操作成功");
    }

    /**
     * 报告留图片段翻转
     *
     * @param json
     * @return
     */
    @AutoLog(value = "报告留图片段翻转")
    @ApiOperation(value = "报告留图片段翻转", notes = "报告留图片段翻转")
    @PostMapping(value = "/reverse")
    public Result<?> fragReverse(@RequestBody JSONObject json) {
        Long fragId = json.getLong("fragId");
        if (null == fragId) {
            return Result.error("缺少片段Id");
        }
        CustUserEcgReportFrag reportFrag = reportFragService.getById(fragId);
        if (null == reportFrag) {
            return Result.error("片段不存在");
        }
        reportFragService.fragReverse(reportFrag);
        return Result.OK("操作成功");
    }

    /**
     * 留图样式
     *
     * @param json
     * @return
     */
    @AutoLog(value = "留图样式")
    @ApiOperation(value = "留图样式", notes = "留图样式")
    @PostMapping(value = "/style")
    public Result<?> fragStyle(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (null == repId) {
            return Result.error("缺少报告Id");
        }
        Integer categoryId = json.getInteger("categoryId");
        if (null == categoryId) {
            return Result.error("缺少片段项目");
        }
        Integer style = null == json.getInteger("style") ? 1 : json.getInteger("style");
        fragStyleService.fragStyle(repId, categoryId, style);
        Map<String, Object> map = new HashMap<>();
        map.put("style", style);
        return Result.OK(map);
    }

    /**
     * 留图样式
     *
     * @param json
     * @return
     */
    @AutoLog(value = "片段排序")
    @ApiOperation(value = "片段排序", notes = "片段排序")
    @PostMapping(value = "/order")
    public Result<?> fragOrder(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (null == repId) {
            return Result.error("缺少报告Id");
        }
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        String fragIds = json.getString("fragIds");
        reportFragService.fragOrder(repId, fragIds, loginUserId);
        return Result.OK("操作成功");
    }

    /**
     * 按分钟自主留图
     *
     * @param json
     * @return
     */
    @AutoLog(value = "按分钟自主留图")
    @ApiOperation(value = "按分钟自主留图", notes = "按分钟自主留图")
    @PostMapping(value = "/min")
    public Result<?> minToFrag(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (null == repId) {
            return Result.error("缺少报告Id");
        }
        Long ecgId = json.getLong("ecgId");
        Long fragStartTime = json.getLong("stime");
        String title = json.getString("title");
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        Result<?> result = reportFragService.minToFrag(repId, ecgId, fragStartTime, title, loginUserId);
        if (null != result) {
            return result;
        }
        return Result.OK("操作成功");
    }

    /**
     * 用户事件填写结论
     *
     * @param json
     * @return
     */
    @AutoLog(value = "用户事件填写结论")
    @ApiOperation(value = "用户事件填写结论", notes = "用户事件填写结论")
    @PostMapping(value = "/summary")
    public Result<?> userEventSummary(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (null == repId) {
            return Result.error("缺少报告Id");
        }
        Long fragId = json.getLong("fragId");
        if (null == fragId) {
            return Result.error("缺少片段Id");
        }
        String fragDesc = json.getString("fragDesc");
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        Result<?> result = reportFragService.userEventSummary(repId, fragId, fragDesc, loginUserId);
        if (null != result) {
            return result;
        }
        return Result.OK("操作成功");
    }
}
