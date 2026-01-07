package com.vueboard.domains.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vueboard.domains.auth.entity.User;

@Mapper
public interface UserMapper {
	
	// 회원가입
//	public int join(User user);
	
	// 로그인
	// 아이디와 비밀번호로 회원 조회
    User login(@Param("memberId") String memberId, @Param("password") String password);

}
