package cn.edu.nju.supercode.exception;

import cn.edu.nju.supercode.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = SuperCodeException.class)
    public ResultVO<String> handleAllExternalException(SuperCodeException e) {
        return ResultVO.buildFailure(e.getMessage());
    }
    @ExceptionHandler(value = LoginException.class)
    public ResultVO<String> handleLoginException(LoginException e) {
        return ResultVO.notLogin(e.getMessage());
    }
}