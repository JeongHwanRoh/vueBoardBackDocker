package com.vueboard.domains.auth.dto;

import com.vueboard.domains.auth.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDTO {
	private Long pn;
	private String memberId;
	private String name;
	private String email;
	
    public static AuthResponseDTO from(UserResponseDTO user) {
        return new AuthResponseDTO(
            user.getPn(),
            user.getMemberId(),
            user.getName(),
            user.getEmail()
        );
    }

}
