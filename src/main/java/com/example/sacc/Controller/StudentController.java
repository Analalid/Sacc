package com.example.sacc.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.sacc.Entity.Account;
import com.example.sacc.Exception.LocalRuntimeException;
import com.example.sacc.Service.RedisService;
import com.example.sacc.Service.StudentService;
import com.example.sacc.pojo.AnswerObj;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    RedisService redisService;
    @GetMapping("/unitList")
    public Map<String, Object> uniList() {
        return studentService.getUnitList(0, 1000);
    }

    @GetMapping("/normal/questionList")
    public Map<String, Object> questionList(@RequestParam Integer unit) {
        return studentService.questionList(unit);
    }

    @PostMapping("/normal/answer")
    public String answer(@RequestBody AnswerObj answerObj, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        Account account = (Account) redisService.get(token);
        if (studentService.answer(answerObj,account.getUid())) {
            return "success";
        } else {
            throw new LocalRuntimeException("提交答案失败");
        }
    }
}
