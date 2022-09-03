package com.example.sacc.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class ReaderOP {
    //学号
    String stu_id;
    //批改情况
    String judgeInfo;
    //分组
    String group;
}
