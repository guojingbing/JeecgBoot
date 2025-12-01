package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.entity.CustUserEcgReportStatistics;
import org.jeecg.modules.zxecg.cust.service.ICustUserEcgReportStatisticsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/11
 */

@Slf4j
@Api(tags = "心电报告结论")
@RestController
@RequestMapping("/zxecg/conclusion")
public class CustUserEcgReportStatisticsController {
    @Resource
    ICustUserEcgReportStatisticsService statisticsService;

    /**
     * 根据报告id获取报告结论
     *
     * @param repId
     * @return
     */
    @AutoLog(value = "根据报告id获取报告结论")
    @ApiOperation(value = "根据报告id获取报告结论", notes = "根据报告id获取报告结论")
    @GetMapping(value = "/rep")
    public Result<?> repStatistics(@RequestParam("repId") Long repId) {
        CustUserEcgReportStatistics statistics = statisticsService.getByRepId(repId);
        return Result.OK(statistics);
    }

    /**
     * 医师结论
     *
     * @param json
     * @return
     */
    @AutoLog(value = "医师结论")
    @ApiOperation(value = "医师结论", notes = "医师结论")
    @PostMapping(value = "/doctor")
    public Result<?> addDoctorAdvice(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (null == repId) {
            return Result.error("缺少报告Id");
        }
        String conclusion = json.getString("conclusion");
        if (StringUtils.isBlank(conclusion)) {
            return Result.error("请填写医嘱内容");
        }
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        statisticsService.saveAdvice(repId, conclusion, loginUserId);
        return Result.OK("操作成功");
    }


    /**
     * 报告统计信息编辑
     *
     * @param json
     * @return
     */
    @AutoLog(value = "报告统计信息编辑")
    @ApiOperation(value = "报告统计信息编辑", notes = "报告统计信息编辑")
    @PostMapping(value = "/update")
    public Result<?> statisticsUpdate(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (null == repId) {
            return Result.error("缺少报告Id");
        }
        CustUserEcgReportStatistics statistics = statisticsService.getByRepId(repId);
        if (null == statistics) {
            return Result.error("统计数据不存在");
        }
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        try {
            for (String key : json.keySet()) {
                //解决时间格式属性为空时复制报错问题
                ConvertUtils.register(new SqlDateConverter(null), Date.class);
                ConvertUtils.register(new SqlTimestampConverter(null), Timestamp.class);
                BeanUtils.setProperty(statistics, key, json.get(key));
            }
            //人工修改
            statistics.setModifyId(1);
            statistics.setLastModifyUserId(loginUserId);
            statistics.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
            statisticsService.updateById(statistics);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("操作失败");
        }
        return Result.OK("操作成功");
    }


    /**
     * 统计结论
     *
     * @param json
     * @return
     */
    @AutoLog(value = "统计结论")
    @ApiOperation(value = "统计结论", notes = "统计结论")
    @PostMapping(value = "/ana")
    public Result<?> anaConclusion(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (null == repId) {
            return Result.error("缺少报告Id");
        }
        CustUserEcgReportStatistics statistics = statisticsService.getByRepId(repId);
        if (null == statistics) {
            return Result.error("统计数据不存在");
        }
        //todo 获取当前登录用户id
        Long loginUserId = 13L;
        String templ = json.getString("templ");
        String content = json.getString("content");
        Integer wrap = json.getInteger("conclusionWrap") == null ? 0 : json.getInteger("conclusionWrap");//是否换行
        CustUserEcgReportStatistics st = statisticsService.saveAnaConclusion(repId, templ, content, wrap, loginUserId);
        Map<String, Object> map = new HashMap<>();
        map.put("data", st.getAnaConclusion());
        map.put("lastAnaConclusionTime", st.getLastAnaConclusionTime());
        return Result.OK(map);
    }

    /**
     * 是否在报告中打印
     *
     * @param json
     * @return
     */
    @AutoLog(value = "是否在报告中打印")
    @ApiOperation(value = "是否在报告中打印", notes = "是否在报告中打印")
    @PostMapping(value = "/print")
    public Result<?> itemPrint(@RequestBody JSONObject json) {
        Long repId = json.getLong("repId");
        if (null == repId) {
            return Result.error("缺少报告Id");
        }
        String item = json.getString("item");
        int printId = json.getInteger("printId");
        statisticsService.itemPrint(repId, item, printId == 1 ? true : false);
        return Result.OK("操作成功");
    }
}
