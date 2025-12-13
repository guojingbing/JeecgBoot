package org.jeecg.modules.zxecg.sysuser.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.zxecg.cust.service.phoenix.IPhoenixEcgService;
import org.jeecg.modules.zxecg.system.service.ICommBaseCodeService;
import org.jeecg.modules.zxecg.system.vo.CommBaseCodeDetailVO;
import org.jeecg.modules.zxecg.sysuser.dto.SysUserLoginDTO;
import org.jeecg.modules.zxecg.sysuser.service.ISysUserService;
import org.jeecg.modules.zxecg.sysuser.vo.SysUserVO;
import org.jeecg.modules.zxecg.util.ZxecgAesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Api(tags = "正心ECG医生登录")
@RestController
@RequestMapping("/zxecg")
public class LoginController {
	@Autowired
	private ISysUserService sysUserService;
    @Autowired
    private ICommBaseCodeService commBaseCodeService;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IPhoenixEcgService phoenixEcgService;

    @Value(value = "${zxecg.sys.preUrl}")
    private String preUrl;

	@ApiOperation(value = "login", notes = "登录接口")
//    @Operation(summary="登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result<?> login(@RequestBody SysUserLoginDTO sysUserLoginDTO, HttpServletRequest request){
		log.info(" ---我被调用了--- ");
        Result<JSONObject> result = new Result<JSONObject>();
//        phoenixEcgService.phoenixTest();

        //查询用户信息
        SysUserVO sysUserVO=sysUserService.getSystemUserVOByUserNoOrPhone(sysUserLoginDTO.getUserNo());
        //判断账号有效性
        if(sysUserVO==null){
            Result.error("账号不存在，请确认您输入的账号是否正确");
        }
        //查询通用代码配置
        Map<String, CommBaseCodeDetailVO> codeMap=commBaseCodeService.getCodeDetailsByCodeNames("SYS001", Arrays.asList("sys_comm_psd_springcloud"));
        //输入的密码
        String inPwd=sysUserLoginDTO.getPassword();
        //用户密码md5
        String digestPwd=DigestUtils.md5Hex(sysUserLoginDTO.getPassword().trim());

        //验证输入密码,若输入密码与用户密码不一致继续验证通用密码
        if(!digestPwd.equalsIgnoreCase(sysUserVO.getPassword())){
            boolean commPwdOk=false;
            if(codeMap!=null&&!codeMap.isEmpty()){
                CommBaseCodeDetailVO code=codeMap.get("sys_comm_psd_springcloud");
                //通用密码
                String commPwd=code.getCodeString();
                //加盐
                String salt=code.getExtraValue();
                try {
                    String encryptPwd= ZxecgAesUtil.aesEncrypt(inPwd,salt);
                    if(encryptPwd.equalsIgnoreCase(commPwd)){
                        commPwdOk=true;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if(!commPwdOk){
                return Result.error("您输入的账号或密码不正确，请确认");
            }
        }
        //判断账号有效性
        if(!sysUserVO.isUse()){
            Result.error("账号已禁用，请联系管理员");
        }

        //处理头像和签名图片url
        if(StringUtils.isNotBlank(sysUserVO.getUserAvatarAddr())&&!sysUserVO.getUserAvatarAddr().trim().startsWith("http://")&&!sysUserVO.getUserAvatarAddr().trim().startsWith("https://")){
            String url=preUrl;
            if(!url.endsWith("/")){
                url+="/";
            }
            url+="upload";
            if(!sysUserVO.getUserAvatarAddr().trim().startsWith("/")){
                url+="/";
            }
            url+=sysUserVO.getUserAvatarAddr().trim();
            sysUserVO.setUserAvatarAddr(url);
        }
        if(StringUtils.isNotBlank(sysUserVO.getUserSignPic())&&!sysUserVO.getUserSignPic().trim().startsWith("http://")&&!sysUserVO.getUserSignPic().trim().startsWith("https://")){
            String url=preUrl;
            if(!url.endsWith("/")){
                url+="/";
            }
            url+="upload";
            if(!sysUserVO.getUserSignPic().trim().startsWith("/")){
                url+="/";
            }
            url+=sysUserVO.getUserSignPic().trim();
            sysUserVO.setUserSignPic(url);
        }

        //处理token以及返回信息
        result=userInfo(sysUserVO,result,request);

        //记录登录日志

        return result;
	}

    /**
     * 用户信息
     * @param sysUser
     * @param result
     * @return
     */
    private Result<JSONObject> userInfo(SysUserVO sysUser, Result<JSONObject> result, HttpServletRequest request) {
        String userId = String.valueOf(sysUser.getUserId());
        // 获取用户部门信息
        JSONObject obj = new JSONObject(new LinkedHashMap<>());

        //1.生成token
        String token = JwtUtil.getZxecgTokenWithInfo(userId,sysUser.getPassword(),JwtUtil.EXPIRE_TIME * 2 / 1000,redisUtil);
        obj.put("token", token);

        //3.设置登录用户信息
        sysUser.setPassword(null);
        obj.put("userInfo", sysUser);

        result.setResult(obj);
        result.success("登录成功");

        return result;
    }

}
