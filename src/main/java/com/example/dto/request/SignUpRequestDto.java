package com.example.dto.request;

import com.example.entity.enums.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {

	@NotBlank(message = "아이디를 입력해주세요.")
	private String username;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	private String password;

	@NotBlank(message = "이름을 입력해주세요.")
	private String name;

	@NotNull(message = "역할을 선택해주세요.")
	private Role role;
}
