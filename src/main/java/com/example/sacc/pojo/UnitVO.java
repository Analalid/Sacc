package com.example.sacc.pojo;

import com.example.sacc.Entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitVO {
    private Long unit;
    private String title;
    private String content ;
    private Boolean isLate;
    private LocalDateTime start;
    private LocalDateTime end;

    public UnitVO(Unit unit) {
        this.unit=unit.getId();
        this.title=unit.getName();
        this.start=unit.getStart();
        this.end=unit.getEnd();
        this.content=unit.getDescription();
    }
}
