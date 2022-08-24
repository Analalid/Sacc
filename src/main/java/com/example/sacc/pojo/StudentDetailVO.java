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
    Integer total;
    Integer finished;
    String begin;
    String end;
    Integer total_correcting;
    Integer finished_correcting;
}
