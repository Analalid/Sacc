package com.example.sacc.Service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class AdminService {
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
}
