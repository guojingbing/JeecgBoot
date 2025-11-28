package org.jeecg.modules.zxecg.api;
import org.jeecg.modules.zxecg.api.fallback.DemoHelloFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "jeecg-demo", fallbackFactory = DemoHelloFallback.class)
public interface DemoHelloApi {

    /**
     * demo hello 微服务接口
     * @param
     * @return
     */
    @GetMapping(value = "/demo/hello")
    String callHello();
}
