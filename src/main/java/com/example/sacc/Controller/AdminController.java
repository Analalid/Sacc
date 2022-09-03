package com.example.sacc.Controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.sacc.Entity.Account;
import com.example.sacc.Entity.Problem;
import com.example.sacc.Entity.Unit;
import com.example.sacc.Exception.LocalRuntimeException;
import com.example.sacc.Mapper.AccountMapper;
import com.example.sacc.Mapper.ProblemMapper;
import com.example.sacc.Mapper.ProblemurlMapper;
import com.example.sacc.Mapper.UnitMapper;
import com.example.sacc.Service.AccountService;
import com.example.sacc.Service.Impl.AdminService;
import com.example.sacc.Service.RedisService;
import com.example.sacc.listener.AccountListener;
import com.example.sacc.util.saveExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {
    @Autowired
    ProblemurlMapper problemurlMapper;
    @Autowired
    RedisService redisService;
    @Autowired
    ProblemMapper problemMapper;
    @Autowired
    UnitMapper unitMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    AdminService adminService;
    @Autowired
    AccountMapper accountMapper;

    @PostMapping("/root/newUnit")
    public String createUnit(@RequestBody Unit unit) {
        try {
            unitMapper.insert(unit);
        } catch (Exception e) {
            throw new LocalRuntimeException("插入失败!");
        }
        return "success";
    }

    @PostMapping("/root/importReader")
    public String pubggupload(@RequestBody MultipartFile fileList) {
        try {
            byte[] byteArr = fileList.getBytes();
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            saveExcel util = new saveExcel();
            String fileName = "tmpExcel/" + fileList.getName();
            File file = new File(fileName);
            if (file.exists() && file.isFile()) file.delete();
            util.save(inputStream, fileList.getName());
            EasyExcelFactory.read("tmpExcel/" + fileList.getName() + ".xlsx", Account.class, new AccountListener(accountService)).sheet().doRead();
        } catch (Exception e) {
            throw new LocalRuntimeException("导入excel失败!");
        }
        return "success";
    }
//    已废弃
//    @PostMapping("/root/uploadProblem")
//    public String uploadProblems(@RequestParam Integer unit, @RequestParam String description, @RequestBody MultipartFile file) {
//        QueryWrapper<Unit> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("id", unit);
//        Unit unit1 = new Unit();
//        unit1.setDescription(description);
//        unitMapper.update(unit1, queryWrapper);
//        String uploadFile = adminService.uploadFile(file);
//        return "上传题目成功!";
//    }

    @PostMapping("/root/updateReader")
    public String updateReader(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        String stuId = jsonObject.getString("stuId");
        System.out.println(stuId);
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("stu_id", stuId);
        Account account = accountMapper.selectOne(updateWrapper);
        if (account == null) throw new LocalRuntimeException("数据库中没有该用户!");
        account.setRole(2);
        accountMapper.update(account, updateWrapper);
        return "更新成功";
    }

    @PostMapping("root/updateGroup")
    public String updateGroup(@RequestParam String stuId, @RequestParam String group) {
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("stu_id", stuId);
        Account account = accountMapper.selectOne(updateWrapper);
        if (account == null) throw new LocalRuntimeException("数据库中没有该用户!");
        account.setGrouped(group);
        accountMapper.update(account, updateWrapper);
        return "更新成功";
    }

    @GetMapping("root/getReader")
    public Map<String, Object> getReader(@RequestParam Long unit) {
        return adminService.getReader(unit);
    }

    @PostMapping("root/uploadProblem")
    public String upLoadProblem(HttpServletRequest httpServletRequest,
                                @RequestParam Long unit,
                                @RequestParam Integer type,
                                @RequestParam String title,
                                @RequestParam String A,
                                @RequestParam String B,
                                @RequestParam String C,
                                @RequestParam String D,
                                @RequestParam MultipartFile[] imgLists) {
        String token = httpServletRequest.getHeader("token");
        Account account = (Account)redisService.get(token);
        Long ownerUid = account.getUid();
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < imgLists.length; i++) {
            String uploadFile = adminService.uploadFile(imgLists[i]);
            if(i!=imgLists.length-1){
                sb.append(uploadFile+"|");
            }else {
                sb.append(uploadFile);
            }
        }
        problemMapper.insert(new Problem(unit,ownerUid,type,title,A,B,C,D, sb.toString()));
        return "上传题目成功";
    }
}
