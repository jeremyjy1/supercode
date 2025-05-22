package cn.edu.nju.supercode.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException userNotExisted() {
        throw new NotFoundException("用户不存在");
    }

    public static NotFoundException problemNotFound(){throw new NotFoundException("题目不存在");}

}