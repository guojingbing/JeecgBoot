package org.jeecg.modules.openapi.limit;

import com.google.common.collect.Maps;
import io.github.forezp.distributedlimitcore.constant.LimitType;
import io.github.forezp.distributedlimitcore.entity.LimitEntity;
import io.github.forezp.distributedlimitcore.entity.LimitResult;
import io.github.forezp.distributedlimitcore.exception.LimitException;
import io.github.forezp.distributedlimitcore.limit.LimitExcutor;
import io.github.forezp.distributedlimitcore.util.KeyUtil;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.openapi.base.entity.SysOpenAuthUser;
import org.jeecg.modules.openapi.base.service.ISysOpenAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:OPENAPI接口请求限流
 * @Author: Kingpin
 * @Date: 2021-07-22 14:35:51
 **/
@Component
public class OPENAPICallLimitInterceptor implements AsyncHandlerInterceptor {
    private Map<String, LimitEntity> limitEntityMap = Maps.newConcurrentMap();
    protected static String X_ACCESS_TOKEN = "X-Access-Token";
    static Map<String, SysOpenAuthUser> userMap=new ConcurrentHashMap<>();
    @Autowired
    ISysOpenAuthUserService ouSer;
    @Autowired
    LimitExcutor limitExcutor;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        //过滤限流uri
        if(!uri.contains("/oapi")||uri.contains("/oapi/token")){
            return true;
        }
        String token = request.getHeader(X_ACCESS_TOKEN);
        if(token==null){
            return true;
        }

        int secs=1;
        int times=2;
        //限流2个维度:  用户和api维度
        String identifier = JwtUtil.getUsername(token);
        if(!userMap.containsKey(identifier)){
            //TODO 写入redis提高效率
            SysOpenAuthUser owner=ouSer.getById(Long.parseLong(identifier));
            userMap.put(identifier,owner);
        }
        SysOpenAuthUser owner=userMap.get(identifier);
        if(owner==null||owner.getReqFreq()==null){
            throw new LimitException("没有配置接口调用频率限制,不能使用");
        }
        if(owner.getReqFreq().intValue()>0){
            times=owner.getReqFreq().intValue();
        }
        String composeKey = KeyUtil.compositeKey(identifier, uri);
        LimitEntity limitEntity = limitEntityMap.get(composeKey);
        if (limitEntity == null) {
            limitEntity = new LimitEntity();
            limitEntity.setIdentifier(identifier);
            limitEntity.setLimitType(LimitType.USER_URL);
            limitEntity.setKey(uri);
            limitEntity.setSeconds(secs);
            limitEntity.setLimtNum(times);
            limitEntityMap.putIfAbsent(composeKey, limitEntity);
        }
        LimitResult limitResult=limitExcutor.tryAccess(limitEntity);
        if (limitResult==null||limitResult.getResultType()== LimitResult.ResultType.FAIL) {
            throw new LimitException("接口调用过于频繁，触发每秒钟接口调用流控限制");
        }

        return true;
    }
}
