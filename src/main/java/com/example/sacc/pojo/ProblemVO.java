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
    String A;
    String B;
    String C;
    String D;
    String content;

    public ProblemVO(Problem problem) {
        this.id=problem.getId();
        this.type=problem.getType();
        this.title=problem.getTitle();
        this.A=problem.getA();
        this.B=problem.getB();
        this.C=problem.getC();
        this.D=problem.getD();
    }
}
