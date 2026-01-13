package com.vueboard.global.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vueboard.domains.auth.dto.AuthResponseDTO;
import com.vueboard.domains.auth.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//jwt 생성 및 검증 유틸리티
@Component // 빈 등록
public class JwtUtil {

	// jwt 만료시간 설정
	public static final long EXPIRATION_TIME = 1000 * 60 * 15; // 유효기간 설정 15분 유효
	public static final long REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 30; // 유효기간 설정 30일 유효 리프레시 토큰용 (자동로그인 전용) 
	public static final long GENERAL_REFRESH_EXPIRATION_TIME = 1000 * 60 * 30; // 유효기간 설정 30분 유효 리프레시 토큰용 (일반로그인 전용) 
	public static final String JWT_SECKEY = "Lwz8gB1p9XzJ4mTYGmU7C2qK0D9tN3zQhF1kW5sP7yE2R8vC0lM6xA4nH9uT5rX"; // jwt 키

	// 생성자에서 secret_key를 주입받아 초기화
	// 선언
	private String secret_key; // 관리자 비밀 키. 이걸 암호화 해서 사용. 256비트 키
	private final SecretKey key; // JWT 서명 및 검증에 사용될 HMAC SHA 키 (256비트)

	public JwtUtil() {
		// HMAC SHA 서명 키로 변환. 이 키로 토큰을 검증. HMAC SHA 알고리즘 사용. 키는 최소 256비트(32바이트) 이상.
		this.secret_key = JWT_SECKEY; // 설정 파일에서 주입된 비밀 키 저장
		this.key = Keys.hmacShaKeyFor(secret_key.getBytes());
	}

	// 액세스 토큰 생성
	public String generateAccessToken(AuthResponseDTO user) { // 사용자 정보를 받아 jwt 생성
		return Jwts.builder() // jwt 빌더 객체를 생성
				.setSubject(String.valueOf(user.getPn())) // 토큰의 주체 설정(사용자 pk)
				.claim("email", user.getEmail()).claim("name", user.getName())
				.setIssuedAt(new Date()) // 발급시간 설정
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료시간 설정, 현재시간에 유효기간 추가.
				.signWith(key, SignatureAlgorithm.HS256) // 서명 생성
				.compact(); // 최종적으로 jwt 문자열을 생성하여 반환

	}

	// 액세스토큰 검증
	public boolean validateAccessToken(String token) {
		try {
			Jwts.parserBuilder() // jwt 파서 생성(파싱)
					.setSigningKey(key) // key를 사용하여 서명 검증.
					.build() //
					.parseClaimsJws(token); // 토큰을 해석 및 검증 => 유효하지 않으면 예외처리
			return true;

		} catch (ExpiredJwtException e) {
	        // 만료 예외는 다시 던져서 필터에서 처리
	        throw e;
	        
	    } catch (JwtException e) {
			return false;
		}

	}

	// 리프레시 토큰 생성
	public String generateRefreshToken(String uuid) {
		return Jwts.builder().setSubject(uuid).setIssuedAt(new Date()) // 발급시간 설정
				.setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
				.signWith(key, SignatureAlgorithm.HS256) // 서명 생성
				.compact(); // jwt 생성
	}
	
	public String generateGeneralRefreshToken(String uuid) {
		return Jwts.builder().setSubject(uuid).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	// 토큰 클레임 추출
	// 클레임 : jwt토큰 안에 있는 키-값 형태의 정보
	// jwt는 3부분으로 구성 HEADER.PAYLOAD.SIGNATURE
	// 이 중 PAYLOAD에 담긴 게 claims
	public Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	// 이메일 정보 추출
	public String extractUserEmail(String token) {
		return parseClaims(token).get("email", String.class); // 토큰에서 (email) 추출
		// 액세스 토큰 생성시 setSubject를 email로 설정했기 때문에 getSubject() 시 email 추출됨.
	}

	// 토큰에서 name 추출
	public String extractUserName(String token) {
		return parseClaims(token).get("name", String.class);
	}


	// 토큰에서 pn 추출
	public String extractPn(String token) {
		return parseClaims(token).getSubject();
	}

	public String generateAccessToken(Long pn, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public String extractUserPn(String token) {
		return parseClaims(token).getSubject();
	}

}