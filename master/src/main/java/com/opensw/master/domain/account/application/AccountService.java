package com.opensw.master.domain.account.application;

import com.opensw.master.domain.account.dao.AccountRepository;
import com.opensw.master.domain.account.domain.Account;
import com.opensw.master.domain.account.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Optional<Account> getAccount(Integer id) {
        return this.accountRepository.findById(id);
    }

    public Optional<Account> getLastId() { return this.accountRepository.findFirstByOrderByIdDesc(); }

    public List<Account> getLastIds(Integer id) { return this.accountRepository.findByIdGreaterThanEqual(id); }

    public Long getCount() { return this.accountRepository.countBy(); }

    public Account saveAccount(AccountDto accountDto) {
        return this.accountRepository.save(accountDto.toEntity());
    }

    public void saveReplica(Account account) {
        this.accountRepository.insertAccount(account.getId(), account.getUsername(), account.getEmail(), account.getPhone(), account.getCreatedAt(), account.getUpdatedAt());
    };
}
