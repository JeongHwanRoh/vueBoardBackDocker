package com.vueboard.domains.auth.controller;

import com.vueboard.domains.auth.dto.AuthResponseDTO;
import com.vueboard.domains.auth.dto.UserResponseDTO;
import com.vueboard.domains.auth.entity.User;
import com.vueboard.domains.auth.service.AuthService;
import com.vueboard.global.utils.CookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("") // Vue 프록시 설정에서 이미 "/api" 지정 -> URL 경로 충돌 피하고자 빈값으로 둠
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // Vue 포트 허용(일반vue용)
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // Vue 포트 허용(Nuxt vue용)
public class AuthController {

	private final AuthService authService;
	private final CookieUtil cookieUtil;

	/**
	 * 로그인
	 */
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Map<String, String> req, HttpServletResponse response) {
		String memberId = req.get("userId");
		String password = req.get("password");
		UserResponseDTO user = authService.login(memberId, password, response);
		Map<String, Object> result = new HashMap<>();

		if (user != null) {
			result.put("success", true);
			result.put("user", user);
		} else {
			result.put("success", false);
		}
		return result;
	}

	/**
	 * 새로고침 시 pinia에 재저장할 사용자 정보 요청하는 로직
	 */
	@GetMapping("/me")
	public AuthResponseDTO me(Authentication authentication) {
		UserResponseDTO user = (UserResponseDTO) authentication.getPrincipal(); // 현재 요청을 보낸 인증된 사용자객체 자체를 꺼내기
		System.out.println("사용자객체아이디값: "+ user.getMemberId());
		return AuthResponseDTO.from(user); // 프런트엔드 pinia에 재저장할 사용자 데이터 반환
	}

	/**
	 * 로그아웃
	 */
	@PostMapping("/logout")
	public String logout(HttpServletResponse response) {
		System.out.println("로그아웃 요청");
		Cookie deleteAccessToken = cookieUtil.deleteTokenCookie("accessToken");
		response.addCookie(deleteAccessToken);
		return "logout success";
	}
}
