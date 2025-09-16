package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.dto.CustUserEcgReportScreenNoteDTO;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportScreenNote;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportScreenEventService;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportScreenNoteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/2
 */

@Slf4j
@Api(tags = "消费者筛查")
@RestController
@RequestMapping("/zxecg/screen")
public class CustUserEcgReportScreenEventController {
    @Resource
    ICustUserEcgReportScreenEventService screenEventService;
    @Resource
    ICustUserEcgReportScreenNoteService screenNoteService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "消费者筛查确认-分页列表查询")
    @ApiOperation(value = "消费者筛查确认-分页列表查询", notes = "消费者筛查确认-分页列表查询")
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
        pageList = screenEventService.loadListPaging(pageList, loginUserId, likeMap, column, order);
        return Result.OK(pageList);
    }

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
    @GetMapping(value = "/note/list")
    public Result<?> queryNotePageList(@RequestBody(required = false) JSONObject json,
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
        pageList = screenEventService.loadNoteListPaging(pageList, loginUserId, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 回访建议提交
     *
     * @param
     * @param screenNoteDTO
     * @return
     */
    @AutoLog(value = "消费者筛查确认-回访建议提交")
    @ApiOperation(value = "消费者筛查确认-回访建议提交", notes = "消费者筛查确认-回访建议提交")
    @PostMapping(value = "/note/submit")
    public Result<?> noteSubmit(@RequestBody CustUserEcgReportScreenNoteDTO screenNoteDTO) {
        Long noteId = screenNoteDTO.getNoteId();
        Long repId = screenNoteDTO.getRepId();
        if (null == noteId && null == repId) {
            return Result.error("参数错误");
        }
        if (null != noteId) {
            CustUserEcgReportScreenNote note = screenNoteService.getById(noteId);
            if (null == note) {
                return Result.error("回访记录不存在");
            }
        }
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        screenNoteService.noteSubmit( screenNoteDTO, loginUserId);
        return Result.OK("操作成功");
    }
}
