package org.jeecg.modules.cust.common.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.enums.DySmsEnum;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.DySmsHelper;
import org.jeecg.common.util.SysRedisUtil;
import org.jeecg.modules.cust.common.entity.SysCommAttach;
import org.jeecg.modules.cust.common.entity.SysCommAttachItem;
import org.jeecg.modules.cust.common.entity.SysHelpDocument;
import org.jeecg.modules.cust.common.service.IMpCommonService;
import org.jeecg.modules.cust.common.service.ISysCommAttachItemService;
import org.jeecg.modules.cust.common.service.ISysCommAttachService;
import org.jeecg.modules.cust.common.service.ISysHelpDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(tags = "小程序-通用")
@RestController
@RequestMapping("/mp/api/comm")
@Slf4j
public class MpCommonAPIController {
    @Value(value = "${jeecg.path.upload}")
    protected String uploadpath;
    @Autowired
    private IMpCommonService mpCommonService;
    @Autowired
    private ISysCommAttachService attachSer;
    @Autowired
    private ISysCommAttachItemService attachItemSer;
    @Autowired
    private ISysHelpDocumentService sysHelpDocumentSer;
    @Autowired
    private SysRedisUtil sysRedisUtil;

    @ApiOperation(value = "获取数据字典", notes = "获取指定代码的数据字典")
    @GetMapping(value = "/dict/items")
    public Result<?> doGetDictItems(@RequestParam(name = "dictCode", required = false) String dictCode, @RequestParam(name = "itemCode", required = false) String itemCode, HttpServletRequest req) {
        List<Map<String, Object>> list=mpCommonService.getDictItemsByDictCode(dictCode,itemCode);
        Map map = new HashMap();
        map.put("items",list);
        return Result.ok(map);
    }

    @ApiOperation(value = "获取帮助文档", notes = "按文档代码获取帮助文档，例如隐私协议")
    @GetMapping(value = "/doc")
    public Result<?> doGetAreaTree(@RequestParam(name = "code") String code, HttpServletRequest req) {
        LambdaQueryWrapper<SysHelpDocument> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(true,SysHelpDocument::getDocCode,code);
        List<SysHelpDocument> docs=sysHelpDocumentSer.list(queryWrapper);
        Map map = new HashMap();
        map.put("doc", CollectionUtils.isEmpty(docs)?null:docs.get(0));
        return Result.ok(map);
    }

    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码")
    @PostMapping(value = "/send/sms")
    public Result<?> doSendSms(@RequestBody JSONObject body, HttpServletRequest req) {
        /**
         * smsmode 短信模板方式
         * 0 .登录模板、1.注册模板、2.忘记密码模板
         * 110.小程序登录认证
         */
        String bussi=body.getString("bussi");
        String phoneNumber=body.getString("phoneNumber");
        Long timestamp=body.getLong("timestamp");
        String sign=body.getString("sign");

        //查询系统配置字典项
        List<Map<String, Object>> list=mpCommonService.getDictItemsByDictCode("sys_config","sys_config_entrypted_salt");

        String salt="JHWL";
        if(!CollectionUtils.isEmpty(list)){
            salt=(String)list.get(0).get("item_value");
        }

        String preEntryStr=bussi+phoneNumber+timestamp+salt;
        String entryedStr= DigestUtils.md5Hex(preEntryStr);
        if(!sign.equalsIgnoreCase(entryedStr)){
            return Result.error("非法请求");
        }

        List<Map<String, Object>> tlist=mpCommonService.getDictItemsByDictCode("sys_config","sms_templ_code-"+bussi);
        if(CollectionUtils.isEmpty(tlist)){
            return Result.error("没有配置短信模板");
        }
        String templateCode=(String)tlist.get(0).get("item_value");

        DySmsEnum smsTemplate= DySmsEnum.MP_LOGIN_TEMPLATE_CODE;
        smsTemplate.setTemplateCode(templateCode);

        //随机数
        String captcha = RandomUtil.randomNumbers(6);
        JSONObject obj = new JSONObject();
        obj.put("code", captcha);
        //验证码10分钟内有效
        String key=sysRedisUtil.initSmsCodeKey(templateCode,phoneNumber);
        sysRedisUtil.set(key, captcha, 600);
        //微信小程序登录模板
        try {
            boolean b = DySmsHelper.sendSms(phoneNumber, obj, smsTemplate);
            if(b){
                return Result.ok("验证码发送成功");
            }else{
                return Result.error("短信发送失败");
            }
        } catch (ClientException e) {
            return Result.error(e.getMessage());
        }
    }

