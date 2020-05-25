package com.wangyu.common.config;

import com.wangyu.common.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: WangYu
 * @date: 2020-04-30
 */

@Configuration
public class WebMvcConfig{

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }
}
