package com.example.sacc.pojo;

import com.example.sacc.Entity.Problem;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ProblemVO {
    Long id;
    Integer type;
    String title;
    String opt1;
    String opt2;
    String opt3;
    String opt4;
    String content;

    public ProblemVO(Problem problem) {
        this.id=problem.getId();
        this.type=problem.getType();
        this.title=problem.getTitle();
        this.opt1=problem.getA();
        this.opt2=problem.getB();
        this.opt3=problem.getC();
        this.opt4=problem.getD();
    }
}
