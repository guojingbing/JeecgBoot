package org.jeecg.modules.openapi.base.controller;

import org.jeecg.modules.openapi.base.entity.SysOpenAuthUser;
import org.jeecg.modules.openapi.base.entity.SysOpenAuthUserApi;
import org.jeecg.modules.openapi.base.enums.SysOpenAuthUserTypeEnum;
import org.jeecg.modules.openapi.base.service.ISysOpenAuthUserApiService;
import org.jeecg.modules.openapi.base.service.ISysOpenAuthUserService;
import org.jeecg.modules.openapi.base.vo.SysOpenAuthUserApiVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.Md5Util;
import org.jeecg.modules.base.mapper.BaseCommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Description: 三方授权机构管理
 * @Author: jeecg-boot
 * @Date: 2022-01-05
 * @Version: V1.0
 */
@Api(tags = "三方授权机构管理")
@RestController
@RequestMapping("/comm/base/open/auth")
@Slf4j
public class SysOpenAuthUserController extends JeecgController<SysOpenAuthUser, ISysOpenAuthUserService> {
    @Autowired
    private ISysOpenAuthUserService sysOpenAuthUserService;

    @Autowired
    private ISysOpenAuthUserApiService sysOpenAuthUserApiService;
    @Resource
    private BaseCommonMapper baseCommonMapper;

