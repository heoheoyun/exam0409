package com.example.dto.response;

import com.example.entity.Account;
import lombok.Getter;

@Getter
public class AccountResponseDto {
    private Long id;
    private String accountNumber;
    private Long balance;
    private boolean frozen;

    public AccountResponseDto(Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
        this.frozen = account.isFrozen();
    }
}
