package org.jeecg.modules.cust.api;

import org.jeecg.common.constant.ServiceNameConstants;
import org.jeecg.modules.cust.api.fallback.MpHelloFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(contextId = "mpBaseRemoteApi", value = ServiceNameConstants.SERVICE_CUST, fallbackFactory = MpHelloFallback.class)
//@ConditionalOnMissingClass("org.jeecg.modules.system.service.impl.SysBaseApiImpl")
public interface MpHelloApi {
    /**
     * mp hello 微服务接口
     * @param
     * @return
     */
    @GetMapping(value = "/mp/api/comm/hello")
    String callHello();
}
