package com.vueboard.domains.auth.service;
import org.springframework.stereotype.Service;
import com.vueboard.domains.auth.entity.User;
import com.vueboard.domains.auth.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserMapper userMapper;
	
	// 로그인
	public User login(String memberId, String password) {
		return userMapper.login(memberId, password);
	}
}
