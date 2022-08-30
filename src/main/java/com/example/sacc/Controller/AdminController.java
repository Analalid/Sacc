package com.example.sacc.Controller;

import com.alibaba.excel.EasyExcelFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.sacc.Entity.Account;
import com.example.sacc.Entity.Unit;
import com.example.sacc.Exception.LocalRuntimeException;
import com.example.sacc.Mapper.AccountMapper;
import com.example.sacc.Mapper.UnitMapper;
import com.example.sacc.Service.AccountService;
import com.example.sacc.Service.Impl.AdminService;
import com.example.sacc.listener.AccountListener;
import com.example.sacc.util.saveExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {
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
        unitMapper.insert(unit);
        return "success";
    }
    @PostMapping("/root/importReader")
    public String pubggupload(@RequestBody MultipartFile fileList) {
        try {
            byte[] byteArr = fileList.getBytes();
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            saveExcel util = new saveExcel();
            util.save(inputStream, fileList.getName());
            EasyExcelFactory.read("D:\\tmpExcel\\" + fileList.getName() + ".xlsx", Account.class, new AccountListener(accountService)).sheet().doRead();
        }catch (Exception e){
            throw new LocalRuntimeException("导入excel失败!");
        }
        return "success";
    }
    @PostMapping("/root/uploadProblem")
    public String uploadProblems(@RequestParam Integer unit, @RequestParam String description, @RequestBody MultipartFile file){
        QueryWrapper<Unit> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",unit);
        Unit unit1=new Unit();
        unit1.setDescription(description);
        unitMapper.update(unit1,queryWrapper);
        String uploadFile = adminService.uploadFile(file);
        System.out.println(uploadFile);
        return "上传题目成功!";
    }
    @PostMapping("/root/updateReader")
    public String updateReader(@RequestParam String stuId){
        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("stu_id",stuId);
        Account account = accountMapper.selectOne(updateWrapper);
        if(account==null)throw new LocalRuntimeException("数据库中没有该用户!");
        account.setRole(2);
        accountMapper.update(account,updateWrapper);
        return "success";
    }
    @PostMapping("root/updateGroup")
    public String updateGroup(@RequestParam String stuId,@RequestParam String group){
        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("stu_id",stuId);
        Account account = accountMapper.selectOne(updateWrapper);
        if(account==null)throw new LocalRuntimeException("数据库中没有该用户!");
        account.setGrouped(group);
        accountMapper.update(account,updateWrapper);
        return "success";
    }
}
