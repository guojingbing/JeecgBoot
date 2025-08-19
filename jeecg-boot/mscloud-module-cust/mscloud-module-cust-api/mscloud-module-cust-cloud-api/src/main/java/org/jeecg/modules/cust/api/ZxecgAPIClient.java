package org.jeecg.modules.cust.api;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.ServiceNameConstants;
import org.jeecg.modules.cust.api.fallback.ZxecgAPIFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = ServiceNameConstants.SERVICE_ZXECG, fallbackFactory = ZxecgAPIFallback.class)
public interface ZxecgAPIClient {
    @GetMapping(value = "/zxecg/base/dict/items")
    Result<?> getDictItems(@RequestParam(value = "codes",required = false) String codes);
}
