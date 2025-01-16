package org.jeecg.modules.cust.api.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.jeecg.modules.cust.api.MpHelloApi;
import lombok.Setter;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JeecgBoot
 */
@Slf4j
@Component
public class MpHelloFallback implements FallbackFactory<MpHelloApi> {
    @Setter
    private Throwable cause;

    @Override
    public MpHelloApi create(Throwable throwable) {
        log.error("微服务接口调用失败： {}", cause);
        return null;
    }

}
