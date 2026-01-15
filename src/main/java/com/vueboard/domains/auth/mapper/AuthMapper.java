package com.vueboard.domains.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vueboard.domains.auth.dto.UserResponseDTO;
import com.vueboard.domains.auth.entity.User;

@Mapper
public interface AuthMapper {
		
	
	// 로그인
	// 아이디로 조회
    UserResponseDTO getUserInfoById(@Param("memberId") String memberId);
    
 // 해당 pn값에 해당하는 user 정보 조회
    UserResponseDTO findByPn(@Param("pn") Long pn);

}
