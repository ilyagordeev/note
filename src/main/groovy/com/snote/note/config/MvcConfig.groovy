package com.snote.note.config

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class MvcConfig implements WebMvcConfigurer {

void addViewControllers(ViewControllerRegistry registry) {

//    registry.addViewController("/").setViewName("index")
//    registry.addViewController("/hello").setViewName("hello")
//    registry.addViewController("/login").setViewName("login");

    }

}
