package com.vueboard.domains.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vueboard.domains.auth.dto.UserResponseDTO;
import com.vueboard.domains.auth.entity.User;
import com.vueboard.domains.auth.mapper.AuthMapper;
import com.vueboard.global.utils.CookieUtil;
import com.vueboard.global.utils.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthMapper authMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtutil;
	private final CookieUtil cookieUtil;

	// 로그인
	public UserResponseDTO login(String memberId, String password, HttpServletResponse response) {
		UserResponseDTO user = authMapper.getUserInfoById(memberId);

		if (user == null) {
			return null;
		}
		if (!passwordEncoder.matches(password, user.getPassword())) {
			return null;
		}

		String accessToken = jwtutil.generateAccessToken(user);
		Cookie accessCookie = cookieUtil.createAccessTokenCookie("accessToken", accessToken);

		response.addCookie(accessCookie);

		return user;
	}

	// 해당 pn값에 해당하는 user 정보 조회
	public UserResponseDTO findByPn(long pn) {
		UserResponseDTO user = authMapper.findByPn(pn);
		return user;

	}
}
