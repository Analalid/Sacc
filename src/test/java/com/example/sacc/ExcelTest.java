package com.example.sacc;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONArray;
import com.example.sacc.pojo.ReaderVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ExcelTest {
    @Test
    public void test() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(new File("D:/导入阅卷人.xlsx"));
        List<ReaderVO> list = EasyExcel.read(inputStream).head(ReaderVO.class).sheet().doReadSync();
        System.out.println(JSONArray.toJSONString(list));
    }
}