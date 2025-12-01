package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.entity.CustUserMach;
import org.jeecg.modules.zxecg.cust.service.ICustUserMachBindingLogService;
import org.jeecg.modules.zxecg.cust.service.ICustUserMachPowerLogService;
import org.jeecg.modules.zxecg.cust.service.ICustUserMachService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 */

@Slf4j
@Api(tags = "设备绑定")
@RestController
@RequestMapping("/zxecg/mach")
public class CustUserMachController {
    @Resource
    ICustUserMachService userMachService;
    @Resource
    ICustUserMachBindingLogService bindingLogService;
    @Resource
    ICustUserMachPowerLogService powerLogService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "设备绑定-分页列表查询")
    @ApiOperation(value = "设备绑定-分页列表查询", notes = "设备绑定-分页列表查询")
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
        pageList = userMachService.loadListPaging(pageList, loginUserId, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 绑定记录列表
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "设备绑定-绑定记录列表")
    @ApiOperation(value = "设备绑定-绑定记录列表", notes = "设备绑定-绑定记录列表")
    @GetMapping(value = "/bindingList")
    public Result<?> queryPageBindingList(@RequestParam Long bindingId,
                                          @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                          HttpServletRequest req) {
        Page<Map> pageList = new Page<>(pageNo, pageSize);
        String column = req.getParameter("column");
        String order = req.getParameter("order");
        pageList = bindingLogService.loadListPaging(pageList, bindingId, column, order);
        return Result.OK(pageList);
    }

    /**
     * 电量记录
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "设备绑定-电量记录")
    @ApiOperation(value = "设备绑定-电量记录", notes = "设备绑定-电量记录")
    @GetMapping(value = "/powerList")
    public Result<?> queryPagePowerList(@RequestParam Long bindingId,
                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                        HttpServletRequest req) {
        Page<Map> pageList = new Page<>(pageNo, pageSize);
        String column = req.getParameter("column");
        String order = req.getParameter("order");
        pageList = powerLogService.loadListPaging(pageList, bindingId, column, order);
        return Result.OK(pageList);
    }

    /**
     * 解绑
     *
     * @param bindingId
     * @return
     */
    @AutoLog(value = "设备绑定-解绑")
    @ApiOperation(value = "设备绑定-重置用户密码", notes = "设备绑定-解绑")
    @PostMapping(value = "/unbind")
    public Result<?> machUnbind(@RequestParam Long bindingId) {
        CustUserMach userMach = userMachService.getById(bindingId);
        if (null == userMach) {
            return Result.error("绑定信息不存在");
        }
        userMach.setBindingStatus(2);
        userMach.setUnbindTime(new Timestamp(System.currentTimeMillis()));
        userMachService.machUnbind(userMach);
        return Result.OK("操作成功");
    }
}
