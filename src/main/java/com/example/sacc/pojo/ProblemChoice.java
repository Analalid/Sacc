package com.example.sacc.pojo;

import com.example.sacc.Entity.Problem;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProblemChoice {
    Integer id;
    String fir;
    String sec;
    String thi;
    String fou;

    public ProblemChoice(Problem problem) {
        this.id=problem.getOrderId();
        this.fir=problem.getOpt1();
        this.sec=problem.getOpt2();
        this.thi=problem.getOpt3();
        this.fou=problem.getOpt4();
    }
}
