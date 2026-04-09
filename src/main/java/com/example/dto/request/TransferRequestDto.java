package com.example.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequestDto {

	@NotBlank(message = "수신 계좌번호를 입력해주세요.")
	private String receiverAccountNumber;

	@NotNull
	@Min(value = 1, message = "1원 이상 입력해주세요.")
	private Long amount;
}
