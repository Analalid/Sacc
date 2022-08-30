package com.example.sacc.Entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Account implements Serializable {
    private static final long serialVersionUID = 532960928738689155L;
    @TableId(value = "uid", type = IdType.AUTO)
    Long uid;
    @ExcelProperty("学号")
    @TableField("stu_id")
    String stuId;
    @TableField("user_name")
    String userName;
    String password;
    @ExcelProperty("权限")
    Integer role;
    String grouped;
    public Account(String stu_id, String user_name, String password, Integer role) {
        this.stuId = stu_id;
        this.userName = user_name;
        this.password = password;
        this.role = role;
        this.grouped="";
    }
}
