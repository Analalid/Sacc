package com.example.sacc.Exception;
import com.example.sacc.responce.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(LocalRuntimeException.class)
    public Result localRunTimeException(LocalRuntimeException e) {
        log.error("异常", e);
        if (e.getErrorEnum() != null) {
            return Result.failure(e.getErrorEnum());
        } else {
            return Result.failure(e.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handlerValidationException(MethodArgumentNotValidException e) {
        log.error("参数校验异常", e);
        //流处理，获取错误信息
        String messages = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("\n"));
        return Result.failure(messages);
    }
}
