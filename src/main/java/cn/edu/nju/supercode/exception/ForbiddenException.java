package cn.edu.nju.supercode.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }

    public static ForbiddenException noSuchPrivilege() {
        throw new ForbiddenException("用户无权执行本操作");
    }

    public static ForbiddenException tooOften() {
        throw new ForbiddenException("提交过于频繁");
    }

}