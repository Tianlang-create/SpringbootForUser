package com.demo.springdemo.exception;
import com.demo.springdemo.enerity.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandlerAdvice.class);
    @ExceptionHandler({Exception.class})
    public ResponseMessage handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        //记录日志
        logger.error("error", e);
        return new ResponseMessage(500, "error", null);
    }
}
