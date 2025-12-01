package org.jeecg.modules.zxecg.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.system.entity.CommCompanyMach;
import org.jeecg.modules.zxecg.system.service.ICommCompanyMachService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/26
 * @description 设备管理
 */

@Slf4j
@Api(tags = "设备管理")
@RestController
@RequestMapping("/zxecg/system/mach")
public class CommCompanyMachController {
    @Resource
    ICommCompanyMachService machService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "设备管理-分页列表查询")
    @ApiOperation(value = "设备管理-分页列表查询", notes = "设备管理-分页列表查询")
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
        //获取当前登录用户
        Long loginUserId = 13L;
        pageList = machService.loadListPaging(pageList, loginUserId, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 设备状态变更
     *
     * @param machId
     * @return
     */
    @AutoLog(value = "设备管理-设备状态变更")
    @ApiOperation(value = "设备管理-设备状态变更", notes = "设备管理-设备状态变更")
    @PostMapping(value = "/status")
    public Result<?> updateStatus(@RequestParam Long machId) {
        CommCompanyMach companyMach = machService.getById(machId);
        if (null == companyMach) {
            return Result.error("设备不存在");
        }
        //禁用、启用操作，后端验证是否已过有效日期，若已过期，提示“设备已经过期，不可操作”。
        Timestamp endDate = companyMach.getEndDate();
        Date d = new Date(endDate.getTime());
        LocalDate now = LocalDate.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = now.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        if (d.compareTo(date) < 0) {
            return Result.error("设备已经过期，不可操作");
        }
        machService.updateStatus(companyMach);
        return Result.OK("操作成功");
    }


}
