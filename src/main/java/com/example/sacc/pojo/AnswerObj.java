package com.example.sacc.pojo;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnswerObj {
    Integer unit;
    List<choiceList> choicesList;
    List<answerList> answerList;

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Data
    public static class choiceList {
        Integer id;
        boolean hasA;
        boolean hasB;
        boolean hasC;
        boolean hasD;

        public String mkContent() {
            String ans="";
            if(this.hasA) ans=ans+"A";
            if(this.hasB) ans=ans+"B";
            if(this.hasC) ans=ans+"C";
            if(this.hasD) ans=ans+"D";
            return ans;
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class answerList {
        Integer id;
        String data;
    }
}