    /**
     * 分页列表查询
     *
     * @param sysOpenAuthUser
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "三方授权机构管理-分页列表查询")
    @ApiOperation(value = "三方授权机构管理-分页列表查询", notes = "三方授权机构管理-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(SysOpenAuthUser sysOpenAuthUser,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<SysOpenAuthUser> queryWrapper = QueryGenerator.initQueryWrapper(sysOpenAuthUser, req.getParameterMap());
        Page<SysOpenAuthUser> page = new Page<SysOpenAuthUser>(pageNo, pageSize);
        IPage<SysOpenAuthUser> pageList = sysOpenAuthUserService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param sysOpenAuthUser
     * @return
     */
    @AutoLog(value = "三方授权机构管理-添加")
    @ApiOperation(value = "三方授权机构管理-添加", notes = "三方授权机构管理-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody SysOpenAuthUser sysOpenAuthUser) {
        if (null == sysOpenAuthUser.getIsApiUse()) {
            sysOpenAuthUser.setIsApiUse(false);
        }
        if (null == sysOpenAuthUser.getAuthStatus()) {
            sysOpenAuthUser.setAuthStatus(1);
        }
        Result result = sysOpenAuthUser.checkParam(sysOpenAuthUser);
        if (null != result) {
            return result;
        }
        String apiKey = Md5Util.md5Encode(sysOpenAuthUser.getCorpCode() + RandomUtils.nextInt(90), "utf-8");
        String apiSecret = Md5Util.md5Encode(apiKey, "utf-8");
        sysOpenAuthUser.setApiKey(apiKey);
        sysOpenAuthUser.setApiSecret(apiSecret);
        sysOpenAuthUserService.save(sysOpenAuthUser);
        sysOpenAuthUserService.redisCacheIP();
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param sysOpenAuthUser
     * @return
     */
    @AutoLog(value = "三方授权机构管理-编辑")
    @ApiOperation(value = "三方授权机构管理-编辑", notes = "三方授权机构管理-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody SysOpenAuthUser sysOpenAuthUser) {
        Result result = sysOpenAuthUser.checkParam(sysOpenAuthUser);
        if (null != result) {
            return result;
        }
        sysOpenAuthUserService.updateById(sysOpenAuthUser);
        sysOpenAuthUserService.redisCacheIP();
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "三方授权机构管理-通过id删除")
    @ApiOperation(value = "三方授权机构管理-通过id删除", notes = "三方授权机构管理-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        sysOpenAuthUserService.removeById(id);
        sysOpenAuthUserService.redisCacheIP();
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "三方授权机构管理-批量删除")
    @ApiOperation(value = "三方授权机构管理-批量删除", notes = "三方授权机构管理-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.sysOpenAuthUserService.removeByIds(Arrays.asList(ids.split(",")));
        sysOpenAuthUserService.redisCacheIP();
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "三方授权机构管理-通过id查询")
    @ApiOperation(value = "三方授权机构管理-通过id查询", notes = "三方授权机构管理-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        SysOpenAuthUser sysOpenAuthUser = sysOpenAuthUserService.getById(id);
        if (sysOpenAuthUser == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(sysOpenAuthUser);
    }

    /**
     * 接口配置查询
     *
     * @param id 授权机构id
     * @return
     */
    @AutoLog(value = "三方授权机构管理-接口配置查询")
    @ApiOperation(value = "三方授权机构管理-接口配置查询", notes = "三方授权机构管理-接口配置查询")
    @GetMapping(value = "/setting/inter")
    public Result<?> setInterList(@RequestParam(name = "id", required = true) String id) {
        SysOpenAuthUser sysOpenAuthUser = sysOpenAuthUserService.getById(id);
        if (sysOpenAuthUser == null) {
            return Result.error("未找到对应数据");
        }
        if (!sysOpenAuthUser.getIsApiUse()) {
            return Result.error("未开启接口对接");
        }
        String rootUrl = sysOpenAuthUser.getApiRootUrl();
        //查询数据库中已经设置过的信息
        List<SysOpenAuthUserApi> userApi = sysOpenAuthUserApiService.selectListByAuthId(id, sysOpenAuthUser.getType(), null);
        if (CollectionUtils.isEmpty(userApi)) {
            userApi = new ArrayList<>();
        }
        //查询字典中设置的
        List<SysOpenAuthUserApi> allApiList;
        String code = StringUtils.EMPTY;
        if (SysOpenAuthUserTypeEnum.SUPPLIER.getCode() == sysOpenAuthUser.getType()) {
            //查询sys_open_auth_user_api_ecg
            code = "sys_open_auth_user_api_ecg";
        } else if (SysOpenAuthUserTypeEnum.HOSP.getCode() == sysOpenAuthUser.getType()) {
            //查询sys_open_auth_user_api_order
            code = "sys_open_auth_user_api_order";
        }
        List<DictModel> dictModels = baseCommonMapper.queryDictItemsByCode(code);
        List<Map> list = new ArrayList<>(dictModels.size());
        for (DictModel dictModel : dictModels) {
            String apiName = dictModel.getText();
            Integer apiCode = Integer.parseInt(dictModel.getValue());
            Map<String, Object> map = new HashMap<>();
            map.put("apiCode", apiCode);
            map.put("apiName", apiName);
            map.put("id", null);
            map.put("apiUri", null);
            Optional<SysOpenAuthUserApi> authUserApi = userApi.stream().filter(u -> u.getApiCode().intValue() == apiCode.intValue()).findAny();
            if (authUserApi.isPresent()) {
                SysOpenAuthUserApi api = authUserApi.get();
                map.put("id", api.getId());
                map.put("apiUri", api.getApiUri());
            }
            list.add(map);
        }
        SysOpenAuthUserApiVo vo = new SysOpenAuthUserApiVo();
        vo.setId(id);
        vo.setApiRootUrl(rootUrl);
        vo.setApiList(list);
        return Result.OK(vo);
    }

    /**
     * 接口配置
     *
     * @param vo
     * @return
     */
    @AutoLog(value = "三方授权机构管理-接口配置")
    @ApiOperation(value = "三方授权机构管理-接口配置", notes = "三方授权机构管理-接口配置")
    @PutMapping(value = "/setting/inter")
    public Result<?> setInterList(@RequestBody SysOpenAuthUserApiVo vo) {
        String authId = vo.getId();
        SysOpenAuthUser sysOpenAuthUser = sysOpenAuthUserService.getById(authId);
        if (sysOpenAuthUser == null) {
            return Result.error("未找到对应数据");
        }
        if (!sysOpenAuthUser.getIsApiUse()) {
            return Result.error("未开启接口对接");
        }
        String apiRootUrl = StringUtils.isBlank(vo.getApiRootUrl()) ? sysOpenAuthUser.getApiRootUrl() : vo.getApiRootUrl();
        if (StringUtils.isBlank(apiRootUrl)) {
            return Result.error("请填写根路径");
        }
        List<Map> apiList = vo.getApiList();
        if (CollectionUtils.isEmpty(apiList)) {
            return Result.error("请填写接口信息");
        }
        List<SysOpenAuthUserApi> newUserApiList = new ArrayList<>(apiList.size());
        for (Map map : apiList) {
            Integer apiCode = (Integer) map.get("apiCode");
            String apiUri = (String) map.get("apiUri");
            SysOpenAuthUserApi api = new SysOpenAuthUserApi();
            api.setAuthId(authId);
            api.setApiUri(apiUri);
            api.setApiCode(apiCode);
            api.setId(null == map.get("id") ? null : (String) map.get("id"));
            newUserApiList.add(api);
        }
        sysOpenAuthUserApiService.saveOrUpdateBatch(newUserApiList);
        sysOpenAuthUser.setApiRootUrl(apiRootUrl);
        sysOpenAuthUserService.updateById(sysOpenAuthUser);
        return Result.OK("设置成功");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param sysOpenAuthUser
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SysOpenAuthUser sysOpenAuthUser) {
        return super.exportXls(request, sysOpenAuthUser, SysOpenAuthUser.class, "三方授权机构管理");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, SysOpenAuthUser.class);
    }

}
