package org.jeecg.modules.zxecg.cust.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.zxecg.cust.dto.CustUserInfoDTO;
import org.jeecg.modules.zxecg.cust.entity.CustUserInfo;
import org.jeecg.modules.zxecg.cust.service.ICustUserInfoService;
import org.jeecg.modules.zxecg.cust.vo.CardInfoVo;
import org.jeecg.modules.zxecg.cust.vo.CustUserInfoVo;
import org.jeecg.modules.zxecg.system.service.ICommBaseCodeService;
import org.jeecg.modules.zxecg.system.vo.CommBaseCodeDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/8/21
 */

@Slf4j
@Api(tags = "我的病人")
@RestController
@RequestMapping("/zxecg/user")
public class CustUserInfoController {
    @Resource
    private ICustUserInfoService userInfoService;
    @Autowired
    private ICommBaseCodeService commBaseCodeService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "我的病人-分页列表查询")
    @ApiOperation(value = "我的病人-分页列表查询", notes = "我的病人-分页列表查询")
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
        pageList = userInfoService.loadListPaging(pageList, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 通过id删除
     *
     * @param userId
     * @return
     */
    @AutoLog(value = "我的病人-通过id删除")
    @ApiOperation(value = "我的病人-通过id删除", notes = "我的病人-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> deleteUserById(@RequestParam(name = "userId") Long userId) {
        CustUserInfo userInfo = userInfoService.getById(userId);
        if (null == userInfo) {
            return Result.error("用户信息不存在，删除失败");
        }
        userInfo.setStatus(0);
        userInfo.setLastModifyDate(new Timestamp(System.currentTimeMillis()));
        userInfoService.updateById(userInfo);
        return Result.OK("操作成功");
    }


    /**
     * 重置用户密码
     *
     * @param json
     * @return
     */
    @AutoLog(value = "我的病人-重置用户密码")
    @ApiOperation(value = "我的病人-重置用户密码", notes = "我的病人-重置用户密码")
    @PostMapping(value = "/resetPwd")
    public Result<?> resetPwd(@RequestBody JSONObject json) {
        Long userId = json.getLong("userId");
        if (null == userId) {
            return Result.error("缺少用户id");
        }
        CustUserInfo userInfo = userInfoService.getById(userId);
        if (null == userInfo) {
            return Result.error("用户信息不存在，密码重置失败");
        }
        //查询通用代码配置
        Map<String, CommBaseCodeDetailVO> codeMap = commBaseCodeService.getCodeDetailsByCodeStrings("SYS000", Arrays.asList("SYS_DEFAULT_PWD_CUST"));
        if (codeMap != null && !codeMap.isEmpty()) {
            CommBaseCodeDetailVO code = codeMap.get("SYS_DEFAULT_PWD_CUST");
            String extraValue = code.getExtraValue();
            String encryptPwd = DigestUtils.md5Hex(extraValue);
            userInfo.setPassword(encryptPwd);
            userInfo.setLastModifyDate(new Timestamp(System.currentTimeMillis()));
            userInfoService.updateById(userInfo);
        }
        return Result.OK("操作成功");
    }

    /**
     * 用户状态变更
     *
     * @param userInfo
     * @return
     */
    @AutoLog(value = "我的病人-用户状态变更")
    @ApiOperation(value = "我的病人-用户状态变更", notes = "我的病人-用户状态变更")
    @PostMapping(value = "/userStatus")
    public Result<?> updateUserStatus(@RequestBody CustUserInfoDTO userInfo) {
        if (null == userInfo) {
            return Result.error("用户信息不存在");
        }
        Long userId = userInfo.getUserId();
        if (null == userId) {
            return Result.error("用户Id为空");
        }
        CustUserInfo user = userInfoService.getById(userId);
        if (null == user) {
            return Result.error("用户信息不存在");
        }
        user.setUserStatus(userInfo.getUserStatus());
        user.setLastModifyDate(new Timestamp(System.currentTimeMillis()));
        userInfoService.updateById(user);
        return Result.OK("操作成功");
    }

    /**
     * 解绑手机
     *
     * @param userId
     * @return
     */
    @AutoLog(value = "我的病人-解绑手机")
    @ApiOperation(value = "我的病人-解绑手机", notes = "我的病人-解绑手机")
    @PostMapping(value = "/unbindPhone")
    public Result<?> unBindMobilePhone(@RequestParam(name = "userId") Long userId) {
        CustUserInfo user = userInfoService.getById(userId);
        if (null == user) {
            return Result.error("用户信息不存在");
        }
        user.setDeviceFlag(StringUtils.EMPTY);
        user.setLastModifyDate(new Timestamp(System.currentTimeMillis()));
        userInfoService.updateById(user);
        return Result.OK("操作成功");
    }

    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    @AutoLog(value = "我的病人-用户详情")
    @ApiOperation(value = "我的病人-用户详情", notes = "我的病人-用户详情")
    @PostMapping(value = "/info")
    public Result<?> userInfo(@RequestParam(name = "userId") Long userId) {
        CustUserInfo user = userInfoService.getById(userId);
        if (null == user) {
            return Result.error("用户信息不存在");
        }
        CustUserInfoVo info = userInfoService.userInfo(userId);
        return Result.OK(info);
    }

    /**
     * 报告券列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "我的病人详情-报告券")
    @ApiOperation(value = "我的病人-报告券", notes = "我的病人-报告券")
    @GetMapping(value = "/voucherList")
    public Result<?> queryVoucherPageList(@RequestBody(required = false) JSONObject json,
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
        pageList = userInfoService.loadVoucherListPaging(pageList, likeMap, column, order);
        return Result.OK(pageList);
    }

    /**
     * 查询卡券详情
     *
     * @param
     * @param cardNo
     * @return
     */
    @AutoLog(value = "我的病人详情-查询卡券详情")
    @ApiOperation(value = "我的病人-查询卡券详情", notes = "我的病人-查询卡券详情")
    @GetMapping(value = "/voucherInfo")
    public Result<?> getVoucherInfo(@RequestParam String cardNo) {
        CardInfoVo cardInfoVo = userInfoService.getVoucherInfo(cardNo);
        if (null == cardInfoVo) {
            return Result.error("无效的卡券号");
        }
        return Result.OK(cardInfoVo);
    }


    /**
     * 紧急联系人列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "我的病人详情-紧急联系人")
    @ApiOperation(value = "我的病人-紧急联系人", notes = "我的病人-紧急联系人")
    @GetMapping(value = "/relatedList")
    public Result<?> queryRelatedPageList(@RequestBody(required = false) JSONObject json,
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
        pageList = userInfoService.loadRelatedListPaging(pageList, likeMap, column, order);
        return Result.OK(pageList);
    }
}
