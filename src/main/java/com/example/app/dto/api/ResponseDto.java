package com.example.app.dto.api;

import com.example.app.exception.BusinessException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ResponseDto<T> {
    private String code;
    private String message;
    private LocalDateTime time;
    private T data;

    public static <T> ResponseDto<T> ok(T input) {
        ResponseDto<T> output = new ResponseDto<>();
        output.setCode("SUCCESS");
        output.setMessage("SUCCESS");
        output.setTime(LocalDateTime.now());
        output.setData(input);
        return output;
    }

    public static <T> ResponseDto<T> fail(T input, BusinessException exception) {
        ResponseDto<T> output = new ResponseDto<>();
        output.setCode(exception.getErrorCode());
        output.setMessage(exception.getErrorMessage());
        output.setTime(LocalDateTime.now());
        output.setData(input);
        return output;
    }

    public static <T> ResponseDto<T> fail(T input, Exception exception) {
        ResponseDto<T> output = new ResponseDto<>();
        output.setCode("SERVER_ERROR");
        output.setMessage(exception.getMessage());
        output.setTime(LocalDateTime.now());
        output.setData(input);
        return output;
    }

}