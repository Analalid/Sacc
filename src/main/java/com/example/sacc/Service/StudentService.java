package com.example.sacc.Service;

import com.example.sacc.Entity.Account;
import com.example.sacc.pojo.AnswerObj;

import java.util.List;
import java.util.Map;

public interface StudentService {
    public Map<String, Object> getUnitList(Integer pageNum, Integer pageSize);

    public Map<String, Object> questionList(Integer unit);

    public boolean answer(AnswerObj answerObj, Long uid);

}
