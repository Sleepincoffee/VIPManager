package com.sprint;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration; 
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
@SpringBootApplication
//@EnableRedisHttpSession
public class Application extends SpringBootServletInitializer{
	
	//Gradle WAR
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        System.out.print("VIP管理系统启动成功");
    }

}
