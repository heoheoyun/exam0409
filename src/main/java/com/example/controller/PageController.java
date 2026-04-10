package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	// 홈
	@GetMapping("/")
	public String index() {
		return "index";
	}

	// 로그인 페이지
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// 회원가입 페이지
	@GetMapping("/register")
	public String register() {
		return "register";
	}

	// 뱅킹 서비스 페이지
	@GetMapping("/banking")
	public String banking() {
		return "banking";
	}

	// 관리자 페이지
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
}
