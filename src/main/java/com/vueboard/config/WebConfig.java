package com.vueboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//작동 원리
//
//SockJS는 WebSocket 연결 전에 xhr-polling 기반의 preflight 요청(OPTIONS, GET /info) 을 날린다.
//
//이 요청은 WebSocket 핸들러(WebSocketConfig)가 아니라 Spring MVC DispatcherServlet이 먼저 받는다.
//
//MVC에서 Access-Control-Allow-Origin 헤더가 없으면 브라우저가 CORS 차단.
//
//따라서 WebMvcConfigurer.addCorsMappings() 으로 전역 CORS 허용이 필요하다.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns(
                "http://localhost:3000",
                "http://localhost:*",
                "http://frontend:3000",
                "http://192.168.2.30:3000"
            )
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
    
    // 프런트 proxy(routeRules)에서 요청한 /uploads 요청(이미지 파일 조회 및 접근 요청)에 대한 응답 로직
    // 1) addResourceHandler: Spring WAS에 ResourceHandler에 경로 등록한다
    // 2) 프런트에서 vite.server.proxy (nuxt.config.ts) 요청을 보낸다
    // 3) 요청이 들어오면 ResourceHandlerRegistry를 통해 스프링 컨테이너가 해당 요청을 addResourceHandler 객체에 주입한다.(추가)
    //  - ResourceHandlerRegistry:  스프링 컨테이너가 주입해주는 설정 관리 객체
    // 브라우저에서 동작, Docker에서는 동작x
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/uploads/**") // 브라우저에서 접근할 URL 경로
				.addResourceLocations("file:/app/uploads/"); // 실제 로컬 디스크 경로
	}
}

