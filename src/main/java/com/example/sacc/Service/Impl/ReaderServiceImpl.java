package com.example.sacc.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sacc.Entity.Account;
import com.example.sacc.Entity.Answer;
import com.example.sacc.Entity.Judge;
import com.example.sacc.Entity.Problem;
import com.example.sacc.Mapper.AccountMapper;
import com.example.sacc.Mapper.AnswerMapper;
import com.example.sacc.Mapper.JudgeMapper;
import com.example.sacc.Mapper.ProblemMapper;
import com.example.sacc.Service.ReaderService;
import com.example.sacc.pojo.ScoreVO;
import com.example.sacc.pojo.StudentDetailVO;
import com.example.sacc.pojo.answerOP;
import com.example.sacc.pojo.choicesOP;
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

    @Override
    public Map<String, Object> getDetail(Integer unit) {
        Map<String, Object> result = new HashMap<>();
        //答题人总数
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("role", 0);
        result.put("totalAccount", accountMapper.selectCount(accountQueryWrapper));
        //未批改题目数量
        Integer answer_num = answerMapper.selectCount(null);
        Integer judge_num = judgeMapper.selectCount(null);
        result.put("totalQuestions", answer_num - judge_num);
        //取出这个比赛对应的所有的problem总数
        QueryWrapper<Problem> problemQueryWrapper = new QueryWrapper<>();
        problemQueryWrapper.eq("unit_id", unit);
        Integer problem_num = problemMapper.selectCount(problemQueryWrapper);
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
            Integer finished_num = answerMapper.selectCount(answerQueryWrapper);
            studentDetailVO.setFinished(finished_num);
            //开始的时间,可以直接从token里面拿,不过还是偷懒吧
            studentDetailVO.setBegin("2022年9月1日");
            //结束的时间,偷懒
            studentDetailVO.setEnd("2022年10月1日");
            //题目总数tips:和finished一样
            studentDetailVO.setTotal_correcting(finished_num);
            //已经被批改的题目数
            QueryWrapper<Judge> judgeQueryWrapper=new QueryWrapper<>();
            //答题人的uid对应的judge总数
            judgeQueryWrapper.eq("answer_id",uid);
            Integer judge_person_num = judgeMapper.selectCount(judgeQueryWrapper);
            studentDetailVO.setFinished_correcting(judge_person_num);
            studentDetailVOS.add(studentDetailVO);
        }
        result.put("userList",studentDetailVOS);
        return result;
    }

    @Override
    public Map<String, Object> getOne(String stuId) {
        QueryWrapper<Account> accountQueryWrapper=new QueryWrapper<>();
        accountQueryWrapper.eq("stu_id",stuId);
        //通过学号得到uid
        Long uid;
        Account account = accountMapper.selectOne(accountQueryWrapper);
        if (account==null){
            throw new RuntimeException("该学号没有对应的账户!");
        }
        uid=account.getUid();
        QueryWrapper<Answer> answerQueryWrapper=new QueryWrapper<>();
        answerQueryWrapper.eq("uid",uid);
        List<Answer> answersList = answerMapper.selectList(answerQueryWrapper);
        List<choicesOP> choicesOPS=new ArrayList<>();
        List<answerOP> answerOPS=new ArrayList<>();
        for (Answer answer : answersList) {
            Long problemId = answer.getProblemId();
            String content = answer.getContent();
            QueryWrapper<Problem> problemQueryWrapper=new QueryWrapper<>();
            problemQueryWrapper.eq("id",problemId);
            Problem problem = problemMapper.selectOne(problemQueryWrapper);
            Integer type = problem.getType();
            if(0==type){
                choicesOPS.add(new choicesOP(problem,content));
            }else{
                answerOPS.add(new answerOP(problem,content));
            }
        }
        Map<String,Object> result=new HashMap<>();
        result.put("choiceQuestions",choicesOPS);
        result.put("answerQuestions",answerOPS);
        return result;
    }

    @Override
    public String insertJudge(List<ScoreVO> scoreVOS) {
        for (ScoreVO scoreVO : scoreVOS) {
            judgeMapper.insert(new Judge(scoreVO));
        }
        return "打分数据提交成功!";
    }
}
