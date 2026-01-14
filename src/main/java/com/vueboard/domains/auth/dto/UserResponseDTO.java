package com.vueboard.domains.auth.dto;

import java.sql.Date;

import com.vueboard.domains.auth.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDTO {
	private Long pn;
	private String memberId;
	private String password;
	private String name;
	private String email;
	private Date regdate;
	
	public UserResponseDTO(User user) {
		this.pn=user.getPn();
		this.memberId=user.getMemberId();
		this.password=user.getPassword();
		this.name=user.getName();
		this.email=user.getEmail();
		this.regdate=user.getRegdate();
	}
	
}
