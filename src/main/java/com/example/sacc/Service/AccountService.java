package com.example.sacc.Service;

import com.example.sacc.Entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;
public interface AccountService {
    void insertList(List<Account> list);
}
