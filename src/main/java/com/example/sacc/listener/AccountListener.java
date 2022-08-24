package com.example.sacc.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.example.sacc.Entity.Account;
import com.example.sacc.Service.AccountService;
import com.example.sacc.Service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AccountListener extends AnalysisEventListener<Account> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountListener.class);

    /**
     * 每1条数据存入数据库
     * @param account
     * @param analysisContext
     */
    private static final int BATCH_COUNT = 1;
    List<Account> list=new ArrayList<Account>(BATCH_COUNT);
    private AccountService accountService;

    public AccountListener(AccountService accountService){
        this.accountService=accountService;
    }



    @Override
    public void invoke(Account account, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(account));
        account.setPassword("elpsycongroo");
        list.add(account);
        if (list.size()>=BATCH_COUNT){
            SaveDate();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        LOGGER.info("所有数据解析完成！");
    }
    private void SaveDate(){
        LOGGER.info("{}条数据开始保存");
        accountService.insertList(list);
        LOGGER.info("数据存储成功");
    }
}
