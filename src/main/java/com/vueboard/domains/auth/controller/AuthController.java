package com.vueboard.domains.auth.controller;

import com.vueboard.domains.auth.dto.AuthResponseDTO;
import com.vueboard.domains.auth.entity.User;
import com.vueboard.domains.auth.service.AuthService;
import com.vueboard.global.utils.CookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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

	@PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> req,  HttpServletResponse response) {
        String memberId = req.get("userId");
        String password = req.get("password");
        AuthResponseDTO user = authService.login(memberId, password, response);
        Map<String, Object> result = new HashMap<>();

        
        if (user != null) {
            result.put("success", true);
            result.put("user", user);
//            session.setAttribute("loginUser",user);
        } else {
            result.put("success", false);
        }
        return result;
    }

	/**
	 * 세션 확인 (Vue에서 현재 로그인 사용자 정보 확인용) => jwt access Token 적용 후 주석처리함
	 */
//	@GetMapping("/session")
//	public Map<String, Object> getSessionUser(HttpSession session) {
//		Map<String, Object> result = new HashMap<>();
//		User user = (User) session.getAttribute("loginUser");
//
//		if (user != null) {
//			result.put("isLogin", true);
//			result.put("user", user);
//		} else {
//			result.put("isLogin", false);
//		}
//
//		return result;
//	}

	/**
	 * 로그아웃
	 */
	@PostMapping("/logout")
	public String logout(HttpServletResponse response) {
	    Cookie deleteAccessToken= cookieUtil.deleteTokenCookie("accessToken");
	    response.addCookie(deleteAccessToken);
		return "logout success";
	}
}


