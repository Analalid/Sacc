package com.example.sacc.enums;

public enum ErrorEnum {
    Login_Error(1000,"登录信息错误"),
    Register_Error(1001,"注册信息错误"),
    NO_NextStuId(2001,"没有下一份了"),
    NO_PROBLEM_ERROR(2002,"没有该回答对应的问题_mysql异常"),
    No_ACCOUNT_ERROR(2003,"mysql中没有对应的账号"),
    N0_SUCH_ACCOUNT(2004,"该学号没有对应的用户id"),
    Redis_NoAccount(3001,"redis中没有该token对应的用户");
    private Integer errCode;
    private String errMsg;
    ErrorEnum(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }
}
