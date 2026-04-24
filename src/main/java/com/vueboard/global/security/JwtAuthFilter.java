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

		log.info("🔥 JwtAuthFilter HIT: {} {}", request.getMethod(), request.getRequestURI());

		String uri = request.getRequestURI();
		// OPTIONS 요청은 무조건 통과
		// 프런트에서 쿠키를 포함해 요청하기 때문에(withCredentials:true)
		// 보안적으로 OPTIONS가 발생함.
		// 이 예외상황에서도 JWT 검사 제외한다.
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			filterChain.doFilter(request, response);
			return;
		}

		// JWT 검사 제외 경로
	    if (isPublicPath(uri)) {
			System.out.println("uri :"+uri);
	        filterChain.doFilter(request, response);
	        return;
	    }
	    try{
			String token = CookieUtil.resolveAccessTokenFromCookie(request); // 쿠키에서 accessToken 검증
	
			if (token != null && jwtUtil.validateAccessToken(token)) {
				
				long pn = Long.parseLong(jwtUtil.extractPn(token)); // accessToken에서 pn값 꺼내기
	
				UserResponseDTO user = authservice.findByPn(pn); // 사용자 식별
	
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, // principal
						null, List.of());
				log.info("✅ JWT 인증 성공: pn={}, uri={}", pn, uri);
				SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext에 인증 정보 저장
			}

		}catch(Exception e){
			log.error("JWT 인증 중 오류 발생: {}", e.getMessage());
			// JWT 검증 실패 시, SecurityContext에 인증 정보가 없으므로 401 Unauthorized 응답이 자동으로 처리됨
		}

		filterChain.doFilter(request, response);

	}
	
	// 로그인 없이도 접근 가능한 URI들
	private boolean isPublicPath(String uri) {
	    return uri.startsWith("/api/board/image/")
	    	|| uri.startsWith("/api/board/content/")
	        || uri.startsWith("/api/uploads/")
	        || uri.startsWith("/api/login")
	        || uri.startsWith("/api/join");
	}

}


