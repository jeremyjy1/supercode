package cn.edu.nju.supercode.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }

    public static ConflictException userExisted() {
        throw new ConflictException("用户名已存在");
    }

}