package org.jeecg.modules.cust.cust.user.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencentyun.TLSSigAPIv2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.AesCbcUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.SysRedisUtil;
import org.jeecg.common.util.http.CrawlerUtil;
import org.jeecg.modules.base.service.BaseCommonService;
import org.jeecg.modules.cust.common.constant.MpConfig;
import org.jeecg.modules.cust.cust.user.entity.CustUser;
import org.jeecg.modules.cust.cust.user.entity.CustUserThirdAccount;
import org.jeecg.modules.cust.cust.user.entity.CustUserTrack;
import org.jeecg.modules.cust.cust.user.entity.WxUser;
import org.jeecg.modules.cust.cust.user.service.ICustUserService;
import org.jeecg.modules.cust.cust.user.service.ICustUserThirdAccountService;
import org.jeecg.modules.cust.cust.user.service.ICustUserTrackService;
import org.jeecg.modules.cust.cust.user.service.IWxUserService;
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

/**
 * @Description: 微信小程序平台接口
 * @Author:
 * @Date: 2020-02-17
 * @Version: V1.0
 */
@Api(tags = "微信小程序平台接口")
@RestController
@RequestMapping("/mp/api/wxmp")
@Slf4j
public class WxmpAPIController {
    @Value(value = "${jeecg.path.upload}")
    protected String uploadpath;

    @Autowired
    private IWxUserService wxUserService;
//    @Autowired
//    INshareDistriShopAdminService shopAdminService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysRedisUtil sysRedisUtil;
//    @Autowired
//    private INshareUserTeamService nshareUserTeamService;
    @Autowired
    ICustUserService cuSer;
    @Autowired
    ICustUserThirdAccountService cutaSer;
    @Autowired
    private BaseCommonService baseCommonService;
    @Autowired
    private ICustUserTrackService custUserTrackService;

    private Map<String, Map<String, String>> appAuth=MpConfig.appAuth;

    @ApiOperation(value = "获取微信用户敏感信息", notes = "获取微信用户敏感信息")
    @PostMapping(value = "/u/info")
    public Result<?> doGetUserInfo(@RequestBody String jsonStr) {
        Map map = new HashMap();
        //登录凭证不能为空
        if (jsonStr == null || jsonStr.length() == 0) {
            return Result.error("body请求参数获取失败");
        }
        JSONObject paramJson = JSONObject.parseObject(jsonStr);
        String code = paramJson.getString("code");
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            return Result.error("code 不能为空");
        }

