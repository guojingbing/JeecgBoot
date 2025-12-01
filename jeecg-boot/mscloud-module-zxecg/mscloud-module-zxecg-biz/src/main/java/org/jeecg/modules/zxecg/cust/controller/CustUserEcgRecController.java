package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgRec;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgRecService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/29
 */

@Slf4j
@Api(tags = "监测记录")
@RestController
@RequestMapping("/zxecg/ecg")
public class CustUserEcgRecController {
    @Resource
    ICustUserEcgRecService recService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "监测记录-分页列表查询")
    @ApiOperation(value = "监测记录-分页列表查询", notes = "监测记录-分页列表查询")
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
        pageList = recService.loadListPaging(pageList, loginUserId, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 标记入库
     *
     * @param json
     * @return
     */
    @AutoLog(value = "监测记录-标记入库")
    @ApiOperation(value = "监测记录-标记入库", notes = "监测记录-标记入库")
    @PostMapping(value = "/mark")
    public Result<?> markSample(@RequestBody JSONObject json) {
        Long ecgId = json.getLong("ecgId");
        if (null == ecgId) {
            return Result.error("请选择测量记录");
        }
        String codes = json.getString("codes");
        if (StringUtils.isBlank(codes)) {
            return Result.error("请选择事件标记");
        }
        CustUserEcgRec ecgRec = recService.getById(ecgId);
        if (null == ecgRec) {
            return Result.error("监测记录不存在");
        }
        //todo  操作
        return Result.OK("操作成功");
    }

    /**
     * 删除
     *
     * @param ecgId
     * @return
     */
    @AutoLog(value = "监测记录-删除")
    @ApiOperation(value = "监测记录-删除", notes = "监测记录-删除")
    @DeleteMapping(value = "/del")
    public Result<?> delEcg(@Param("ecgId") Long ecgId) {
        CustUserEcgRec ecgRec = recService.getById(ecgId);
        if (null == ecgRec) {
            return Result.error("监测记录不存在");
        }
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
//        recService.deleteEcg(ecgId, loginUserId);
        return Result.OK("操作成功");
    }

    /**
     * 翻转
     *
     * @param json
     * @return
     */
    @AutoLog(value = "监测记录-翻转")
    @ApiOperation(value = "监测记录-翻转", notes = "监测记录-翻转")
    @PostMapping(value = "/invert")
    public Result<?> ecgInvert(@RequestBody JSONObject json) {
        Long ecgId = json.getLong("ecgId");
        if (null == ecgId) {
            return Result.error("请选择测量记录");
        }
        CustUserEcgRec ecgRec = recService.getById(ecgId);
        if (null == ecgRec) {
            return Result.error("测量记录不存在");
        }
        Integer wearWay = ecgRec.getWearWay();
        if (null == wearWay || wearWay.intValue() == 1) {
            wearWay = 2;
        } else {
            wearWay = 1;
        }
        ecgRec.setWearWay(wearWay);
        recService.updateById(ecgRec);
        return Result.OK("操作成功");
    }


}
