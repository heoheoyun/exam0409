package com.example.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// RuntimeException 발생 시 메시지만 반환
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	// 그 외 모든 예외도 메시지만 반환
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
