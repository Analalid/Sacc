package com.example.sacc.responce;

import com.example.sacc.enums.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private String success;
    private String errMsg;
    private String errCode;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>("true", "null", "null", data);
    }

    public static Result failure(String errMsg) {
        return new Result<>("false", errMsg, "5000", "null");
    }

    public static Result failure(ErrorEnum error) {
        return new Result<>("false",error.getErrMsg(), error.getErrCode().toString(), "null");
    }
}
