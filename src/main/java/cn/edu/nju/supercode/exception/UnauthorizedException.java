package cn.edu.nju.supercode.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public static UnauthorizedException noSuchPrivilege() {
        throw new UnauthorizedException("用户无权执行本操作");
    }

}