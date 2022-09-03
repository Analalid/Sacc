package com.example.sacc.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sacc.Entity.Account;
import com.example.sacc.Entity.Unit;
import com.example.sacc.Exception.LocalRuntimeException;
import com.example.sacc.Mapper.UnitMapper;
import com.example.sacc.Service.RedisService;
import com.example.sacc.Service.StudentService;
import com.example.sacc.pojo.answerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    RedisService redisService;
    @Autowired
    UnitMapper unitMapper;
    @GetMapping("/unitList")
    public Map<String, Object> uniList() {
        return studentService.getUnitList(0, 1000);
    }

    @GetMapping("/normal/questionList")
    public Map<String, Object> questionList(@RequestParam Integer unit, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        Account account = (Account) redisService.get(token);
        Long uid = account.getUid();
        Map<String, Object> res = new HashMap<>();
        QueryWrapper<Unit> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",unit);
        Unit unit1 = unitMapper.selectOne(queryWrapper);
        res.put("title",unit1.getName());
        res.put("description",unit1.getDescription());
        res.put("listObj", studentService.questionList(unit, uid));
        System.out.println(account);
        return res;
    }

    @PostMapping("/normal/answer")
    public String answer(@RequestParam Integer unit,@RequestBody List<answerVO> ans, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        Account account = (Account) redisService.get(token);
        if (studentService.answer(ans,account.getUid(),unit)) {
            return "success";
        } else {
            throw new LocalRuntimeException("提交答案失败");
        }
    }
}