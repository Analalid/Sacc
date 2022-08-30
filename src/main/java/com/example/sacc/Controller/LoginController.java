package com.example.sacc.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sacc.Entity.Account;
import com.example.sacc.Exception.LocalRuntimeException;
import com.example.sacc.Mapper.AccountMapper;
import com.example.sacc.Service.RedisService;
import com.example.sacc.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Account account) {
        //登录处理
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_id", account.getStuId());
        Account accountFromDB = accountMapper.selectOne(queryWrapper);
        if (accountFromDB == null) {
            throw new LocalRuntimeException("账号不存在");
        } else if (!((account.getPassword()).equals(accountFromDB.getPassword()))) {
            throw new LocalRuntimeException("密码错误");
        }
        String token = jwtUtil.createToken(accountFromDB);
        Map<String, Object> map = new HashMap<>();
        map.put("stu_id", accountFromDB.getStuId());
        map.put("user_name", accountFromDB.getUserName());
        map.put("role", accountFromDB.getRole().toString());
        map.put("token", token);

        log.info("===============================================");
        log.info("用户登录：{}，role：{}", accountFromDB.getStuId(), accountFromDB.getRole());
        log.info("===============================================");

        //用redis中的过期时间代替JWT的过期时间，每次经过拦截器时更新过期时间
        //先设置为30天
        redisService.set(token, accountFromDB, 30, TimeUnit.DAYS);
        return map;
    }
    @PostMapping("register")
    public String register(@RequestBody Account account){
        QueryWrapper<Account> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("stu_id",account.getStuId());
        Account one=accountMapper.selectOne(queryWrapper);
        if (one!=null){
            throw new LocalRuntimeException("该学号已被注册");
        }
        Account account1=new Account(account.getStuId(),account.getUserName(),account.getPassword(),0);
        accountMapper.insert(account1);
        return "success";
    }
}
