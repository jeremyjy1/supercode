package cn.edu.nju.supercode.exception;

import cn.edu.nju.supercode.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = SuperCodeException.class)
    public ResultVO<String> handleAllExternalException(SuperCodeException e) {
        // e.printStackTrace();
        return ResultVO.buildFailure(e.getMessage());
    }
}