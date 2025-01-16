package org.jeecg.modules.cust.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.cust.common.entity.SysHelpDocument;
import org.jeecg.modules.cust.common.service.ISysHelpDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
* @Description: 帮助文档表
* @Author:
* @Date:   2024-03-27
* @Version: V1.0
*/
@RestController
@RequestMapping("/mp/api/help/doc")
@Slf4j
public class SysHelpDocumentController extends JeecgController<SysHelpDocument, ISysHelpDocumentService> {
   @Autowired
   private ISysHelpDocumentService sysHelpDocumentService;

   /**
    * 分页列表查询
    *
    * @param sysHelpDocument
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @GetMapping(value = "/list")
   public Result<?> queryPageList(SysHelpDocument sysHelpDocument,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<SysHelpDocument> queryWrapper = QueryGenerator.initQueryWrapper(sysHelpDocument, req.getParameterMap());
       Page<SysHelpDocument> page = new Page<SysHelpDocument>(pageNo, pageSize);
       IPage<SysHelpDocument> pageList = sysHelpDocumentService.page(page, queryWrapper);
       return Result.ok(pageList);
   }

   /**
    *   添加
    *
    * @param sysHelpDocument
    * @return
    */
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody SysHelpDocument sysHelpDocument) {
       sysHelpDocumentService.save(sysHelpDocument);
       return Result.ok("添加成功！");
   }

   /**
    *  编辑
    *
    * @param sysHelpDocument
    * @return
    */
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody SysHelpDocument sysHelpDocument) {
       sysHelpDocumentService.updateById(sysHelpDocument);
       return Result.ok("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       sysHelpDocumentService.removeById(id);
       return Result.ok("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.sysHelpDocumentService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.ok("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       SysHelpDocument sysHelpDocument = sysHelpDocumentService.getById(id);
       if(sysHelpDocument==null) {
           return Result.error("未找到对应数据");
       }
       return Result.ok(sysHelpDocument);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param sysHelpDocument
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, SysHelpDocument sysHelpDocument) {
       return super.exportXls(request, sysHelpDocument, SysHelpDocument.class, "帮助文档表");
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
       return super.importExcel(request, response, SysHelpDocument.class);
   }

}
