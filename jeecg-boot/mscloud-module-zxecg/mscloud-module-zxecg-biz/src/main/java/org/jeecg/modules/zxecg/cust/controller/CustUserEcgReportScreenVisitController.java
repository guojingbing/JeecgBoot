package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportScreenVisit;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportScreenVisitService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/3
 */

@Slf4j
@Api(tags = "消费者筛查回访")
@RestController
@RequestMapping("/zxecg/visit")
public class CustUserEcgReportScreenVisitController {
    @Resource
    ICustUserEcgReportScreenVisitService visitService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "消费者筛查回访-分页列表查询")
    @ApiOperation(value = "消费者筛查回访-分页列表查询", notes = "消费者筛查回访-分页列表查询")
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
        pageList = visitService.loadListPaging(pageList, likeMap, column, order);
        return Result.OK(pageList);
    }

    @AutoLog(value = "消费者筛查回访-删除")
    @ApiOperation(value = "消费者筛查回访-删除", notes = "消费者筛查回访-删除")
    @DeleteMapping(value = "/del")
    public Result<?> delVisit(@RequestBody JSONObject json) {
        Long id = json.getLong("id");
        if (null == id) {
            return Result.error("请选择要删除的回访记录");
        }
        CustUserEcgReportScreenVisit visit = visitService.getById(id);
        if (null == visit) {
            return Result.error("回访记录不存在");
        }
        visitService.removeById(id);
        return Result.OK("操作成功");
    }

    /**
     * @param visit
     * @return
     */
    @AutoLog(value = "消费者筛查回访-添加")
    @ApiOperation(value = "消费者筛查回访-添加", notes = "消费者筛查回访-添加")
    @PostMapping(value = "/add")
    public Result<?> addVisit(@RequestBody CustUserEcgReportScreenVisit visit) {
        Long userId = visit.getUserId();
        if (null == userId) {
            return Result.error("缺少用户id");
        }
        String visitContent = visit.getVisitContent();
        if (StringUtils.isBlank(visitContent)) {
            return Result.error("缺少回访内容");
        }
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        if (null == visit.getVisitTime()) {
            visit.setVisitTime(new Timestamp(System.currentTimeMillis()));
        }
        visit.setCreateUserId(loginUserId);
        visit.setLastModifyUserId(loginUserId);
        visit.setCreateDate(new Timestamp(System.currentTimeMillis()));
        visit.setLastModifyDate(new Timestamp(System.currentTimeMillis()));
        visitService.save(visit);
        return Result.OK("操作成功");
    }
}
