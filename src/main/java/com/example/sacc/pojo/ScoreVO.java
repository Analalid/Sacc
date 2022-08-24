package com.example.sacc.pojo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreVO {
    Long id;
    Integer score;
}
