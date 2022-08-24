package com.example.sacc.Mapper;

import java.io.Serializable;

public class Result implements Serializable {
    public int status;
    public String message;
    public Object data;

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public Result(Object data) {
        this.status = 200;
        this.message = "success";
        this.data = data;
    }
}
