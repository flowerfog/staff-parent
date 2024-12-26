package org.flowerfog.handler;

import org.flowerfog.entity.Result;
import org.flowerfog.entity.ResultCode;
import org.flowerfog.exception.CommonException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义全局异常处理
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response,Exception e){

        if(e.getClass() == CommonException.class){
            //转型
            CommonException ce =(CommonException) e;

            Result result = new Result(ce.getResultCode());
            return result;
        }else{
            Result result = new Result(ResultCode.SERVER_ERROR);


            return result;
        }




    }
}
