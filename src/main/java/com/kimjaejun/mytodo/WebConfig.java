package com.kimjaejun.mytodo;

import com.kimjaejun.mytodo.interceptor.LogInterceptor;
import com.kimjaejun.mytodo.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","/jpeg/**");
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/signup","/login","/logout","/css/**","/jpeg/**");
        /*
          /**는 하위는 전부 다라는 뜻임
         */

    }
}
