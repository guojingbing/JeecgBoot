package org.jeecg.modules.zxecg.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.system.entity.CommSystemBussiEventWarningNotify;
import org.jeecg.modules.zxecg.system.service.ICommSystemBussiEventWarningNotifyService;
import org.jeecg.modules.zxecg.system.service.ICommSystemBussiEventWarningService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/25
 */

@Slf4j
@Api(tags = "运维预警")
@RestController
@RequestMapping("/zxecg/system/warn")
public class CommSystemBussiEventWarningController {
    @Resource
    ICommSystemBussiEventWarningService warningService;
    @Resource
    ICommSystemBussiEventWarningNotifyService notifyService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "运维预警-分页列表查询")
    @ApiOperation(value = "运维预警-分页列表查询", notes = "运维预警-分页列表查询")
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
        pageList = warningService.loadListPaging(pageList, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 批量处理
     *
     * @param
     * @param json
     * @return
     */
    @AutoLog(value = "运维预警-批量处理")
    @ApiOperation(value = "运维预警-批量处理", notes = "运维预警-批量处理")
    @PostMapping(value = "/deal")
    public Result<?> dealWarning(@RequestBody JSONObject json) {
        //todo 获取登录用户id
        Long loginUserId = null;
        String ids = json.getString("ids");
        if (StringUtils.isBlank(ids)) {
            return Result.error("请选择需要处理的记录");
        }
        Integer dealResult = json.getInteger("dealResult");
        if (null == dealResult) {
            return Result.error("处理结果不能为空");
        }
        warningService.dealWarning(ids, dealResult, loginUserId);
        return Result.OK("操作成功");
    }

    @AutoLog(value = "运维预警-预警设置列表")
    @ApiOperation(value = "运维预警-预警设置列表", notes = "运维预警-预警设置列表")
    @GetMapping(value = "/notify")
    public Result<?> notifyList() {
        List<CommSystemBussiEventWarningNotify> notifyList = notifyService.getNotifyList();
        return Result.OK(notifyList);
    }

    /**
     * 预警设置
     *
     * @param
     * @param json
     * @return
     */
    @AutoLog(value = "运维预警-预警设置")
    @ApiOperation(value = "运维预警-预警设置", notes = "运维预警-预警设置")
    @PostMapping(value = "/set")
    public Result<?> setWarning(@RequestBody JSONObject json) {
        Long loginUserId = null;
        Integer notifyLevel = json.getInteger("notifyLevel");
        if (null == notifyLevel) {
            return Result.error("请选择预警级别");
        }
        String notifyPhones = json.getString("notifyPhones");
        if (StringUtils.isBlank(notifyPhones)) {
            return Result.error("请填写手机号");
        }
        notifyService.setWarning(notifyLevel, notifyPhones);
        return Result.OK("操作成功");
    }
}
