package com.example.sacc.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Answer {
    @TableId(type = IdType.AUTO)
    Long id;
    Long unitId;
    Long problemId;
    Long uid;//答题人
    String content;


    public Answer(Integer unit, Integer id, Long uid, String data) {
        this.unitId=unit.longValue();
        this.problemId=id.longValue();
        this.uid=uid;
        this.content=data;
    }
}