        String wxspAppid, wxspSecret;
        //授权（必填）
        String grantType = "authorization_code";
        //小程序端设置的appid
        String appid = paramJson.getString("appid");
        if (StringUtils.isBlank(appid)) {
            wxspAppid = "wx035e90552bbb21fb";
        } else {
            wxspAppid = appid;
        }
        wxspSecret = appAuth.get(wxspAppid).get("MP-SECRET");
        try {
            //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
            Map params = new HashMap();

            params.put("appid", wxspAppid);
            params.put("secret", wxspSecret);
            params.put("js_code", code);
            params.put("grant_type", grantType);
            JSONObject json = CrawlerUtil.getForJSONObject("https://api.weixin.qq.com/sns/jscode2session", params, null, null, null);
            if(json==null||json.get("session_key")==null){
                return Result.error("session_key获取失败");
            }

            //获取会话密钥（session_key）
            String session_key = json.get("session_key").toString();
            map.put("sessionKey", session_key);
            appAuth.get(wxspAppid).put("SESSION_KEY",session_key);
            //用户的唯一标识（openid）
            String openid = (String) json.get("openid");

            //redis加锁避免并发导致重复创建用户
            String lockKey=sysRedisUtil.initKey("lock","mplogin",wxspAppid,openid);
            while(redisUtil.get(lockKey)!=null){
                Thread.sleep(100);
            }
            //默认锁定5秒
            redisUtil.set(lockKey,System.currentTimeMillis());
            redisUtil.expire(lockKey,5);

            //查询是否存在微信用户信息
            WxUser user = wxUserService.getOne(new LambdaQueryWrapper<WxUser>().eq(WxUser::getOpenid, openid).eq(WxUser::getAppid, wxspAppid));

            Map userMap=new HashMap(),tokenMap=null,trtcMap=null;
            if (user == null) {
                //微信用户不存在，解析数据并创建微信用户
                String encryptedData = paramJson.getString("encryptedData");
                String iv = paramJson.getString("iv");
                //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
                String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
                if (null != result && result.length() > 0) {
                    JSONObject userInfoJSON = JSONObject.parseObject(result);
                    user = new WxUser();
                    user.setOpenid(userInfoJSON.getString("openId"));
                    user.setAvatarUrl(userInfoJSON.getString("avatarUrl"));
                    user.setCity(userInfoJSON.getString("city"));
                    user.setCountry(userInfoJSON.getString("country"));
                    user.setGender(userInfoJSON.getString("gender"));
                    user.setUserName(userInfoJSON.getString("nickName"));
//                    user.setRealName(user.getUserName());
                    user.setProvince(userInfoJSON.getString("province"));
                    user.setUnionid(userInfoJSON.getString("unionId"));
                    user.setAppid(wxspAppid);
                    user.setIsUse("1");
                    user.setBindStatus("0");
                    wxUserService.save(user);
                    user = wxUserService.getOne(new LambdaQueryWrapper<WxUser>().eq(WxUser::getOpenid, openid).eq(WxUser::getAppid, wxspAppid));
                    userMap.put("wxUserInfo", user);
                    userMap.put("login",false);
                }else{
                    userMap=null;
                }
            } else {
                userMap.put("wxUserInfo", user);
                userMap.put("login",false);

                Map custUser=cuSer.getUserByThirdUserId(CommonConstant.CustUserThirdAccountType.WX_MP, appid, user.getId());
                if(custUser!=null&&custUser.get("bindStatus")!=null&&Integer.parseInt(custUser.get("bindStatus").toString())==1){
                    userMap.put("custUserInfo",custUser);
                    tokenMap=new HashMap();
                    userMap.put("userExpire",System.currentTimeMillis()+JwtUtil.EXPIRE_TIME/3);//登录有效时间
                    userMap.put("login",true);

                    //微信用户已存在，查询是否存在已经登录的业务账号
                    String token=JwtUtil.getTokenByCustUserId((String)custUser.get("id"),redisUtil);
                    tokenMap.put("tokenInfo", token);
                    tokenMap.put("tokenExpire",System.currentTimeMillis()+JwtUtil.EXPIRE_TIME);//token有效时间

                    //TRTC配置
                    if(appAuth.get(wxspAppid).containsKey("TRTC-SDKAPPID")){
                        //TRTC UserSig
                        long sdkAppId=Long.parseLong(appAuth.get(wxspAppid).get("TRTC-SDKAPPID"));
                        String sdkSecret=appAuth.get(wxspAppid).get("TRTC-SECRET");
                        TLSSigAPIv2 api = new TLSSigAPIv2(sdkAppId, sdkSecret);
                        String userId=user.getPhone();
                        String userSig = api.genUserSig(userId, 3 * 86400);
                        trtcMap=new HashMap();
                        trtcMap.put("sdkAppID", sdkAppId);
                        trtcMap.put("userSig", userSig);
                    }
                }
            }
            redisUtil.del(lockKey);

            map.put("userInfo", userMap);
            map.put("token", tokenMap);
            map.put("trtcConfig", trtcMap);
            return Result.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("用户信息获取出现异常");
    }

    @ApiOperation(value = "获取微信运动步数信息", notes = "解密微信运动步数")
    @PostMapping(value = "/werundata")
    public Result<?> doGetWeRunData(@RequestBody String jsonStr) {
        Map map = new HashMap();
        //登录凭证不能为空
        if (jsonStr == null || jsonStr.length() == 0) {
            return Result.error("body请求参数获取失败");
        }
        JSONObject paramJson = JSONObject.parseObject(jsonStr);
        String code = paramJson.getString("code");
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            return Result.error("code 不能为空");
        }

        String wxspAppid, wxspSecret;
        //授权（必填）
        String grantType = "authorization_code";
        //小程序端设置的appid
        String appid = paramJson.getString("appid");
        if (StringUtils.isBlank(appid)) {
            wxspAppid = "wx035e90552bbb21fb";
        } else {
            wxspAppid = appid;
        }
        wxspSecret = appAuth.get(wxspAppid).get("MP-SECRET");

