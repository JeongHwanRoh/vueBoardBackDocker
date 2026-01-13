package com.vueboard.domains.auth.controller;

import com.vueboard.domains.auth.entity.User;
import com.vueboard.domains.auth.service.JoinService;
import com.vueboard.domains.auth.service.AuthService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/join") // Vue 프록시 설정에서 이미 "/api" 지정 -> URL 경로 충돌 피하고자 빈값으로 둠
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // Vue 포트 허용(일반vue용)
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // Vue 포트 허용(Nuxt vue용)
public class JoinController {

	private final JoinService joinService;

	@PostMapping("/memberJoin")
	public ResponseEntity<?> memberJoin(@RequestBody User user) {
		joinService.addUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
