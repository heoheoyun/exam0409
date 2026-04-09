package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.request.LoginRequestDto;
import com.example.dto.request.SignUpRequestDto;
import com.example.dto.response.LoginResponseDto;
import com.example.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	// 회원가입
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody SignUpRequestDto dto) {
		authService.signUp(dto);
		return ResponseEntity.ok("회원가입 완료");
	}

	// 로그인 - 성공 시 JWT 토큰 반환
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
		return ResponseEntity.ok(authService.login(dto));
	}
}
