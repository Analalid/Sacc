package com.example.sacc.Service;

import com.example.sacc.pojo.ScoreVO;

import java.util.List;
import java.util.Map;

public interface ReaderService {
    Map<String, Object> getDetail(Integer unit);
    Map<String, Object> getOne(String stuId);

    String insertJudge(List<ScoreVO> scoreVOS);
}
