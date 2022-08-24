package com.example.sacc.pojo;

import com.example.sacc.Entity.Problem;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProblemAnswer {
    Integer id;
    Integer kind;
    String content;

    public ProblemAnswer(Problem problem) {
        this.id=problem.getOrderId();
        //是简答题
        if(2==problem.getType()){
            this.kind=0;
        }else{
            this.kind=1;
        };
        this.content=problem.getDescription();
    }
}
