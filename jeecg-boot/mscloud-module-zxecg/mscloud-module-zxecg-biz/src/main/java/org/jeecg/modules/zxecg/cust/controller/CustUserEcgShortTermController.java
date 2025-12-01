package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgShortTerm;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgShortTermService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/22
 */

@Slf4j
@Api(tags = "快速检测记录")
@RestController
@RequestMapping("/zxecg/shortterm")
public class CustUserEcgShortTermController {
    @Resource
    ICustUserEcgShortTermService shortTermService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "快速检测记录-分页列表查询")
    @ApiOperation(value = "快速检测记录-分页列表查询", notes = "快速检测记录-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(@RequestBody(required = false) JSONObject json,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        List<Long> deptIdList = null;
        Page<Map> pageList = new Page<>(pageNo, pageSize);
        String column = req.getParameter("column");
        String order = req.getParameter("order");
        Map<String, Object> likeMap = new HashMap<>();
        if (null != json) {
            json.keySet().forEach(key -> {
                likeMap.put(key, json.get(key));
            });
        }
//        BeanMap beanMap = new BeanMap(shortTermDTO);
//        for (Object key : beanMap.keySet()) {
//            Object o = beanMap.get(key);
//            if (null != o) {
//                likeMap.put(String.valueOf(key), beanMap.get(key));
//            }
//        }
//        // 移除默认生成的class
//        likeMap.remove("class");
        pageList = shortTermService.loadListPaging(pageList, deptIdList, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 诊断结果
     *
     * @param jsonObject
     * @return
     */
    @AutoLog(value = "快速检测记录-诊断结果")
    @ApiOperation(value = "快速检测记录-诊断结果", notes = "快速检测记录-诊断结果")
    @PostMapping(value = "/resultEdit")
    public Result<?> resultEdit(@RequestBody JSONObject jsonObject) {
        Long ecgId = jsonObject.getLong("ecgId");
        if (null == ecgId) {
            return Result.error("ecgId不能为空");
        }
        CustUserEcgShortTerm shortTerm = shortTermService.getById(ecgId);
        if (null == shortTerm) {
            return Result.error("快速筛查记录不存在");
        }
        JSONArray eventList = jsonObject.getJSONArray("eventList");
        if (null == eventList || eventList.size() == 0) {
            return Result.error("诊断结果不能为空");
        }
        shortTermService.resultEdit(shortTerm, eventList);
        return Result.OK("操作成功");
    }

}
