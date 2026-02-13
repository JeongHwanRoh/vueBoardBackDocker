package com.vueboard.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	
	//1. 공통 에러
	INVALID_INPUT_VALUE("COMMON-400-001", HttpStatus.BAD_REQUEST, "잘못된 입력값입니다"),
	INVALID_TYPE_VALUE("COMMON-400-002", HttpStatus.BAD_REQUEST, "타입이 올바르지 않습니다"),
	MISSING_PARAMETER("COMMON-400-003", HttpStatus.BAD_REQUEST, "필수 파라미터가 누락되었습니다"),

	ACCESS_DENIED("COMMON-403-001", HttpStatus.FORBIDDEN, "접근 권한이 없습니다"),

	METHOD_NOT_ALLOWED("COMMON-405-001", HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP 메서드입니다"),
	NOT_SUPPORTED_MEDIA_TYPE("COMMON-415-001", HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 미디어 타입입니다"),

	INTERNAL_SERVER_ERROR("COMMON-500-001", HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다"),
	
	
	//2. 인증에러 401
	INVALID_TOKEN("AUTH-401-001", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),
	EXPIRED_TOKEN("AUTH-401-002", HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다"),
	UNAUTHORIZED("AUTH-401-003", HttpStatus.UNAUTHORIZED, "인증이 필요합니다"),
	
	
	//3. 인가 에러 403
	FORBIDDEN("AUTH-403-001", HttpStatus.FORBIDDEN, "권한이 없습니다"),
	
	//4. 요청(@valid), 검증 에러. JSON 파싱 실패 시 
	VALIDATION_ERROR("COMMON-400-010", HttpStatus.BAD_REQUEST, "요청값 검증에 실패했습니다"),
	JSON_PARSE_ERROR("COMMON-400-011", HttpStatus.BAD_REQUEST, "요청 JSON 파싱에 실패했습니다"),
	
	
	//5. Not Found
	USER_NOT_FOUND("USER-404-001", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
	RESOURCE_NOT_FOUND("COMMON-404-001", HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다"),
	ENDPOINT_NOT_FOUND("COMMON-404-002", HttpStatus.NOT_FOUND, "요청한 엔드포인트가 존재하지 않습니다"),
	CODE_NOT_FOUND("COMMON-404-002", HttpStatus.NOT_FOUND, "해당 코드를 찾을 수 없습니다"),
	
	
	//6. 중복, 충돌
	DUPLICATE_RESOURCE("COMMON-409-001", HttpStatus.CONFLICT, "이미 존재하는 리소스입니다"),
	USER_ALREADY_EXISTS("USER-409-001", HttpStatus.CONFLICT, "이미 가입된 사용자입니다"),
	
	//7. 서버/외부 API 에러
	EXTERNAL_API_ERROR("COMMON-502-001", HttpStatus.BAD_GATEWAY, "외부 API 호출에 실패했습니다"),
	SERVICE_UNAVAILABLE("COMMON-503-001", HttpStatus.SERVICE_UNAVAILABLE, "서비스를 사용할 수 없습니다"),
	TIMEOUT("COMMON-504-001", HttpStatus.GATEWAY_TIMEOUT, "요청 시간이 초과되었습니다");
	
	private final String code;
	private final HttpStatus status;
	private final String message;
	
}
