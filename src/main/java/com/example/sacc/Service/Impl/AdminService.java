package com.example.sacc.Service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sacc.Entity.Account;
import com.example.sacc.Entity.Judge;
import com.example.sacc.Entity.Unit;
import com.example.sacc.Mapper.*;
import com.example.sacc.pojo.ReaderOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminService {
    @Autowired
    UnitMapper unitMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    JudgeMapper judgeMapper;
    @Autowired
    AnswerMapper answerMapper;
    public String uploadFile(MultipartFile multipartFile) {
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tLowkWdhyASZeURHTR6";
        String accessKeySecret = "9kBWfI8jRn1MbNSrvRNRpGQ2BNlXAJ";
        String bucketName = "sacc-blog";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String datePath = dateFormat.format(new Date());
            InputStream inputStream = multipartFile.getInputStream();
            String originName = multipartFile.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String suffix = originName.substring(originName.lastIndexOf("."));
            String newName = fileName + suffix;
            String fileUrl = datePath + newName;
            ossClient.putObject(bucketName, fileUrl, inputStream);
            String url = "http://" + bucketName+"."+ endpoint + "/" + fileUrl;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public Map<String, Object> getReader(Long unitId) {
        Map<String,Object> map=new HashMap<>();
        Map<String,Object> que2=new HashMap<>();
        List<ReaderOP> readerOPS=new ArrayList<>();
        QueryWrapper<Unit> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",unitId);
        QueryWrapper<Account> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("role",2);
        map.put("title",unitMapper.selectOne(queryWrapper));
        List<Account> accounts = accountMapper.selectList(queryWrapper1);
        Long totalJudge=0L;
        for (Account account : accounts) {
            QueryWrapper<Judge> queryWrapper2=new QueryWrapper<>();
            queryWrapper2.eq("judger_id",account.getUid());
            Long OneJudge = judgeMapper.selectCount(queryWrapper2);
            totalJudge+=OneJudge;
            ReaderOP readerOP=new ReaderOP();
            readerOP.setStu_id(account.getStuId());
            readerOP.setJudgeInfo(OneJudge.toString());
            readerOP.setGroup(account.getGrouped());
            readerOPS.add(readerOP);
        }
        QueryWrapper<Account> queryWrapper2=new QueryWrapper<>();
        queryWrapper2.eq("role",1);
        que2.put("totalNum",accountMapper.selectCount(queryWrapper2));
        que2.put("ReaderNum",accounts.size());
        que2.put("judgeMessage",totalJudge.toString()+"/"+answerMapper.selectCount(null));
        map.put("readerInfo",readerOPS);
        map.put("rightInfo",que2);
        return map;
    }
}
