package com.example.sacc.Controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sacc.Entity.Account;
import com.example.sacc.Entity.Unit;
import com.example.sacc.Exception.LocalRuntimeException;
import com.example.sacc.Mapper.UnitMapper;
import com.example.sacc.Service.AccountService;
import com.example.sacc.Service.Impl.AdminService;
import com.example.sacc.listener.AccountListener;
import com.example.sacc.util.saveExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

@RestController
public class AdminController {
    @Autowired
    UnitMapper unitMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    AdminService adminService;
    @PostMapping("root/newUnit")
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
}
