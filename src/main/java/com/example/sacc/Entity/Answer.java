package com.example.sacc.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.sacc.pojo.AnswerObj;
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

    public Answer(Integer unit, AnswerObj.choiceList choiceList,Long uid) {
        this.unitId=unit.longValue();
        this.problemId=choiceList.getId().longValue();
        this.uid=uid;
        this.content=choiceList.mkContent();
    }

    public Answer(Integer unit,AnswerObj.answerList answerList,Long uid) {
        this.unitId=unit.longValue();
        this.problemId=answerList.getId().longValue();
        this.uid=uid;
        this.content=answerList.getData();
    }
}
