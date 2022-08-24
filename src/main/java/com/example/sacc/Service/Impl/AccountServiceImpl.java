package com.example.sacc.Service.Impl;

import com.example.sacc.Entity.Account;
import com.example.sacc.Mapper.AccountMapper;
import com.example.sacc.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountMapper accountMapper;
    @Override
    public void insertList(List<Account> list) {
        for (Account account : list) {
            accountMapper.insert(account);
        }
    }
}
