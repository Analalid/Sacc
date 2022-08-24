package com.example.sacc;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONArray;
import com.example.sacc.pojo.ReaderVO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;



public class main {
    public static void main(String[] args) throws FileNotFoundException {
        //同步读取文件内容
        FileInputStream inputStream = new FileInputStream(new File("D:/王书彬.xls"));
        List<ReaderVO> list = EasyExcel.read(inputStream).head(ReaderVO.class).sheet().doReadSync();
        System.out.println(JSONArray.toJSONString(list));
    }
}