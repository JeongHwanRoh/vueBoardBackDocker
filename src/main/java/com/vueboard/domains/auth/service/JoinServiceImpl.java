package com.vueboard.domains.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vueboard.domains.auth.entity.User;
import com.vueboard.domains.auth.mapper.JoinMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {
	
	private final JoinMapper joinMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public String checkEmailDuplicate(String email) {

		if (joinMapper.checkEmailDuplicate(email) == 0) {
			return ("사용 가능한 이메일입니다.");
		} else {
			return ("중복된 이메일은 사용할 수 없습니다.");
		}
	}

	@Override
	public void addUser(User user) {

        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword)); // 비밀번호 해시화
        joinMapper.addUser(user);
	}

}
