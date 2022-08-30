package com.example.sacc.pojo;

import com.example.sacc.Entity.Problem;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class choicesOP {
    Long id;
    String fir;
    String sec;
    String thi;
    String fou;
    String data;

    public choicesOP(Problem problem,String content) {
        this.id=problem.getId();
        this.fir=problem.getA();
        this.sec=problem.getB();
        this.thi=problem.getC();
        this.fou=problem.getD();
        this.data=content;
    }
}
