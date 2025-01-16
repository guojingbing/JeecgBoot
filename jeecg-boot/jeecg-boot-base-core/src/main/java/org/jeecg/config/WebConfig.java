package org.jeecg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2021-07-22 14:33:12
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    org.jeecg.modules.openapi.limit.OPENAPICallLimitInterceptor OPENAPICallLimitInterceptor;

    /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(OPENAPICallLimitInterceptor);
    }
}
