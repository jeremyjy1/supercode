package cn.edu.nju.supercode.exception;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }

    public static LoginException loginRequired() {throw new LoginException("请登录");}
}