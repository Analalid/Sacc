package com.example.sacc.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sacc.Entity.Account;
import com.example.sacc.Entity.Answer;
import com.example.sacc.Entity.Problem;
import com.example.sacc.Entity.Unit;
import com.example.sacc.Exception.LocalRuntimeException;
import com.example.sacc.Mapper.AccountMapper;
import com.example.sacc.Mapper.AnswerMapper;
import com.example.sacc.Mapper.ProblemMapper;
import com.example.sacc.Mapper.UnitMapper;
import com.example.sacc.Service.StudentService;
import com.example.sacc.pojo.AnswerObj;
import com.example.sacc.pojo.ProblemAnswer;
import com.example.sacc.pojo.ProblemChoice;
import com.example.sacc.pojo.UnitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Autowired
    private UnitMapper unitMapper;
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    AccountMapper accountMapper;
    @Override
    public Map<String, Object> getUnitList(Integer pageNum, Integer pageSize) {
        Page<Unit> page = new Page<>(pageNum, pageSize);
        Page<Unit> unitPage = unitMapper.selectPage(page, null);
        List<Unit> records = page.getRecords();
        long total = page.getTotal();
        List<UnitVO> collect = records.stream().map((unit) -> {
            UnitVO contestVO = new UnitVO(unit);
            contestVO.setIsLate(false);
            return contestVO;
        }).collect(Collectors.toList());

        Map<String, Object> res = new HashMap<>();
        res.put("list", collect);
        return res;
    }

    @Override
    public Map<String, Object> questionList(Integer unit) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unit_id", unit);
        List<Problem> problems = problemMapper.selectList(queryWrapper);
        System.out.println(problems);
        List<ProblemChoice> problem_choices = new ArrayList<>();
        List<ProblemAnswer> problem_answers = new ArrayList<>();
        for (Problem problem : problems) {
            Integer type = problem.getType();
            if (type == 1) {
                problem_choices.add(new ProblemChoice(problem));
            } else {
                problem_answers.add(new ProblemAnswer(problem));
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("answer", problem_answers);
        map.put("choice", problem_choices);
        return map;
    }

    @Override
    public boolean answer(AnswerObj answerObj, Long uid) {
        List<AnswerObj.choiceList> choicesList = answerObj.getChoicesList();
        Integer unit = answerObj.getUnit();
        try {
            for (AnswerObj.choiceList choiceList : choicesList) {
                answerMapper.insert(new Answer(unit, choiceList,uid));
            }
            for (AnswerObj.answerList answerList : answerObj.getAnswerList()) {
                answerMapper.insert(new Answer(unit, answerList,uid));
            }
            return true;
        } catch (Exception e) {
            throw new LocalRuntimeException("插入失败!");
        }

    }



}
