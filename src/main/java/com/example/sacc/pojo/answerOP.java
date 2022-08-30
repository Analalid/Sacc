package com.example.sacc.pojo;

import com.example.sacc.Entity.Problem;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class answerOP {
    Long id;
    String content;
    String data;

    public answerOP(Problem problem, String content) {
        this.content=problem.getTitle();
        this.data=content;
    }
}
