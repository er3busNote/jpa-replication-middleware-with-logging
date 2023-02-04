package com.opensw.master.domain.account.application;

import com.opensw.master.domain.account.dao.AccountRepository;
import com.opensw.master.domain.account.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account saveAccount(Account account) {
        return this.accountRepository.save(account);
    }
}
