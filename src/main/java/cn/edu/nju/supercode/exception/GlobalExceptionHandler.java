package cn.edu.nju.supercode.exception;

import cn.edu.nju.supercode.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = LoginException.class)
    public ResultVO<String> handleLoginException(LoginException e) {
        return ResultVO.notLogin(e.getMessage());
    }

    @ExceptionHandler(value = ForbiddenException.class)
    public ResultVO<String> handleUnauthorizedException(ForbiddenException e) {
        return ResultVO.forbidden(e.getMessage());
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResultVO<String> handleNotFoundException(NotFoundException e) {
        return ResultVO.notFound(e.getMessage());
    }

    @ExceptionHandler(value = ConflictException.class)
    public ResultVO<String> handleConflictException(ConflictException e) {
        return ResultVO.conflict(e.getMessage());
    }
}