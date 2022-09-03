package com.example.sacc.Service;

import com.example.sacc.pojo.ProblemVO;
import com.example.sacc.pojo.answerVO;

import java.util.List;
import java.util.Map;

public interface StudentService {
    public Map<String, Object> getUnitList(Integer pageNum, Integer pageSize);

    public List<ProblemVO> questionList(Integer unit, Long uid);

    public boolean answer(List<answerVO> answerVOS, Long uid, Integer unit);

}
