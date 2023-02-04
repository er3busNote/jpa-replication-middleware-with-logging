package com.opensw.master.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    @NotNull(message = "사용자명이 존재하지 않습니다")
    private String username;

    @NotBlank(message = "이메일 주소가 존재하지 않습니다")
    private String email;

    @NotBlank(message = "핸드폰 번호가 존재하지 않습니다")
    private String phone;
}
