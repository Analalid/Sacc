package com.example.sacc;

import com.alibaba.excel.EasyExcel;
import com.example.sacc.Entity.Account;
import com.example.sacc.Mapper.AccountMapper;
import com.example.sacc.Mapper.ProblemMapper;
import com.example.sacc.Mapper.UnitMapper;
import com.example.sacc.Service.RedisService;
import com.example.sacc.Service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class MyBatisPlusTest {
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    UnitMapper unitMapper;
    @Autowired
    RedisService redisService;
    @Autowired
    StudentService studentService;
    @Autowired
    ProblemMapper problemMapper;
    @Test
    public void problem(){
        System.out.println(problemMapper.selectList(null));
    }
    @Test
    public void unitTest(){
        System.out.println(unitMapper.selectList(null));
    }
    @Test
    public void redistest() {
        redisService.set("key", new Account("1", "1", "1", 0), 30, TimeUnit.DAYS);

    }

    @Test
    public void test() {
        accountMapper.insert(new Account("1", "1", "1", 0));

    }

    @Test
    public void tes() {
        List<Account> list = new ArrayList<>();
        list = accountMapper.selectList(null);
        for (Account account : list) {
            System.out.println(account.getStuId());
        }
//        System.out.println(accountMapper.selectList(null));
    }
}
