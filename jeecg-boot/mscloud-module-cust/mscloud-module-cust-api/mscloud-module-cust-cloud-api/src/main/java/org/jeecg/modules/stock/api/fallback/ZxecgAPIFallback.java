package org.jeecg.modules.stock.api.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.stock.api.ZxecgAPIClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author JeecgBoot
 */
@Slf4j
@Component
public class ZxecgAPIFallback implements FallbackFactory<ZxecgAPIClient> {
    @Setter
    private Throwable cause;

    @Override
    public ZxecgAPIClient create(Throwable throwable) {
        log.error("微服务接口调用失败： {}", cause);
        return null;
    }

}
