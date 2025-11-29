package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReport;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportFavoriteService;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportResultChangeService;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/29
 */

@Slf4j
@Api(tags = "心电报告")
@RestController
@RequestMapping("/zxecg/rep")
public class CustUserEcgReportController {
    @Resource
    ICustUserEcgReportService reportService;
    @Resource
    ICustUserEcgReportFavoriteService favoriteService;
    @Resource
    ICustUserEcgReportResultChangeService reportResultChangeService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "心电报告-分页列表查询")
    @ApiOperation(value = "心电报告-分页列表查询", notes = "心电报告-分页列表查询")
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
        if (!likeMap.containsKey("hideDaily")) {
            likeMap.put("hideDaily", 0);
        }
        pageList = reportService.loadListPaging(pageList, loginUserId, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 添加/取消收藏
     *
     * @param json
     * @return
     */
    @AutoLog(value = "心电报告-添加/取消收藏")
    @ApiOperation(value = "心电报告-添加/取消收藏", notes = "心电报告-添加/取消收藏")
    @PostMapping(value = "/fav")
    public Result<?> addFav(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (null == repId) {
            return Result.error("缺少报告Id");
        }
        CustUserEcgReport report = reportService.getById(repId);
        if (null == report) {
            return Result.error("报告不存在");
        }
        Long loginUserId = 13L;
        favoriteService.addFav(repId, loginUserId);
        return Result.OK("操作成功");
    }

    /**
     * 机构迁移
     *
     * @param json
     * @return
     */
    @AutoLog(value = "心电报告-机构迁移")
    @ApiOperation(value = "心电报告-机构迁移", notes = "心电报告-机构迁移")
    @PostMapping(value = "/changeDept")
    public Result<?> changeDept(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (null == repId) {
            return Result.error("请选择要迁移的报告");
        }
        CustUserEcgReport report = reportService.getById(repId);
        if (null == report) {
            return Result.error("报告不存在");
        }
        Long deptId = json.getLong("deptId");
        if (null == deptId) {
            return Result.error("请选择迁移的机构");
        }
        reportService.changeDept(repId, deptId);
        return Result.OK("操作成功");
    }

    /**
     * 查询报告状态
     *
     * @param repId
     * @return
     */
    @AutoLog(value = "心电报告-查询报告状态")
    @ApiOperation(value = "心电报告-查询报告状态", notes = "心电报告-查询报告状态")
    @GetMapping(value = "/state")
    public Result<?> repState(@RequestParam Long repId) {
        CustUserEcgReport report = reportService.getById(repId);
        if (null == report) {
            return Result.error("报告不存在");
        }
        Map stateMap = reportService.repState(repId);
        return Result.OK(stateMap);
    }

    /**
     * AI解读信息查询
     *
     * @param repId
     * @return
     */
    @AutoLog(value = "心电报告-AI解读信息查询")
    @ApiOperation(value = "心电报告-AI解读信息查询", notes = "心电报告-AI解读信息查询")
    @GetMapping(value = "/diag/info")
    public Result<?> diagInfo(@RequestParam Long repId) {
        CustUserEcgReport report = reportService.getById(repId);
        if (null == report) {
            return Result.error("报告不存在");
        }
        Map diagInfoMap = reportService.getRepDiagInfo(repId);
        return Result.OK(diagInfoMap);
    }

    /**
     * 解读结论修改
     *
     * @param json
     * @return
     */
    @AutoLog(value = "心电报告-解读结论修改")
    @ApiOperation(value = "心电报告-解读结论修改", notes = "心电报告-解读结论修改")
    @PostMapping(value = "/diag/up")
    public Result<?> diagUpdate(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (null == repId) {
            return Result.error("缺少报告Id");
        }
        CustUserEcgReport report = reportService.getById(repId);
        if (null == report) {
            return Result.error("错误的报告Id");
        }
        String content = json.getString("content");
        if (StringUtils.isBlank(content)) {
            return Result.error("解读结论为空");
        }
        String inDesc = json.getString("inDesc");
        reportService.diagUpdate(repId, content, inDesc);
        return Result.OK("修改成功");
    }

    /**
     * 查询报告有数据的项目
     *
     * @param repId
     * @return
     */
    @AutoLog(value = "心电报告-查询报告有数据的项目")
    @ApiOperation(value = "心电报告-查询报告有数据的项目", notes = "心电报告-查询报告有数据的项目")
    @GetMapping(value = "/items")
    public Result<?> getRepItems(@RequestParam Long repId) {
        CustUserEcgReport report = reportService.getById(repId);
        if (null == report) {
            return Result.error("错误的报告Id");
        }
        Map<String, Object> map = reportService.getRepItems(repId);
        return Result.OK(map);
    }


    /**
     * 修改报告用户信息
     *
     * @param json
     * @return
     */
    @AutoLog(value = "心电报告-修改报告用户信息")
    @ApiOperation(value = "心电报告-修改报告用户信息", notes = "心电报告-修改报告用户信息")
    @PostMapping(value = "/editReportUserInfo")
    public Result<?> editReportUserInfo(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (null == repId) {
            return Result.error("缺少报告Id");
        }
        CustUserEcgReport report = reportService.getById(repId);
        if (null == report) {
            return Result.error("错误的报告Id");
        }
        String userName = json.getString("userName");
        if (StringUtils.isBlank(userName)) {
            return Result.error("请填写用户姓名");
        }
        Date birthDate = json.getDate("birthDate");
        if (null == birthDate) {
            return Result.error("请填写用户出生日期");
        }
        Integer userGender = json.getInteger("userGender");
        if (null == userGender) {
            return Result.error("请填写用户性别");
        }
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        reportService.editReportUserInfo(repId, userName, birthDate, userGender, loginUserId);
        return Result.OK("操作成功");
    }

    /**
     * 合并报告用户列表分页查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "心电报告-合并报告用户列表分页查询")
    @ApiOperation(value = "心电报告-合并报告用户列表分页查询", notes = "心电报告-合并报告用户列表分页查询")
    @GetMapping(value = "/userList")
    public Result<?> queryUserPageList(@RequestBody(required = false) JSONObject json,
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
        pageList = reportService.loadMergeUserListPaging(pageList, loginUserId, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 查询用户按指定月份统计有报告的日期
     *
     * @param
     * @param json
     * @return
     */
    @AutoLog(value = "心电报告-查询用户按指定月份统计有报告的日期")
    @ApiOperation(value = "心电报告-查询用户按指定月份统计有报告的日期", notes = "心电报告-查询用户按指定月份统计有报告的日期")
    @GetMapping(value = "/repDate")
    public Result<?> repDate(@RequestBody JSONObject json) {
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        Long userId = json.getLong("userId");
        String userName = json.getString("userName");
        String startMonth = json.getString("startMonth");
        String endMonth = json.getString("endMonth");
        if (null == userId || StringUtils.isAnyBlank(userName, startMonth, endMonth)) {
            return Result.error("参数错误");
        }
        List<Map> list = reportService.repDate(userId, userName, startMonth, endMonth, loginUserId);
        return Result.OK(list);
    }

    /**
     * 确认/删除事件
     *
     * @param json
     * @return
     */
    @AutoLog(value = "智能筛查-确认/删除事件")
    @ApiOperation(value = "智能筛查-确认/删除事件", notes = "智能筛查-确认/删除事件")
    @PostMapping(value = "/result/change")
    public Result<?> repResultChange(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (null == repId) {
            return Result.error("缺少报告Id");
        }
        Integer typeId = json.getInteger("typeId");
        if (null == typeId) {
            return Result.error("缺少事件类型");
        }
        Integer operId = json.getInteger("operId");
        if (null == operId) {
            return Result.error("缺少操作类型");
        }
        CustUserEcgReport report = reportService.getById(repId);
        if (null == report) {
            return Result.error("错误的报告Id");
        }
        String ecgIds = json.getString("ecgIds");
        if (StringUtils.isBlank(ecgIds)) {
            return Result.error("缺少测量记录id");
        }
        String abnTimes = json.getString("abnTimes");
        if (StringUtils.isBlank(abnTimes)) {
            return Result.error("缺少事件发生时间");
        }
        List<String> ecgList = Arrays.asList(ecgIds.split(","));
        List<Long> ecgIdList = ecgList.stream().map(Long::new).collect(Collectors.toList());
        List<String> abnTimesList = Arrays.asList(abnTimes.split(","));
        List<Long> timeList = abnTimesList.stream().map(Long::new).collect(Collectors.toList());
        if (ecgIdList.size() != timeList.size()) {
            return Result.error("参数不一致");
        }

        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        reportResultChangeService.repResultChange(repId, typeId, operId, ecgIdList, timeList, loginUserId);
        return Result.OK("操作成功");
    }


}
