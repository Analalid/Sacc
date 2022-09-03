package com.example.sacc.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sacc.Entity.*;
import com.example.sacc.Exception.LocalRuntimeException;
import com.example.sacc.Mapper.*;
import com.example.sacc.Service.ReaderService;
import com.example.sacc.enums.ErrorEnum;
import com.example.sacc.pojo.ProblemVO;
import com.example.sacc.pojo.ScoreVO;
import com.example.sacc.pojo.StudentDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReaderServiceImpl implements ReaderService {
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    ProblemMapper problemMapper;
    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    JudgeMapper judgeMapper;
    @Autowired
    UnitMapper unitMapper;

    @Override
    public Map<String, Object> getDetail(Integer unit) {
        Map<String, Object> result = new HashMap<>();
        //答题人总数
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("role", 0);
        result.put("totalAccount", accountMapper.selectCount(accountQueryWrapper));
        //未批改题目数量
        Long answer_num = answerMapper.selectCount(null);
        Long judge_num = judgeMapper.selectCount(null);
        result.put("totalQuestions", answer_num - judge_num);
        //取出这个比赛对应的所有的problem总数
        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        problemQueryWrapper.eq("unit_id", unit);
        Long problem_num = problemMapper.selectCount(problemQueryWrapper);
        //userList数据集合
        List<StudentDetailVO> studentDetailVOS = new ArrayList<>();
        //得到答题人list
        List<Account> accounts = accountMapper.selectList(accountQueryWrapper);
        System.out.println(accounts);
        for (Account account : accounts) {
            //答题人的uid
            Long uid = account.getUid();
            System.out.println(uid);
            //答题人的学号stu_id
            String stu_id = account.getStuId();
            //该uid对应的回答
            QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
            answerQueryWrapper.eq("uid", uid);
            StudentDetailVO studentDetailVO = new StudentDetailVO();
            //答题人学号
            studentDetailVO.setStuId(stu_id);
            //总题数
            studentDetailVO.setTotal(problem_num);
            //已经完成的题数
            Long finished_num = answerMapper.selectCount(answerQueryWrapper);
            studentDetailVO.setFinished(finished_num);
            //开始的时间,可以直接从token里面拿,不过还是偷懒吧
            studentDetailVO.setBegin("2022年9月1日");
            //结束的时间,偷懒
            studentDetailVO.setEnd("2022年10月1日");
            //题目总数tips:和finished一样
            studentDetailVO.setTotal_correcting(finished_num);
            //已经被批改的题目数
            QueryWrapper<Judge> judgeQueryWrapper = new QueryWrapper<>();
            //答题人的uid对应的judge总数
            judgeQueryWrapper.eq("answer_id", uid);
            Long judge_person_num = judgeMapper.selectCount(judgeQueryWrapper);
            studentDetailVO.setFinished_correcting(judge_person_num);
            studentDetailVOS.add(studentDetailVO);
        }
        result.put("userList", studentDetailVOS);
        return result;
    }

    @Override
    public String getNextStuId(String stuId) {
        //初步检测是否有该学好对应的用户
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("stu_id", stuId);
        Account account = accountMapper.selectOne(accountQueryWrapper);
        if (account == null) throw new LocalRuntimeException(ErrorEnum.N0_SUCH_ACCOUNT);
        //得到uid
        Long uid = account.getUid();
        //初始化返回的用户
        Account account1=new Account();
        //遍历直至得到需要的用户
        do {
        QueryWrapper<Account> accountQueryWrapper1 = new QueryWrapper<>();
        accountQueryWrapper1.eq("uid", ++uid);
        account1 = accountMapper.selectOne(accountQueryWrapper1);
        if(account1==null)break;
        }while(account1.getRole()!=1);
        if (account1 == null) throw new LocalRuntimeException(ErrorEnum.NO_NextStuId);
        return account1.getStuId();
    }

    @Override
    public Map<String, Object> getOne(String stuId, Integer unit) {
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("stu_id", stuId);
        //通过学号得到uid
        Long uid;
        Account account = accountMapper.selectOne(accountQueryWrapper);
        if (account == null) {
            throw new LocalRuntimeException(ErrorEnum.No_ACCOUNT_ERROR);
        }
        uid = account.getUid();
        QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
        answerQueryWrapper.eq("uid", uid);
        List<Answer> answers = answerMapper.selectList(answerQueryWrapper);
        List<ProblemVO> problemVOS = new ArrayList<>();
        for (Answer answer : answers) {
            Long problemId = answer.getProblemId();
            QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", problemId);
            Problem problem = problemMapper.selectOne(queryWrapper);
            if (problem == null) throw new LocalRuntimeException(ErrorEnum.NO_PROBLEM_ERROR);
            //得到title,score,imgUrl
            String title = problem.getTitle();
            Integer score = problem.getScore();
            String imgUrl=problem.getImgUrl();
            problem.setTitle("(" + score + "分)" + title);
            ProblemVO problemVO = new ProblemVO(problem);
            problemVO.setContent(answer.getContent());
            QueryWrapper<Judge> judgeQueryWrapper = new QueryWrapper<>();
            judgeQueryWrapper.eq("answer_id", answer.getId());
            Judge judge = judgeMapper.selectOne(judgeQueryWrapper);
            problemVO.setValue(judge == null ? 0 : judge.getScore());
            problemVO.setImgUrl(problem.getImgUrl());
            problemVOS.add(problemVO);
        }
        Map<String, Object> map = new HashMap();
        QueryWrapper<Unit> unitQueryWrapper = new QueryWrapper<>();
        unitQueryWrapper.eq("id", unit);
        Unit unit1 = unitMapper.selectOne(unitQueryWrapper);
        map.put("title", unit1.getName());
        map.put("content", unit1.getDescription());
        map.put("DataOfOne", problemVOS);
        return map;
    }

    @Override
    public String insertJudge(List<ScoreVO> scoreVOS, Long uid) {

        for (ScoreVO scoreVO : scoreVOS) {
            QueryWrapper<Judge> judgeQueryWrapper = new QueryWrapper<>();
            judgeQueryWrapper.eq("answer_id", scoreVO.getId());
            Judge judge = judgeMapper.selectOne(judgeQueryWrapper);
            if (judge == null) {
                judgeMapper.insert(new Judge(scoreVO, uid));
            } else {
                judgeMapper.update(new Judge(scoreVO, uid), judgeQueryWrapper);
            }
        }
        return "打分数据提交成功!";
    }
}
