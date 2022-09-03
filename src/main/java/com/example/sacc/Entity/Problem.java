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
    String opt1;
    String opt2;
    String opt3;
    String opt4;
    Integer score;
    @TableField("order_id")
    Integer orderId;
    @TableField("img_url")
    String imgUrl;

    public Problem(Long unit, Long ownerUid, Integer type,String title,String a, String b, String c, String d,String imgUrls) {
        this.unitId=unit;
        this.owner_uid=ownerUid;
        this.type=type;
        this.title=title;
        this.opt1=a;
        this.opt2=b;
        this.opt3=c;
        this.opt4=d;
        this.imgUrl=imgUrls;
    }
}
