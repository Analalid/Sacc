package com.example.sacc.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.sacc.pojo.ScoreVO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Judge {
    @TableId(type = IdType.AUTO)
    Long id;
    //answer表对应的ID
    Long answerId;
    //    阅卷人ID
    Long jugerId;
    Integer score;

    public Judge(ScoreVO scoreVO) {
        this.answerId=scoreVO.getId();
        this.score=scoreVO.getScore();
    }
}
