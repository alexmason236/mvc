package com.zk.config;

import com.zk.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice
@Slf4j
public class T1ControllerAdvice {
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public CommonResponse handleException(Exception e){
//        log.error(e.getCause().getMessage());
        return new CommonResponse(400,e.getMessage());
    }
}