    @ApiOperation(value = "获取业务单附件信息", notes = "获取业务单附件信息，若不存在自动关联生成附件主表")
    @PostMapping(value = "/upload/info")
    public Result<?> doLoadBussiAttach(@RequestBody JSONObject body, HttpServletRequest request) {
        Map reData=new HashMap();
        String bussi=body.getString("bussi");
        String bussiDataId=body.getString("bussiDataId");
        String attachId=body.getString("attachId");
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        SysCommAttach attach;
        if(StringUtils.isNotBlank(attachId)){
            attach=attachSer.getById(attachId);
        }else{
            LambdaQueryWrapper<SysCommAttach> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(true, SysCommAttach::getBussi, bussi);
            queryWrapper.eq(true,SysCommAttach::getBussiDataId,bussiDataId);
            attach=attachSer.getBaseMapper().selectOne(queryWrapper);
        }

        if(attach==null){
            attach=new SysCommAttach();
            attach.setBussi(bussi);
            attach.setBussiDataId(bussiDataId);
            attach.setRemark("追踪节点文件上传关联附件表");
            attach.setCreateBy(userId);
            attach=attachSer.saveOrUpdateAttach(attach,null);
        }else{
            //查询附件子表
            LambdaQueryWrapper<SysCommAttachItem> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(true, SysCommAttachItem::getAttachId, attach.getId());
            queryWrapper1.orderBy(true,true,SysCommAttachItem::getCreateTime);
            List<SysCommAttachItem> items=attachItemSer.getBaseMapper().selectList(queryWrapper1);
            reData.put("items",items);
        }
        reData.put("attach",attach);
        return Result.ok(reData);
    }

    @ApiOperation(value = "删除文件", notes = "删除附件中的指定文件")
    @DeleteMapping(value = "/upload/item")
    public Result<?> doDeleteFile(@RequestBody JSONObject body, HttpServletRequest request) {
        String id=body.getString("id");
        attachItemSer.removeById(id);
        return Result.ok("删除成功");
    }

    @ApiOperation(value = "客户端通用文件上传接口", notes = "客户端通用文件上传接口")
    @PostMapping(value = "/upload")
    public Result<?> upload(HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject reData=new JSONObject();
            String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
            String userId = JwtUtil.getUsername(token);
            //获取formData
            String attachId = request.getParameter("attachId");
            String fileType = request.getParameter("fileType");
            String bussi = request.getParameter("bussi");
            String dayDir = request.getParameter("dayDir");

            //相对系统上传文件根目录的路径
            String relativePath=fileType + File.separator + bussi + File.separator;
            if(StringUtils.isNotBlank(dayDir)&&dayDir.equalsIgnoreCase("1")){
                String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
                relativePath += nowday;
            }
            relativePath+=File.separator+userId+File.separator;
            //创建文件根目录
            File file = new File(uploadpath + File.separator + relativePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> mfList=multipartRequest.getFiles("file");//获取上传的多文件
            SysCommAttach attach=null;
            if(!CollectionUtils.isEmpty(mfList)){
                if(StringUtils.isNotBlank(attachId)){
                    attach=attachSer.getById(attachId);
                }else{
                    attach=new SysCommAttach();
                }
                List<SysCommAttachItem> items=new ArrayList<>();
                for(MultipartFile mf:mfList){
                    //获取文件名并设置保存文件名
                    String orgName = mf.getOriginalFilename();
                    String contentType=mf.getContentType();
                    String extName=".tmp";
                    if(StringUtils.isBlank(contentType)||contentType.equalsIgnoreCase("null")){
                        if(fileType.equalsIgnoreCase("imgs")){
                            extName=".png";
                        }
                    }else{
                        extName=orgName.substring(orgName.indexOf("."));
                    }
                    String fileName = System.currentTimeMillis() + "-" + UUID.randomUUID().toString().replace("-", "") + extName;
                    String savePath = file.getPath() + File.separator + fileName;
                    //保存文件
                    File savefile = new File(savePath);
                    FileCopyUtils.copy(mf.getBytes(), savefile);

                    //文件保存相对路径
                    String dbpath = relativePath + fileName;
                    if (dbpath.contains("\\")) {
                        dbpath = dbpath.replace("\\", "/");
                    }

                    SysCommAttachItem item=new SysCommAttachItem();
                    item.setAttachId(attach.getId());
                    item.setFileName(fileName);
                    item.setOrigFileName(orgName);
                    item.setFilePath(dbpath);
                    items.add(item);
                }
                attach=attachSer.saveOrUpdateAttach(attach,items);
            }
            LambdaQueryWrapper<SysCommAttachItem> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(true, SysCommAttachItem::getAttachId, attach.getId());
            queryWrapper.orderBy(true,true,SysCommAttachItem::getCreateTime);
            List<SysCommAttachItem> items=attachItemSer.list(queryWrapper);
            reData.put("attach",attach);
            reData.put("items",items);
            return Result.ok(reData);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return Result.error("文件上传失败");
    }
}