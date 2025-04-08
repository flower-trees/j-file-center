package org.salt.file.center.frame.advice;

import lombok.extern.slf4j.Slf4j;
import org.salt.file.center.frame.exception.ServiceException;
import org.salt.file.center.frame.vo.Rsp;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionInterceptor {

    @ExceptionHandler(ServiceException.class)
    public Rsp<?> errorHandler(ServiceException e){
        log.error("system error:", e);
        return Rsp.builder().code(e.getCode()).msg(e.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    public Rsp<?> handleException(Exception e) {
        log.error("system error:", e);
        return Rsp.builder().code(500).msg("system error!").build();
    }
}
