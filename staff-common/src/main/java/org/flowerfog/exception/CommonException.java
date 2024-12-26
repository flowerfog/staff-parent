package org.flowerfog.exception;

import lombok.Getter;
import org.flowerfog.entity.Result;
import org.flowerfog.entity.ResultCode;

/**
 * 自定义异常
 */
@Getter
public class CommonException extends Exception{

    private ResultCode resultCode;

    public CommonException(ResultCode resultCode){
        this.resultCode = resultCode;
    }
}
