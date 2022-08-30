package com.example.sacc.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Problem implements Serializable {
    private static final long serialVersionUID = 532960851268689155L;
    @TableId(value = "id",type = IdType.AUTO)
    Long id;
    @TableField("problem_name")
    String problemName;
    @TableField("unit_id")
    Long unitId;
    @TableField("owner_uid")
    Long owner_uid;
    Integer type;
    String answer;
    String title;
    String A;
    String B;
    String C;
    String D;
    Integer score;
    @TableField("order_id")
    Integer orderId;
}
