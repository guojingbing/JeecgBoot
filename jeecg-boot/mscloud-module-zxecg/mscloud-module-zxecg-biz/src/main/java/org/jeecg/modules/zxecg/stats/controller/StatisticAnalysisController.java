package org.jeecg.modules.zxecg.stats.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.stats.service.IStatisticAnalysisService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 */

@Slf4j
@Api(tags = "统计分析")
@RestController
@RequestMapping("/zxecg/stats")
public class StatisticAnalysisController {
    @Resource
    IStatisticAnalysisService analysisService;

    /**
     * 机构设备统计
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "统计分析-机构设备统计")
    @ApiOperation(value = "统计分析-机构设备统计", notes = "统计分析-机构设备统计")
    @GetMapping(value = "/mach")
    public Result<?> queryMachDataPageList(@RequestBody(required = false) JSONObject json,
                                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                           HttpServletRequest req) {
        Page<Map> pageList = new Page<>(pageNo, pageSize);
        String column = req.getParameter("column");
        String order = req.getParameter("order");
        String startDate = json.getString("startDate");
        if (StringUtils.isBlank(startDate)) {
            startDate = "2010-01-01";
        }
        String endDate = json.getString("endDate");
        if (StringUtils.isBlank(endDate)) {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            endDate = currentDate.format(formatter);
        }
        Map<String, Object> likeMap = new HashMap<>();
        if (null != json) {
            json.keySet().forEach(key -> {
                likeMap.put(key, json.get(key));
            });
        }
        pageList = analysisService.loadMachDataListPaging(pageList, startDate, endDate, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 机构数据统计
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "统计分析-机构数据统计")
    @ApiOperation(value = "统计分析-机构数据统计", notes = "统计分析-机构数据统计")
    @GetMapping(value = "/company")
    public Result<?> queryCompanyDataPageList(@RequestBody(required = false) JSONObject json,
                                              @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                              HttpServletRequest req) {
        Page<Map> pageList = new Page<>(pageNo, pageSize);
        String column = req.getParameter("column");
        String order = req.getParameter("order");
        String startDate = json.getString("startDate");
        if (StringUtils.isBlank(startDate)) {
            return Result.error("请选择开始时间");
        }
        String endDate = json.getString("endDate");
        if (StringUtils.isBlank(endDate)) {
            return Result.error("请选择结束时间");
        }
        Map<String, Object> likeMap = new HashMap<>();
        if (null != json) {
            json.keySet().forEach(key -> {
                likeMap.put(key, json.get(key));
            });
        }
        likeMap.remove("startDate");
        likeMap.remove("endDate");
        pageList = analysisService.loadCompanyDataListPaging(pageList, startDate, endDate, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 用户分布统计
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "统计分析-用户分布统计")
    @ApiOperation(value = "统计分析-用户分布统计", notes = "统计分析-用户分布统计")
    @GetMapping(value = "/prov")
    public Result<?> queryUserProvDataPageList(@RequestBody(required = false) JSONObject json,
                                               @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                               HttpServletRequest req) {
        List<Map> list = analysisService.loadUserProvDataListPaging();
        return Result.OK(list);
    }

    /**
     * 医生报告统计
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "统计分析-医生报告统计")
    @ApiOperation(value = "统计分析-医生报告统计", notes = "统计分析-医生报告统计")
    @GetMapping(value = "/pdf")
    public Result<?> queryPdfPageList(@RequestBody(required = false) JSONObject json,
                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                      HttpServletRequest req) {
        Page<Map> pageList = new Page<>(pageNo, pageSize);
        String column = req.getParameter("column");
        String order = req.getParameter("order");
        String startDate = json.getString("startDate");
        String endDate = json.getString("endDate");
        Integer repType = json.getInteger("repType");
        Long companyId = json.getLong("companyId");
        if (null == companyId) {
            return Result.error("机构不能为空");
        }
        if (StringUtils.isAnyBlank(startDate, endDate)) {
            return Result.error("日期范围不能为空");
        }
        startDate += " 00:00:00";
        endDate += " 23:59:59";
        pageList = analysisService.loadPdfListPaging(pageList, startDate, endDate, repType, companyId, column, order);
        return Result.OK(pageList);
    }

    /**
     * 医生报告统计详情
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "统计分析-医生报告统计详情")
    @ApiOperation(value = "统计分析-医生报告统计详情", notes = "统计分析-医生报告统计详情")
    @GetMapping(value = "/rep")
    public Result<?> queryRepPageList(@RequestBody(required = false) JSONObject json,
                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                      HttpServletRequest req) {
        Page<Map> pageList = new Page<>(pageNo, pageSize);
        String column = req.getParameter("column");
        String order = req.getParameter("order");
        String startDate = json.getString("startDate");
        String endDate = json.getString("endDate");
        startDate += " 00:00:00";
        endDate += " 23:59:59";
        Map<String, Object> likeMap = new HashMap<>();
        if (null != json) {
            json.keySet().forEach(key -> {
                likeMap.put(key, json.get(key));
            });
        }
        likeMap.remove("startDate");
        likeMap.remove("endDate");
        pageList = analysisService.loadRepListPaging(pageList, startDate, endDate, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 起搏器指征查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "统计分析-起搏器指征查询")
    @ApiOperation(value = "统计分析-起搏器指征查询", notes = "统计分析-起搏器指征查询")
    @GetMapping(value = "/pacemaker")
    public Result<?> queryPacemakerPageList(@RequestBody(required = false) JSONObject json,
                                            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                            HttpServletRequest req) {
        Long loginUserId = 13L;
        Page<Map> pageList = new Page<>(pageNo, pageSize);
        String column = req.getParameter("column");
        String order = req.getParameter("order");
        String dateRange = json.getString("dateRange");
        String startDate = null;
        String endDate = null;
        if (StringUtils.isNotBlank(dateRange)) {
            String[] dateArr = dateRange.split(",");
            startDate = dateArr[0];
            endDate = dateArr[1];
        }
        String paramName = json.getString("paramName");
        Integer rrNums = json.getInteger("rrNums");
        Map<String, Object> likeMap = new HashMap<>();
        if (null != json) {
            json.keySet().forEach(key -> {
                likeMap.put(key, json.get(key));
            });
        }
        likeMap.put("startDate", startDate);
        likeMap.put("endDate", endDate);
        likeMap.remove("dateRange");
        likeMap.remove("paramName");
        likeMap.remove("rrNums");
        pageList = analysisService.loadPacemakerListPaging(pageList, loginUserId, paramName, rrNums, likeMap, column, order);
        return Result.OK(pageList);
    }
}