        try {
            //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
            //发送请求
            Map params = new HashMap();
            params.put("appid", wxspAppid);
            params.put("secret", wxspSecret);
            params.put("js_code", code);
            params.put("grant_type", grantType);
            JSONObject json = CrawlerUtil.getForJSONObject("https://api.weixin.qq.com/sns/jscode2session", params, null, null, null);
            if(json==null||json.get("session_key")==null){
                return Result.error("session_key获取失败");
            }
            //获取会话密钥（session_key）
            String session_key = json.get("session_key").toString();
            appAuth.get(wxspAppid).put("SESSION_KEY",session_key);
            //用户的唯一标识（openid）
            String openid = (String) json.get("openid");

            //查询是否存在已绑定的账号
            WxUser user = wxUserService.getOne(new LambdaQueryWrapper<WxUser>().eq(WxUser::getOpenid, openid).eq(WxUser::getAppid, wxspAppid));
            if (user == null) {
                return Result.error("用户信息获取失败");
            }

            String encryptedData = paramJson.getString("encryptedData");
            String iv = paramJson.getString("iv");
            //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                JSONObject decryptData = JSONObject.parseObject(result);
                JSONArray stepInfoList = decryptData.getJSONArray("stepInfoList");

                map.put("steps", stepInfoList);
                return Result.ok(map);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return Result.error("解密失败");
    }

