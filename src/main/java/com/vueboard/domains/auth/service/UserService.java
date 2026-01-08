package com.vueboard.domains.auth.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.vueboard.domains.auth.entity.User;
import com.vueboard.domains.auth.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	// 로그인
	public User login(String memberId, String password) {
		User user = userMapper.login(memberId);

		if (user == null) {
			return null;
		}
		System.out.println("비밀번호값:"+ password);
		System.out.println("비밀번호값:"+ user.getPassword());
		if (!passwordEncoder.matches(password, user.getPassword())) {
			return null;
		}

		return user;
	}
}
