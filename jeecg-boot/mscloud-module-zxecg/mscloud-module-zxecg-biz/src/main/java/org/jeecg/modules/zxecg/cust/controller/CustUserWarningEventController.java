package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.service.ICustUserWarningEventService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/4
 */

@Slf4j
@Api(tags = "危急预警")
@RestController
@RequestMapping("/zxecg/warning")
public class CustUserWarningEventController {
    @Resource
    ICustUserWarningEventService warningEventService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "危急预警-分页列表查询")
    @ApiOperation(value = "危急预警-分页列表查询", notes = "危急预警-分页列表查询")
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
        if (likeMap.containsKey("read")) {
            likeMap.put("read", 0);
        }
        likeMap.put("warnLevel", 1);
        pageList = warningEventService.loadListPaging(pageList, loginUserId, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 标记已读
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "危急预警-标记已读")
    @ApiOperation(value = "危急预警-标记已读", notes = "危急预警-标记已读")
    @PostMapping(value = "/read")
    public Result<?> markRead(@Param("ids") String ids) {
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        warningEventService.markRead(ids, loginUserId);
        return Result.OK("操作成功");
    }
}
