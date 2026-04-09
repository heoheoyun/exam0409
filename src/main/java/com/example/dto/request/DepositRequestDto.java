package com.example.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositRequestDto {

	@NotNull
	@Min(value = 1, message = "1원 이상 입력해주세요.")
	private Long amount;
}
