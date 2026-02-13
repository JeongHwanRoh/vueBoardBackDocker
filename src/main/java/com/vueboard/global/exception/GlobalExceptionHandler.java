package com.vueboard.global.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAllExceptions(Exception ex) {
		return new ResponseEntity<>("오류가 발생했습니다: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/*
	 *  비즈니스 에러 예외 핸들링
	 *  
	 *  사용법 :
	 *  
	 *  throw new ApiException(ErrorCode.USER_NOT_FOUND)
	 *  
	 *  .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND)
	 * 
	 * 	ErrorCode 객체에서 참조해서 예외 날릴 것.
	 * 
	 * */
	
	
	// SecurityException(403 Error)
	@ExceptionHandler(SecurityException.class)
	public ResponseEntity<ProblemDetail> handleSecurity(SecurityException e) {

	    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
	    problem.setTitle(e.getMessage());
	    problem.setProperty("errorCode", "FILE-403");

	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problem);
	}
	
	// IllegalArgumentException
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException e) {
	    Map<String, Object> body = new HashMap<>();
	    body.put("success", false);
	    body.put("message", e.getMessage());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
	
	// handleIOException
	@ExceptionHandler(IOException.class)
	public ResponseEntity<Map<String, Object>> handleIOException(IOException e) {
	    Map<String, Object> body = new HashMap<>();
	    body.put("success", false);
	    body.put("message", "파일 처리 중 오류가 발생했습니다.");
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
	}

	
}
