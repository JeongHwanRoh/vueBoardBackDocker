package com.vueboard.global.utils;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// 쿠키 관련 유틸 모음.
@Component
@RequiredArgsConstructor
public class CookieUtil {

	private final JwtUtil jwtutil;

	/** Cross-Origin 요청에서 쿠키 전송(브라우저가 쿠키 전송하도록 설정) */
	public void addAccessTokenCookie(HttpServletResponse response, String tokenName, String token) {
		int maxAge = (int) (jwtutil.getAccessTokenExpiration() / 1000);

		String cookie = tokenName + "=" + token + "; Path=/" + "; HttpOnly" + "; SameSite=None" + "; Max-Age=" + maxAge
				+ "; Secure=false"; // local 개발환경

		response.addHeader("Set-Cookie", cookie);
	}

	/** AccessToken 쿠키 발급 */
	public Cookie createAccessTokenCookie(String tokenName, String token) {
		Cookie cookie = new Cookie(tokenName, token);
		cookie.setHttpOnly(true);
		cookie.setSecure(false);
		cookie.setPath("/");
		cookie.setMaxAge((int) (jwtutil.getAccessTokenExpiration() / 1000));
		return cookie;
	}

	/** RefreshToken 쿠키 발급 */
	public Cookie createRefreshTokenCookie(String tokenName, String token, boolean autoLogin) {
		Cookie refreshTokenCookie = new Cookie(tokenName, token);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(false);
		refreshTokenCookie.setPath("/");

		/*
		 * 자동로그인 유무에 따라 만료 시간이 다른 리프레쉬토큰 발급을 위해 autoLogin 조건을 사용 로그인 페이지 에서 자동로그인 체크시
		 * (true) 반환
		 */
		if (autoLogin) {
			refreshTokenCookie.setMaxAge((int) (jwtutil.getRefreshTokenExpiration() / 1000));
			// 클라이언트에서 30일 유지.(자동 로그인을 위한) !! 서버 측이 아님. 서버측은 jwtUtil에 있음.
		} else {
			refreshTokenCookie.setMaxAge((int) (jwtutil.getGeneralRefreshTokenExpiration() / 1000));
		}
		return refreshTokenCookie;
	}

	// 쿠키 삭제
	public Cookie deleteTokenCookie(String tokenName) {

		Cookie cookie = new Cookie(tokenName, null);
		cookie.setHttpOnly(true); // 자바스크립트에서 접근 불가. xss 공격 방지
		cookie.setSecure(false); // 로컬 개발 환경에서는 false로 설정.
		cookie.setPath("/");
		cookie.setMaxAge(0); // 즉시 만료

		return cookie;
	}

	// 쿠키에서 액세스 토큰 추출
	public static String resolveAccessTokenFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("accessToken".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	// 쿠키에서 리프레시 토큰 추출
	public static String resolveRefreshTokenFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("refreshToken".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
