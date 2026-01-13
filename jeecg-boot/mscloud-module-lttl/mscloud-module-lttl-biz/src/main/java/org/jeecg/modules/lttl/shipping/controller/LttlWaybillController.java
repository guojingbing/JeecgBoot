package org.jeecg.modules.lttl.shipping.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybill;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybillFee;
import org.jeecg.modules.lttl.shipping.entity.LttlWaybillGoods;
import org.jeecg.modules.lttl.shipping.service.ILttlWaybillFeeService;
import org.jeecg.modules.lttl.shipping.service.ILttlWaybillGoodsService;
import org.jeecg.modules.lttl.shipping.service.ILttlWaybillService;
import org.jeecg.modules.lttl.shipping.vo.LttlWaybillPage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
 /**
 * @Description: 运单主表
 * @Author: jeecg-boot
 * @Date:   2025-12-29
 * @Version: V1.0
 */
@Tag(name="运单主表")
@RestController
@RequestMapping("/lttl/shipping/po")
@Slf4j
public class LttlWaybillController {
	@Autowired
	private ILttlWaybillService lttlWaybillService;
	@Autowired
	private ILttlWaybillGoodsService lttlWaybillGoodsService;
	@Autowired
	private ILttlWaybillFeeService lttlWaybillFeeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param lttlWaybill
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "运单主表-分页列表查询")
	@Operation(summary="运单主表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LttlWaybill>> queryPageList(LttlWaybill lttlWaybill,
                                                    @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<LttlWaybill> queryWrapper = QueryGenerator.initQueryWrapper(lttlWaybill, req.getParameterMap());
		Page<LttlWaybill> page = new Page<LttlWaybill>(pageNo, pageSize);
		IPage<LttlWaybill> pageList = lttlWaybillService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param lttlWaybillPage
	 * @return
	 */
	@AutoLog(value = "运单主表-添加")
	@Operation(summary="运单主表-添加")
    @RequiresPermissions("lttl:shipping:po:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody LttlWaybillPage lttlWaybillPage) {
		LttlWaybill lttlWaybill = new LttlWaybill();
		BeanUtils.copyProperties(lttlWaybillPage, lttlWaybill);
		lttlWaybillService.saveMain(lttlWaybill, lttlWaybillPage.getLttlWaybillGoodsList(),lttlWaybillPage.getLttlWaybillFeeList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param lttlWaybillPage
	 * @return
	 */
	@AutoLog(value = "运单主表-编辑")
	@Operation(summary="运单主表-编辑")
    @RequiresPermissions("lttl:shipping:po:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody LttlWaybillPage lttlWaybillPage) {
		LttlWaybill lttlWaybill = new LttlWaybill();
		BeanUtils.copyProperties(lttlWaybillPage, lttlWaybill);
		LttlWaybill lttlWaybillEntity = lttlWaybillService.getById(lttlWaybill.getId());
		if(lttlWaybillEntity==null) {
			return Result.error("未找到对应数据");
		}
		lttlWaybillService.updateMain(lttlWaybill, lttlWaybillPage.getLttlWaybillGoodsList(),lttlWaybillPage.getLttlWaybillFeeList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "运单主表-通过id删除")
	@Operation(summary="运单主表-通过id删除")
    @RequiresPermissions("lttl:shipping:po:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		lttlWaybillService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "运单主表-批量删除")
	@Operation(summary="运单主表-批量删除")
    @RequiresPermissions("lttl:shipping:po:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.lttlWaybillService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "运单主表-通过id查询")
	@Operation(summary="运单主表-通过id查询")
	@GetMapping(value = "/info")
	public Result<LttlWaybill> queryById(@RequestParam(name="id",required=true) String id) {
		LttlWaybill lttlWaybill = lttlWaybillService.getById(id);
		if(lttlWaybill==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(lttlWaybill);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "运单货物表-通过主表ID查询")
	@Operation(summary="运单货物表-通过主表ID查询")
	@GetMapping(value = "/goods")
	public Result<IPage<LttlWaybillGoods>> queryLttlWaybillGoodsListByMainId(@RequestParam(name="id",required=true) String id) {
		List<LttlWaybillGoods> lttlWaybillGoodsList = lttlWaybillGoodsService.selectByMainId(id);
		IPage <LttlWaybillGoods> page = new Page<>();
		page.setRecords(lttlWaybillGoodsList);
		page.setTotal(lttlWaybillGoodsList.size());
		return Result.OK(page);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "运单费用表-通过主表ID查询")
	@Operation(summary="运单费用表-通过主表ID查询")
	@GetMapping(value = "/fees")
	public Result<IPage<LttlWaybillFee>> queryLttlWaybillFeeListByMainId(@RequestParam(name="id",required=true) String id) {
		List<LttlWaybillFee> lttlWaybillFeeList = lttlWaybillFeeService.selectByMainId(id);
		IPage <LttlWaybillFee> page = new Page<>();
		page.setRecords(lttlWaybillFeeList);
		page.setTotal(lttlWaybillFeeList.size());
		return Result.OK(page);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param lttlWaybill
    */
    @RequiresPermissions("modules.lttl.shipping.po:lttl_waybill:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LttlWaybill lttlWaybill) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<LttlWaybill> queryWrapper = QueryGenerator.initQueryWrapper(lttlWaybill, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

     //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
           List<String> selectionList = Arrays.asList(selections.split(","));
           queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<LttlWaybill>  lttlWaybillList = lttlWaybillService.list(queryWrapper);

      // Step.3 组装pageList
      List<LttlWaybillPage> pageList = new ArrayList<LttlWaybillPage>();
      for (LttlWaybill main : lttlWaybillList) {
          LttlWaybillPage vo = new LttlWaybillPage();
          BeanUtils.copyProperties(main, vo);
          List<LttlWaybillGoods> lttlWaybillGoodsList = lttlWaybillGoodsService.selectByMainId(main.getId());
          vo.setLttlWaybillGoodsList(lttlWaybillGoodsList);
          List<LttlWaybillFee> lttlWaybillFeeList = lttlWaybillFeeService.selectByMainId(main.getId());
          vo.setLttlWaybillFeeList(lttlWaybillFeeList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "运单主表列表");
      mv.addObject(NormalExcelConstants.CLASS, LttlWaybillPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("运单主表数据", "导出人:"+sysUser.getRealname(), "运单主表"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("lttl:shipping:po:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          // 获取上传文件对象
          MultipartFile file = entity.getValue();
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<LttlWaybillPage> list = ExcelImportUtil.importExcel(file.getInputStream(), LttlWaybillPage.class, params);
              for (LttlWaybillPage page : list) {
                  LttlWaybill po = new LttlWaybill();
                  BeanUtils.copyProperties(page, po);
                  lttlWaybillService.saveMain(po, page.getLttlWaybillGoodsList(),page.getLttlWaybillFeeList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
