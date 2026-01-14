package com.vueboard.global.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vueboard.domains.auth.dto.UserResponseDTO;
import com.vueboard.domains.auth.entity.User;
import com.vueboard.domains.auth.service.AuthService;
import com.vueboard.global.utils.CookieUtil;
import com.vueboard.global.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final CookieUtil cookieUtil;
	private final AuthService authservice;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = CookieUtil.resolveAccessTokenFromCookie(request); // 쿠키에서 accessToken 검증

		if (token != null && jwtUtil.validateAccessToken(token)) {

			long pn =Long.parseLong(jwtUtil.extractPn(token)); // accessToken에서 pn값 꺼내기
			
			UserResponseDTO user = authservice.findByPn(pn); // 사용자 식별

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, // principal
					null, List.of() 
			);

			SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext에 인증 정보 저장
		}

		filterChain.doFilter(request, response);

	}
}
