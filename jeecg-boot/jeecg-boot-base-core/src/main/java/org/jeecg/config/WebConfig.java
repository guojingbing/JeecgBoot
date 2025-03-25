package org.jeecg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

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
