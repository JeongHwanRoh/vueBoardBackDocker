package com.vueboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vueboard.global.security.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;
	// SecurityFilterChain의 보안 필터 체인을 정의
	// 인증은 필터에서, 인가는 컨트롤러/서비스단에서 진행
	@Bean 
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors(Customizer.withDefaults()) // 시큐리티에 WebConfig에서 설정한 CORS 설정 추가
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.logout(logout -> logout.disable()) // Security 기본 LogoutFilter 비활성화 (/logout 컨토롤려 요청과 충돌 때문)
				// OPTIONS(CORS preflight) 
				// permitAll() : 로그인 없이도 security filter 통과 가능
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers("/login", "/logout", "/join/**", "/board/image/**", "/uploads/**").permitAll()
						// 나머지는 전부 로그인 인증 필요
						.anyRequest().authenticated())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
