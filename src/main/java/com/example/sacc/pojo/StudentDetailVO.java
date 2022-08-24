package com.example.sacc.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StudentDetailVO {
    /**
     * 答题人的学号
     */
    String stuId;
    Long total;
    Long finished;
    String begin;
    String end;
    Long total_correcting;
    Long finished_correcting;
}
