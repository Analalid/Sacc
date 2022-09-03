package com.example.sacc.Entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Account implements Serializable {
    private static final long serialVersionUID = 532960928738689155L;
    @TableId(value = "uid", type = IdType.AUTO)
    Long uid;
    @ExcelProperty("学号")
    @TableField("stu_id")
    String stuId;
    @ExcelProperty("姓名")
    @TableField("user_name")
    String userName;
    String password;
    @ExcelProperty("权限")
    Integer role;
    @ExcelProperty("组别")
    String grouped;
    public Account(String stu_id, String user_name, String password, Integer role) {
        this.stuId = stu_id;
        this.userName = user_name;
        this.password = password;
        this.role = role;
        this.grouped="";
    }

    public Account(String stuId, String userName, Integer role, String grouped) {
        this.stuId = stuId;
        this.userName = userName;
        this.role = role;
        this.grouped = grouped;
    }
}
