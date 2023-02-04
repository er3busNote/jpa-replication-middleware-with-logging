package com.opensw.master.account;

import com.opensw.master.domain.account.application.AccountService;
import com.opensw.master.domain.account.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

@ExtendWith(SpringExtension.class)  // 참고 : https://prayme.tistory.com/17 (RunWith -> ExtendWith)
@SpringBootTest
public class AccountServiceTest {
    
    @Autowired
    AccountService accountService;
    
    @Test
    @DisplayName("주소록 저장 여부 확인")
    public void saveAccount() {
        // Given (준비)
        String username = "byoungJu";
        String email = "byoungJu@email.com";
        String phone = "010-5145-9132";
        Account account = Account.builder()
                .username(username)
                .email(email)
                .phone(phone)
                .build();
        this.accountService.saveAccount(account);
    }
}
