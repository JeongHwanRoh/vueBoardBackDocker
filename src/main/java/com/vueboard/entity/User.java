package com.vueboard.entity;

import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {
	
	private Long pn;
	private String memberId;
	private String password;
	private String name;
	private String email;
	private Date regdate;
}
