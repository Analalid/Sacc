package com.example.sacc.Exception;

import com.example.sacc.enums.ErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class LocalRuntimeException extends RuntimeException {
    private ErrorEnum errorEnum;

    public LocalRuntimeException(String message) {
        super(message);
    }
    public LocalRuntimeException(ErrorEnum errorEnum){
        super(errorEnum.getErrMsg());
        this.errorEnum = errorEnum;
    }
}
