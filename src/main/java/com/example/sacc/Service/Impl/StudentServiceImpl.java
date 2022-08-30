package com.example.sacc.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.example.sacc.pojo.ProblemVO;
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
    public List<ProblemVO> questionList(Integer unit, Long uid) {
        QueryWrapper<Problem> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("unit_id",unit);
        List<Problem> problems = problemMapper.selectList(queryWrapper);
        List<ProblemVO> problemVOS=new ArrayList<>();
        for (Problem problem : problems) {
            ProblemVO problemVO=new ProblemVO(problem);
            QueryWrapper<Answer> queryWrapper1=new QueryWrapper<>();
            queryWrapper1.eq("uid",uid);
            queryWrapper1.eq("problem_id",problem.getId());
            Answer answer = answerMapper.selectOne(queryWrapper1);
            if (answer!=null){
                problemVO.setContent(answer.getContent());
            }
            problemVOS.add(problemVO);
        }
//        List problemVO = problemMapper.getProblemVO(unit, uid);
//        log.info(problemVO.toString());
        return problemVOS;
    }

    @Override
    public boolean answer(AnswerObj answerObj, Long uid) {
        List<AnswerObj.choiceList> choicesList = answerObj.getChoicesList();
        Integer unit = answerObj.getUnit();
        try {
            for (AnswerObj.choiceList choiceList : choicesList) {
                UpdateWrapper<Answer> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("problem_id", choiceList.getId());
                updateWrapper.eq("uid", uid);
                if (answerMapper.exists(updateWrapper)) {
                    answerMapper.update(new Answer(unit, choiceList, uid), updateWrapper);

                } else
                    answerMapper.insert(new Answer(unit, choiceList, uid));
            }
            for (AnswerObj.answerList answerList : answerObj.getAnswerList()) {
                UpdateWrapper<Answer> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("problem_id", answerList.getId());
                updateWrapper.eq("uid", uid);
                if (answerMapper.exists(updateWrapper)) {
                    answerMapper.update(new Answer(unit, answerList, uid), updateWrapper);
                } else
                    answerMapper.insert(new Answer(unit, answerList, uid));
            }
            return true;
        } catch (Exception e) {
            throw new LocalRuntimeException("插入失败!");
        }

    }


}
