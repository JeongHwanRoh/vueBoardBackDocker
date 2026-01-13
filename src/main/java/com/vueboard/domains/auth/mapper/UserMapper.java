package com.vueboard.domains.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vueboard.domains.auth.entity.User;

@Mapper
public interface UserMapper {
		
	
	// 로그인
	// 아이디와 비밀번호로 회원 조회
    User getUserInfoById(@Param("memberId") String memberId);

}
