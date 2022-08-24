package com.example.sacc.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ReaderVO {
    @ExcelProperty(value = "学号")
    private String stuId;
    @ExcelProperty(value = "权限")
    private Integer role;
}
