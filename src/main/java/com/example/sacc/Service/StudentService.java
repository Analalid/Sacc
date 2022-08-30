package com.example.sacc.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.sacc.pojo.AnswerObj;
import com.example.sacc.pojo.ProblemVO;

import java.util.List;
import java.util.Map;

public interface StudentService {
    public Map<String, Object> getUnitList(Integer pageNum, Integer pageSize);

    public List<ProblemVO> questionList(Integer unit, Long uid);

    public boolean answer(AnswerObj answerObj, Long uid);

}
