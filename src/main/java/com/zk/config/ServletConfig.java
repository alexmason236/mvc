package com.zk.config;

import com.zk.filter.T1AsyncHandlerInterceptor;
import com.zk.filter.T1Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.zk"},includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
},useDefaultFilters = false)
public class ServletConfig extends WebMvcConfigurerAdapter {
    @Autowired
    T1Filter t1Filter;
    @Autowired
    T1AsyncHandlerInterceptor t1AsyncHandlerInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(t1Filter).addPathPatterns("/**");
        registry.addInterceptor(t1AsyncHandlerInterceptor).addPathPatterns("/**");
    }
}
