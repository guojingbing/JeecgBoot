package org.jeecg.modules.demo.api;
import org.jeecg.modules.demo.api.fallback.DemoHelloFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
