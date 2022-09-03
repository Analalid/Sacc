package com.example.sacc.pojo;

import com.example.sacc.Entity.Problem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProblemVO {
    Long id;
    Integer type;
    String title;

    @JsonProperty("A")
    String A;
    @JsonProperty("B")
    String B;
    @JsonProperty("C")
    String C;
    @JsonProperty("D")
    String D;
    String content;
    Integer value;
    String imgUrl;
    public void setContent(String content) {
        this.content = content;
    }
    public ProblemVO(Problem problem) {
        this.id = problem.getId();
        this.type = problem.getType();
        this.title = problem.getTitle();
        this.A = problem.getOpt1();
        this.B = problem.getOpt2();
        this.C = problem.getOpt3();
        this.D = problem.getOpt4();
        this.imgUrl=problem.getImgUrl();
    }
}
