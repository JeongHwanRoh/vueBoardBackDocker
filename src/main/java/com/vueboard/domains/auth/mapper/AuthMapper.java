package com.vueboard.domains.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vueboard.domains.auth.dto.AuthResponseDTO;
import com.vueboard.domains.auth.entity.User;

@Mapper
public interface AuthMapper {
		
	
	// 로그인
	// 아이디로 조회
    AuthResponseDTO getUserInfoById(@Param("memberId") String memberId);

}
