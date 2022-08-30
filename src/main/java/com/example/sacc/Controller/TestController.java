package com.example.sacc.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {
    //测试跨域
    @PostMapping("test1")
    public String test1(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        return token;
    }
}
