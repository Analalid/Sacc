package com.example.sacc.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.sacc.Entity.Problem;
import com.example.sacc.pojo.ProblemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProblemMapper extends BaseMapper<Problem> {
    @Select("select problem.id,problem.type,problem.title,problem.opt1,problem.opt2,problem.opt3,problem.opt4,answer.content " +
            "FROM problem left outer join answer " +
            "ON problem.id=answer.problem_id AND answer.uid=#{uid} " +
            "WHERE problem.unit_id=#{unit_id}")
    List<ProblemVO> getProblemVO(@Param("uid")Integer uid, @Param("unit_id")Long unit_id);
}