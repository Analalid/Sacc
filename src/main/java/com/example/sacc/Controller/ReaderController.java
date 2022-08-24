package com.example.sacc.Controller;

import com.example.sacc.Service.ReaderService;
import com.example.sacc.pojo.ScoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ReaderController {
    @Autowired
    ReaderService readerService;
    @GetMapping("/reader/getDetail")
    public Map<String,Object> getDetail(@RequestParam Integer unit){
        return readerService.getDetail(unit);
    }
    @GetMapping("/reader/getOne")
    public Map<String,Object> getOne(@RequestParam String stuId){
        return readerService.getOne(stuId);
    }
    @PostMapping("/reader/judge")
    public String judge(@RequestBody List<ScoreVO> scoreVOS){
        return readerService.insertJudge(scoreVOS);
    }
}