    /**
     * 业务流程：
     * 1、判断是否需要解密获取手机号（微信授权获取手机号）
     * 2、根据手机号判断是否已经存在cust_user账号，若不存在则自动创建（创建后通过业务系统接口获取账号认证信息，车主、司机、车辆）
     * 3、判断业务账号cust_user是否存在其他微信账号绑定，若存在则返回错误提示，若不存在则自动建立绑定关系（若openid已经绑定其他业务账号则替换，一个微信同时只能绑定一个业务账号），返回登录成功信息。
     * @param jsonStr
     * @param req
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "用户通过手机号登录，若不存在自动注册")
    @PostMapping(value = "/login")
    public Result<?> doUserLogin(@RequestBody String jsonStr, HttpServletRequest req) {
        Map map = new HashMap();
        JSONObject paramJson = JSONObject.parseObject(jsonStr);
        //微信用户信息
        JSONObject wxUser=paramJson.getJSONObject("wxUserInfo");
        if (wxUser == null) {
            return Result.error("缺少参数");
        }
        String wxUserId = wxUser.getString("id");
        //小程序端设置的appid
        String appid = paramJson.getString("appid");
        try {
            WxUser u = wxUserService.getById(wxUserId);
            if (u == null) {
                return Result.error("用户信息不存在");
            }

            //通过微信授权获取手机号，解密获取手机号
            String encryptedData = paramJson.getString("encryptedData");
            String iv = paramJson.getString("iv");
            if(!StringUtils.isBlank(encryptedData)){
                String session_key = appAuth.get(appid).get("SESSION_KEY");
                //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
                String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
                if (null != result && result.length() > 0) {
                    JSONObject decryptData = JSONObject.parseObject(result);
                    u.setPhone(decryptData.getString("phoneNumber"));
                }
            }else{
                String bussi=paramJson.getString("bussi");
                String phoneNumber=paramJson.getString("phoneNumber");
                String code=paramJson.getString("code");
                if(StringUtils.isBlank(code)){
                    return Result.error("请输入验证码");
                }

                List<Map<String, Object>> tlist=baseCommonService.getDictItemsByDictCode("sys_config","sms_templ_code-"+bussi);
                if(CollectionUtils.isEmpty(tlist)){
                    return Result.error("没有配置短信模板");
                }
                String templateCode=(String)tlist.get(0).get("item_value");

                String key=sysRedisUtil.initSmsCodeKey(templateCode,phoneNumber);
                String cacheCode=(String)redisUtil.get(key);
                if(StringUtils.isBlank(cacheCode)){
                    return Result.error("验证码失效，请重新获取");
                }
                if(!code.equalsIgnoreCase(cacheCode)){
                    return Result.error("验证码错误");
                }
                //通过手机验证码登录
                u.setPhone(phoneNumber);
                redisUtil.del(key);
            }

            //更新微信用户手机号信息
            wxUserService.saveOrUpdate(u);
            u=wxUserService.getById(wxUserId);

            //查询是否存在业务用户账号,若不存在则自动创建，若已存在则继续判断业务账号是否被其他微信绑定
            CustUser custUser=cuSer.getUserByPhoneNumber(u.getPhone(),appid);
            //当前微信与该账号绑定记录绑定
            CustUserThirdAccount myThirdAccount=null;
            boolean isNewUser=false;
            if(custUser==null){
                isNewUser=true;
                custUser=new CustUser();
                custUser.setAppid(appid);
                custUser.setUserNo(u.getPhone());
                custUser.setPhoneNumber(u.getPhone());
                custUser.setAvatar(u.getAvatarUrl());
                custUser.setUserName(u.getUserName());
                custUser.setCreateTime(new Date());
                custUser.setUpdateTime(new Date());
                custUser.setPassword(null);
                custUser.setSalt(null);
                custUser.setProvince(u.getProvince());
                custUser.setCity(u.getCity());
                custUser.setDistrict(u.getDistrict());
                cuSer.saveOrUpdate(custUser);
            }else{
                //业务账号已经存在判断账号是否已经绑定其他微信
                LambdaQueryWrapper<CustUserThirdAccount> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(true,CustUserThirdAccount::getUserId,custUser.getId());
                List<CustUserThirdAccount> cutas=cutaSer.list(queryWrapper);
                if(!CollectionUtils.isEmpty(cutas)){
                    for(CustUserThirdAccount cuta:cutas){
                        if(!cuta.getThirdUserUuid().equalsIgnoreCase(u.getOpenid())&&cuta.getBindStatus().intValue()==1){
                            return Result.error("账号已经被其他微信绑定");
                        }else if(cuta.getThirdUserUuid().equalsIgnoreCase(u.getOpenid())){
                            myThirdAccount=cuta;
                        }
                    }
                }
            }
            if(myThirdAccount==null){
                myThirdAccount=new CustUserThirdAccount();
                myThirdAccount.setUserId(custUser.getId());
                myThirdAccount.setNickName(u.getUserName());
                myThirdAccount.setThirdType(CommonConstant.CustUserThirdAccountType.WX_MP);
                myThirdAccount.setThirdUserUuid(u.getOpenid());
                myThirdAccount.setThirdUserId(u.getId());
                myThirdAccount.setCreateTime(new Date());
            }
            myThirdAccount.setBindStatus(1);
            myThirdAccount.setUpdateTime(new Date());
            cutaSer.saveOrUpdate(myThirdAccount);

            Map userMap=new HashMap();
            userMap.put("wxUserInfo",u);
            userMap.put("custUserInfo",custUser);
            userMap.put("isNewCustUser",isNewUser);
            map.put("user",userMap);

            return Result.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("保存失败");
    }

    @ApiOperation(value = "微信退出登录", notes = "微信退出登录")
    @PostMapping(value = "/logout")
    public Result<?> doUserLogout(@RequestBody String jsonStr, HttpServletRequest req) {
        String token = req.getHeader(CommonConstant.X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);
        CustUserThirdAccount cuta = cutaSer.getOne(new LambdaQueryWrapper<CustUserThirdAccount>().eq(CustUserThirdAccount::getUserId, userId).eq(CustUserThirdAccount::getThirdType, CommonConstant.CustUserThirdAccountType.WX_MP));
        if(cuta!=null){
            cuta.setBindStatus(2);
            cutaSer.saveOrUpdate(cuta);
        }
        return Result.ok("退出成功");
    }

    @ApiOperation(value = "获取用户信息", notes = "根据手机号获取用户信息")
    @GetMapping(value = "/user")
    public Result<?> doGetUserByPhone(@RequestParam(name = "phone") String phone,@RequestParam(name = "appid") String appid) {
        QueryWrapper<WxUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone.trim());
        queryWrapper.eq("appid", appid.trim());
        WxUser user = wxUserService.getOne(queryWrapper);
        return Result.ok(user);
    }

    @ApiOperation(value = "获取用户角色信息", notes = "获取用户授权角色相关信息")
    @GetMapping(value = "/roles")
    public Result<?> doGetUserRoles(HttpServletRequest req) {
        String token = req.getHeader(CommonConstant.X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);

//        JSONObject paramJson = JSONObject.parseObject(jsonStr);
//        //小程序端设置的appid
//        String appid = paramJson.getString("appid");

//        //查询车主信息
//        QueryWrapper<LogisticsCarOwner> carOwnerQueryWrapper = new QueryWrapper<>();
//        carOwnerQueryWrapper.eq("user_id", userId);
//        List<LogisticsCarOwner> carOwners=lcoSer.list(carOwnerQueryWrapper);
//
//        //查询车主用户
//        QueryWrapper<LogisticsCarOwnerUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", userId);
//        List<LogisticsCarOwnerUser> carOwnerUsers=lcouSer.list(queryWrapper);
//        //查询货主用户
//        List cargoOwnerUsers=couSer.getUserCargoOwners(userId);
        Map reMap=new HashMap();
//        reMap.put("cargoOwnerUsers",cargoOwnerUsers);
//        reMap.put("carOwnerUsers",carOwnerUsers);
//        reMap.put("carOwner",CollectionUtils.isEmpty(carOwners)?null:carOwners.get(0));

        return Result.ok(reMap);
    }

    @ApiOperation(value = "用户信息更新接口", notes = "用户信息更新接口")
    @PostMapping(value = "/cust/info")
    public Result<?> updateCustUserInfo(@RequestBody String jsonStr, HttpServletRequest request) {
        try {
            String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
            String userId = JwtUtil.getUsername(token);

            JSONObject paramJson = JSONObject.parseObject(jsonStr);
            JSONObject userInfo=paramJson.getJSONObject("userInfo");
            if(userInfo==null){
                Result.error("参数错误");
            }

            CustUser custUser=cuSer.getById(userId);
            if(custUser==null){
                Result.error("用户不存在");
            }
            custUser.setUserName(userInfo.getString("userName"));
            custUser.setProvince(userInfo.getString("province"));
            custUser.setCity(userInfo.getString("city"));
            custUser.setDistrict(userInfo.getString("district"));
            cuSer.saveOrUpdate(custUser);

            return Result.ok(custUser);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("用户信息更新失败");
        }
    }

    @ApiOperation(value = "通用文件上传接口", notes = "文件上传接口")
    @PostMapping(value = "/upload")
    public Result<?> upload(HttpServletRequest request, HttpServletResponse response) {
        try {
            String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
            String userId = JwtUtil.getUsername(token);
            //获取formData
            String fileType = request.getParameter("fileType");
            String bussi = request.getParameter("bussi");
            String dayDir = request.getParameter("dayDir");
            String userName = request.getParameter("userName");

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
            MultipartFile mf = multipartRequest.getFile("file");// 获取上传文件对象

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

            //文件保存相对路径返回给前端，前端提交到业务
            String dbpath = relativePath + fileName;
            if (dbpath.contains("\\")) {
                dbpath = dbpath.replace("\\", "/");
            }

            if(bussi.equalsIgnoreCase("avatar")){
                CustUser custUser=cuSer.getById(userId);
                if(custUser==null){
                    return Result.error("用户信息不存在，头像上传失败");
                }
                custUser.setAvatar(dbpath);
                cuSer.saveOrUpdate(custUser);
            }

            Map reData=new HashMap();
            reData.put("filePath",dbpath);
            return Result.ok(reData);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return Result.error("文件上传失败");
    }

    @ApiOperation(value = "用户位置跟踪接口", notes = "提交用户位置跟踪信息接口")
    @PostMapping(value = "/track")
    public Result<?> doPostCustUserTrack(@RequestBody String jsonStr, HttpServletRequest request) {
        try {
            String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
            String userId = JwtUtil.getUsername(token);

            JSONObject paramJson = JSONObject.parseObject(jsonStr);
            JSONObject trackInfo=paramJson.getJSONObject("trackInfo");

            if(trackInfo==null){
                Result.error("参数错误");
            }

            CustUserTrack userTrack=trackInfo.toJavaObject(CustUserTrack.class);
            userTrack.setUserId(userId);
            userTrack.setCreateBy(userId);
            if(userTrack.getLocTime()==null){
                userTrack.setLocTime(new Date());
            }
            custUserTrackService.saveOrUpdate(userTrack);

            return Result.ok("运力跟踪信息提交成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("运力跟踪信息提交失败");
        }
    }

    @ApiOperation(value = "获取运力跟踪信息", notes = "根据指定区域代码或位置获取指定半径日期范围内车主位置跟踪信息")
    @GetMapping(value = "/tracks")
    public Result<?> doGetCarOwnerTracks(
            @RequestParam(name = "appid", required = false) String appid,
            @RequestParam(name = "adcode", required = false) String adcode,
            @RequestParam(name = "afterDate", required = false) String afterDate,
            @RequestParam(name = "latlng", required = false) String latlng,
            @RequestParam(name = "radius", required = false) Integer radius, HttpServletRequest req) {
        String token = req.getHeader(CommonConstant.X_ACCESS_TOKEN);
        String userId = JwtUtil.getUsername(token);

        Map map = new HashMap();
        List<Map> track = custUserTrackService.getCustUserTracks(appid, adcode, afterDate, latlng, radius);
        map.put("track", track);

        return Result.ok(map);
    }
}
