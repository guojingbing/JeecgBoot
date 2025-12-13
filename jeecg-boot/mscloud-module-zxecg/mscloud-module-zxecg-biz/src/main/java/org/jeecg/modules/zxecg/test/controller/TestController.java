package org.jeecg.modules.zxecg.test.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.zxecg.system.service.ICommCompanyService;
import org.jeecg.modules.zxecg.sysuser.vo.SysUserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tanyn
 * @version JDK 8
 * @date 2025/9/15
 * @description TODO
 */

@Slf4j
@Api(tags = "机构管理")
@RestController
@RequestMapping("/zxecg/test")
public class TestController {
    @Resource
    ICommCompanyService commCompanyService;

    /**
     * 获取机构树状结构
     *
     * @param
     * @return
     */
    @ApiOperation(value = "机构管理-获取机构树状结构", notes = "机构管理-获取机构树状结构")
    @GetMapping(value = "/nothing")
    public Result<?> nothing() {
        //todo  获取登录用户id
        System.out.println("test nothing");
        Long loginUserId = 13L;

        SysUserVO sysUser = SecurityUtils.getSubject().getPrincipal() != null ? (SysUserVO) SecurityUtils.getSubject().getPrincipal() : null;
//        System.out.println(sysUser.getUserId());
//        List list = commCompanyService.getTreeList(13L);
        return Result.OK(sysUser);
    }

}
