package com.example.sacc.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sacc.Entity.Account;
import com.example.sacc.Service.ReaderService;
import com.example.sacc.Service.RedisService;
import com.example.sacc.pojo.ScoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReaderController {
    @Autowired
    ReaderService readerService;
    @Autowired
    RedisService redisService;
    @GetMapping("/reader/getDetail")
    public Map<String,Object> getDetail(@RequestParam Integer unit){
        return readerService.getDetail(unit);
    }
    @GetMapping("/reader/getOne")
    public Map<String,Object> getOne(@RequestParam String stuId,@RequestParam Integer unit){
        return readerService.getOne(stuId,unit);
    }
    @PostMapping("/reader/judge")
    public String judge(@RequestBody List<ScoreVO> scoreVOS, HttpServletRequest httpServletRequest){
        Long uid = ((Account)redisService.get(httpServletRequest.getHeader("token"))).getUid();
        return readerService.insertJudge(scoreVOS,uid);
    }
    @GetMapping("reader/getNextStuId")
    public String getUidByStuId(@RequestParam String stuId){
        QueryWrapper<Account> accountQueryWrapper=new QueryWrapper<>();
        accountQueryWrapper.eq("stu_id",stuId);
        return readerService.getNextStuId(stuId);
    }
}
