package com.lksupply.lksupply2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map /uploads/filename.jpg -> local uploads folder
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");
    }
}
