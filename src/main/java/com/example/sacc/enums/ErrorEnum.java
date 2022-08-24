package com.example.sacc.enums;

public enum ErrorEnum {
    Login_Error(1000,"登录信息错误"),
    Register_Error(1001,"注册信息错误");
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
